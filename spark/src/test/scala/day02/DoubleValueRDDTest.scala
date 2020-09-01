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
class DoubleValueRDDTest {

  @Test
  def zipPartitionsTest():Unit={
    val list1 = List[Int](1,2,3,4,5)
    val list2 = List[Int](3,2,5,4)

    val rdd1 = sc.makeRDD(list1,2)
    val rdd2 = sc.makeRDD(list2,2)

    val result = rdd1.zipPartitions(rdd2)(
      (rdd1Iter: Iterator[Int],
       rdd2Iter: Iterator[Int]) => {
        rdd1Iter.zipAll(rdd2Iter, 2, 3)
      }
    )
    result.foreach(println)
  }


  @Test
  def zipAllTest():Unit={
    val list1 = List[Int](1,2,3,4,5)
    val list2 = List[Int](3,2,5,4)

    val list = list1.zipAll(list2,2,3)
    list.foreach(println)
  }

  /**
    * 当前RDD中的每一个元素和索引拉链
    */
  @Test
  def zipWithIndexTest():Unit={
    val list1 = List[Int](1,2,3,4)
    val rdd1 = sc.makeRDD(list1,2)
    rdd1.zipWithIndex().foreach(println)
  }



  /**
    * intersection 交集： shuffle
    * union 并集
    * subtract 差集
    */
  @Test
  def intersectionTest():Unit={
    val list1 = List[Int](1,2,3,4)
    val list2 = List[Int](3,2,5,4)

    val rdd1 = sc.makeRDD(list1,2)
    val rdd2 = sc.makeRDD(list2,3)

    rdd1.intersection(rdd2).saveAsTextFile("outputSpark")
    rdd1.union(rdd2).foreach(println)
    rdd1.subtract(rdd2).foreach(println)

    rdd1.zip(rdd2).foreach(println)

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
