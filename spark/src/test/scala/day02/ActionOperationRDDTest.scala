package day02

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Before, Test}

/**
  * @author Shelly An
  * @create 2020/8/2 21:14
  */
class ActionOperationRDDTest {

  @Test
  def writeDataToService():Unit={
    val list1 = List[Int](1,2,3,4,5)
    val rdd1 = sc.makeRDD(list1,2)
    rdd1.foreachPartition(println)
  }

  /**
    * reduce 可以做单值累加 或 sum也可
    * 区别是 reduce可以传入更复杂的函数
    */
  @Test
  def reduceTest():Unit={
    val list1 = List[Int](1,2,3,4,5)
    val rdd1 = sc.makeRDD(list1,2)

    val rdd2 = rdd1.map((_,1))
    /**
      * 在executor端运行foreach，分布式
      * Executor task launch worker for task 0	1
      * Executor task launch worker for task 0	2
      * Executor task launch worker for task 1	3
      * Executor task launch worker for task 1	4
      * Executor task launch worker for task 1	5
      */
    rdd1.foreach(x=>println(Thread.currentThread().getName +"\t"+ x))

    /**
      * 在driver端运行foreach，单线程
      * main	1
      * main	2
      * main	3
      * main	4
      * main	5
      */
    rdd1.collect.foreach(x=>println(Thread.currentThread().getName +"\t"+ x))
  }


  @Test
  def collectTest():Unit={
    val list1 = List[Int](1,2,3,4,5)
    val rdd1 = sc.makeRDD(list1,2)


    rdd1.collect()
    rdd1.saveAsTextFile("")

    rdd1.sortBy(x=>x)

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
