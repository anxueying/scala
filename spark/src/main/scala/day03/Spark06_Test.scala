package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 18:29
  */
object Spark06_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)

    val list = List(1,2,3,4,5,6,7)

    val rdd= sc.makeRDD(list,3)

    val d: Int = rdd.glom().map(_.max).collect().sum
    println(d)
    sc.stop()
  }
}
