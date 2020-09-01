package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 22:36
  */
object Spark09_Test {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(conf)

    val dataRDD = sc.makeRDD(List("Hello Scala", "Hello"))
    val rdd1: RDD[(String, Iterable[String])] = dataRDD.flatMap(_.split(" ")).groupBy(s=>s)
    val result: RDD[(String, Int)] = rdd1.mapValues(_.size)
    result.foreach(println)

    sc.stop()
  }
}
