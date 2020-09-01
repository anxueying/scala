package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 13:12
  *       从服务器日志数据apache.log中获取用户请求URL资源路径
  */
object Spark02_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.textFile("inputSpark/apache.log")

    val newRDD: RDD[String] = rdd.map(line => {
      val datas: Array[String] = line.split(" ")
      datas(6)
    })
    newRDD.foreach(println)
    sc.stop()
  }
}
