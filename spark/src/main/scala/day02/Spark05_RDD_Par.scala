package day02

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/7/31 21:34
  */
object Spark05_RDD_Par {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    //todo 文件数据的分区
    val file: RDD[String] = sc.textFile("input/taobao.txt")
    file.saveAsTextFile("outputSpark")

    sc.stop()
  }
}
