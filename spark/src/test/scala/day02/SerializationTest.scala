package day02

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Before, Test}

/**
  * @author Shelly An
  * @create 2020/8/2 21:14
  */
class user(age:Int) extends Serializable
class user1(age:Int)

class MyUser2(age: Int = 18){
  def fun(x:Int) : Boolean={
    x > 18
  }
  //对传入的RDD进行过滤
  def MyFilter(rdd : RDD[Int]):RDD[Int]={
    val f = fun _ //因为传过来还是一个函数，不是已经序列化的基础数据类型，因此不可
    rdd.filter(f)
  }
}

case class MyUser3(age: Int = 18,name:String="fawefa3fjaoifhjaoewihfoawihfoiawefhawoihfaweoihfa")

class SerializationTest {

  /*
     体会kryo: 告诉Spark，哪些类需要被kryo序列化！
             要求：  被kryo序列化的类，必须实现java.io.Serializable

           java.io.Serializable : 568.0 B
           kyro : 208.0 B
  */
  @Test
  def test6() : Unit ={
    val list = List(MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3(), MyUser3())
    val rdd: RDD[MyUser3] = sc.makeRDD(list, 2)
    val rdd2: RDD[(Int, MyUser3)] = rdd.map(x => (1, x))
    rdd2.groupByKey().collect()
    Thread.sleep(1000000000)
  }


  @Test
  def test4() : Unit ={
    val list = List(19, 20, 3, 4)
    val rdd: RDD[Int] = sc.makeRDD(list, 2)
    //  Task not serializable
    val rdd1: RDD[Int] = new MyUser2().MyFilter(rdd)
    println(rdd1.collect().mkString(","))
  }


  @Test
  def test3():Unit={
    val list = List[Int](1,2)
    val rdd = sc.makeRDD(list)
    val user1 = new user1(18)
    rdd.foreach(x=> user1)
  }


  @Test
  def test2():Unit={
    val list = List[Int](1,2)
    val rdd = sc.makeRDD(list)

    //没有形成闭包
    rdd.foreach(x=>new user(x))
  }


  @Test
  def test1():Unit={
      //常见的基本数据类型都已经实现序列化。 case class 样例类自动实现序列化  class需要手动实现序列化
    val list = List[Int](1,2)
    val rdd = sc.makeRDD(list)

    var sum:Int = 0

    //形成了闭包，sum+=x
    rdd.foreach(x=> sum+=x)

    println(sum)  //0

  }

  var sc: SparkContext = null

  /**
    * 每次运行前，自动删除输出目录 使用hadoop的包
    */
  @Before
  def start(): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("My app").registerKryoClasses(Array(classOf[MyUser3]))
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
