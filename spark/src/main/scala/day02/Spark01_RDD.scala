package day02

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/7/31 21:34
  */
object Spark01_RDD {
  def main(args: Array[String]): Unit = {

    /**
      * Spark RDD的创建
      *
      * 创建方式
      * 1. 内存中创建   ： list（1,2,3,4）
      * 2. 存储中创建 ： File
      *
      * 以下两个方式一般都是框架底层使用的方式，我们用的不多
      * 3. 从RDD创建
      * 4. 直接new
      */

    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    //todo 从集合中创建RDD
    val list: List[Int] = List[Int](1, 2, 3, 4)
    /**
      * 方法1：
      * 使用parallelize方法将集合转换为RDD，进行操作
      * parallelize: 并行处理集合中的数据
      * scala集合：list.par
      */
    val numRDD: RDD[Int] = sc.parallelize(list)
    numRDD.collect().foreach(println)

    //方法2：makeRDD: 生成RDD对象
    val numRDD1: RDD[Int] = sc.makeRDD(list)
    numRDD1.collect().foreach(println)

    //todo 从存储系统中创建RDD
    /**
      * 存储系统：基本上就是文件系统，数据库，Hbase
      * path 表示文件的相对路径
      *       1. 可以使用星号进行通配操作
      *       2. path路径可以是具体的文件，也可以是目录
      * Spark环境通过textFile来读取文件，读取的方式一行一行来读取的
      *
      */
    val file: RDD[String] = sc.textFile("inputSpark/word*.txt")
    file.collect().foreach(println)
    sc.stop()
  }
}
