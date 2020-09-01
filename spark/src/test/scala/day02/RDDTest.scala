package day02

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Before, Test}


/**
  * @author Shelly An
  * @create 2020/8/2 15:17
  *        main(主类文件)：相对路径，相对于project
  *        test(测试类文件)：相对路径，相对于module
  */
class RDDTest {

  var sc:SparkContext = null

  /**
    * 每次运行前，自动删除输出目录 使用hadoop的包
    */
  @Before
  def start(): Unit ={
    val conf = new SparkConf().setMaster("local").setAppName("My app")
    conf.set("spark.default.parallelism","1")
    sc = new SparkContext(conf)
    val fs = FileSystem.get(new Configuration())
    val path = new Path("outputSPark")
    if (fs.exists(path)) {
      //递归删除
      fs.delete(path,true)
    }
  }


  /**
    * 每次运行后，关闭资源
    */
  @After
  def stop()={
    sc.stop()
  }

  /**
    * 将结果输出到控制台
    */
  @Test
  def test1() ={
    val rdd = sc.textFile("inputSpark/word.txt")
      .flatMap(_.split(" ")) // [aa ,bb ,cc..]
      .map((_, 1)) //[(aa,1),(bb,1),(cc,1)...] 变成k-v类型
      .reduceByKey(_ + _)  //传入函数对value做运算

    rdd.collect().foreach(println)
  }

  /**
    * 将结果保存到目录
    * 目录不能存在
    */
  @Test
  def test2() ={
    val rdd = sc.textFile("inputSpark/word.txt")
      .flatMap(_.split(" ")) // [aa ,bb ,cc..]
      .map((_, 1)) //[(aa,1),(bb,1),(cc,1)...] 变成k-v类型
      .reduceByKey(_ + _)  //传入函数对value做运算

    rdd.saveAsTextFile("outputSpark")
  }


  /**
    * slice 返回索引在[from,until)之间的元素
    */
  @Test
  def arraySlice():Unit={
    val arr = Array(1,2,3,4,5)
    println(arr.slice(2, 4).mkString(","))
  }

  /**
    * 从内存中创建RDD：ParallelCollectionRDD
    */
  @Test
  def createRDDMemory():Unit={
    val arr = Array(1,2,3,4,5)
    val rdd = sc.makeRDD(arr,4)
    //等价于
    val rdd1 = sc.parallelize(arr,4)
    rdd.saveAsTextFile("outputSpark")
  }


  /**
    * 从文件中创建RDD 因为inputSpark中有两个文件，所以分区是2
    */
  @Test
  def createRDDFile():Unit={
    val rdd = sc.textFile("inputSpark")
    rdd.saveAsTextFile("outputSpark")
  }

  /**
    * 从文件中创建RDD指定分区
    */
  @Test
  def createRDDFilePartition():Unit={
    val rdd = sc.textFile("inputSpark",1)
    rdd.saveAsTextFile("outputSpark")
  }

}
