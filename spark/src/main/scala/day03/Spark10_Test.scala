package day03

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 18:29
  */
object Spark10_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.textFile("inputSpark/apache.log")

    // 83.149.9.216 - - 17/05/2015:10:05:03 +0000 GET /presentations/logstash-monitorama-2013/images/kibana-search.png
    val result: RDD[String] = rdd.filter(line => {
      val datas: Array[String] = line.split(" ")
      datas(3).startsWith("17/05/2015")
    })
    result.foreach(println)

    result.distinct()
    sc.stop()
  }
}
