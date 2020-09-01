package day02

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark._
import org.junit.{After, Before, Test}

/**
  * @author Shelly An
  * @create 2020/8/2 21:14
  */

case class User(name: String,age:Int)

class UserPartitioner(numPartition:Int) extends Partitioner{
  override def numPartitions: Int = numPartition

  override def getPartition(key: Any): Int = {
    if (!key.isInstanceOf[User]) {
      0
    }else{
      val user = key.asInstanceOf[User]
      user.age % numPartitions
    }
  }
}

class KeyValueRDDTest {
  @Test
  def partitionByTest1(): Unit = {
    val list = List[Int](3, 2, 5, 4)
    val rdd = sc.makeRDD(list,2).map((_,1))


    val result = rdd.partitionBy(new RangePartitioner(3,rdd))

    result.keys.saveAsTextFile("outputSpark")

  }



  @Test
  def partitionByTest(): Unit = {
    val list = List[Int](3, 2, 5, 4)
    val rdd = sc.makeRDD(list,2).map((_,1))

    //MapPartitionsRDD
    println(rdd.getClass.getName)

    //隐式转换 MapPartitionsRDD ==> PairRDDFunctions 得有隐式转换函数
    val result = rdd.partitionBy(new HashPartitioner(3))
    //result: ShuffledRDD(new HashPartitioner(3))


    //下面这行代码会不会再次分区
    val result1 = rdd.partitionBy(new HashPartitioner(3))

    //new HashPartitioner(3) ==new HashPartitioner(3) 因此不会再次分区


    /**
      * int类型的hash值就是它本身
      * 因此结果应为
      * 3%3=0
      * 2%3=2
      * 5%3=2
      * 4%3=1
      * 3  4  2,5
      */
    result1.keys.saveAsTextFile("outputSpark")

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
