package day02

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Before, Test}

import scala.reflect.ClassTag

/**
  * @author Shelly An
  * @create 2020/8/2 21:14
  */
case class Person(age:Int,name:String) extends Ordered[Person]{
  override def compare(that: Person): Int = {
    var result = -this.age.compareTo(that.age)
    if (result==0) {
      result=this.name.compareTo(that.name)
    }
    result
  }
}

case class Person1(age:Int,name:String)
class SingleValueRDDTest {

  /**
    * 自定义类型排序
    *
    *  排序： 调用 compareTo
    *
    * scala提供两种类型： Ordering[T] extends Comparator[T] ：  可以比较T类型的比较器！
    * 不要求T类型实现  CompareTo
    *
    * Ordered with java.lang.Comparable[A] :  当前T类型是可排序的！
    * 调用T类型的CompareTo
    */
  @Test
  def sortByTest():Unit={
    val list = List(Person(11, "jack"),
                    Person(11, "jack1"),
                    Person(12, "tom"),
                    Person(13, "marry"))
    val rdd: RDD[Person] = sc.makeRDD(list, 2)
    //按照年龄降序，年龄一样按照名称升序排序
    rdd.sortBy(person=>person,numPartitions = 1).saveAsTextFile("outputSpark")

  }

  @Test
  def sortByTest1():Unit={
    val list = List(Person(11, "jack"),
      Person(11, "jack1"),
      Person(12, "tom"),
      Person(13, "marry"))
    val rdd: RDD[Person] = sc.makeRDD(list, 2)
    //全部升序 true or 降序 false
    //rdd.sortBy(person=>(person.age,person.name),false,numPartitions = 1)
    rdd.sortBy(p => (p.age,p.name), //谁写在前面先排谁
      numPartitions = 1)(Ordering.Tuple2(Ordering.Int.reverse,Ordering.String),
      ClassTag(classOf[Tuple2[Int,String]])).saveAsTextFile("outputSpark")
  }


  /**
    * map:            f:T=>U
    * mapPartitions : f:Iterator[T] => Iterator[U],preservesPartitioning: Boolean = false
    */
  @Test
  def mapPartitionTest():Unit={
    val list = List(1, 2, 3, 4)
    val rdd = sc.makeRDD(list,2)
    val result = rdd.mapPartitions(iter=>iter.map(_+1))
    result.saveAsTextFile("outputSpark")
  }


  @Test
  def mapTest1():Unit={
    val list = List(1, 2, 3, 4)
    val rdd = sc.makeRDD(list,2)

    val rdd1 = rdd.map(x => {
      println("当前第一个map：" + x)
      x
    })
    val rdd2 = rdd1.map(x => {
      println("当前第二个map：" + x)
      x
    })

    rdd2.saveAsTextFile("outputSpark")
  }


  @Test
  def mapTest(): Unit = {
    val list = List(1, 2, 3, 4)
    val rdd = sc.makeRDD(list,2)
    /**
      * 闭包：一个函数调用了外部的某个元素，这个外部元素和函数共同构成闭包
      * 在spark中，区分代码的执行位置
      * 一个Job的进程：Driver Executor
      * 只要是算子，都是在executor端执行
      */
    //创建变量
    val i = 1  //clean检查map中的函数是否用到闭包，
    // 如果用到了，要求i必须可以序列化，才能发送给task
    val rdd1:RDD[Int] = rdd.map(x => x + i) //这个就是闭包
    rdd1.saveAsTextFile("outputSpark")
  }


  var sc: SparkContext = null

  /**
    * 每次运行前，自动删除输出目录 使用hadoop的包
    */
  @Before
  def start(): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("My app")

    sc = new SparkContext(conf)
    val fs = FileSystem.get(new Configuration())
    val path = new Path("outputSPark")
    if (fs.exists(path)) {
      //递归删除
      fs.delete(path, true)
    }
  }


  /**
    * 每次运行后，关闭资源
    */
  @After
  def stop() = {
    sc.stop()
  }
}
