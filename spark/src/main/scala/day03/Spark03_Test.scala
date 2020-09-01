package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 13:12
  */
object Spark03_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val list = List[Int](1,4,3,2)
    val rdd: RDD[Int] = sc.makeRDD(list,2)

    val rdd1: RDD[Int] = rdd.mapPartitions(
      iter =>
        List(iter.max).iterator
    )
    rdd1.foreach(println)

    sc.stop()
  }
}
