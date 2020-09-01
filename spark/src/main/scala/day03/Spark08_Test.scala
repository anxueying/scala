package day03

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 18:29
  */
object Spark08_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.textFile("inputSpark/apache.log")

    // 83.149.9.216 - - 17/05/2015:10:05:03 +0000 GET /presentations/logstash-monitorama-2013/images/kibana-search.png
    val rdd1: RDD[(String, Iterable[String])] = rdd.groupBy(line => {
      val datas: Array[String] = line.split(" ")
      val dateString: String = datas(3)
      //时间转换 string--date--string
      val sdf = new SimpleDateFormat("dd/MM/yyyy:HH:mm:ss")
      val target = new SimpleDateFormat("HH")
      val c: Date = sdf.parse(dateString)
      target.format(c)
    })
    
    val result: RDD[(String, Int)] = rdd1.mapValues(_.size)
    result.foreach(println)

    sc.stop()
  }
}
