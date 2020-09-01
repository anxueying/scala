package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 18:29
  */
object Spark05_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)

    val list = List(List[Int](1,2),3,List[Int](4,5))

    val rdd: RDD[Any] = sc.makeRDD(list)
    rdd.flatMap {
      case list: List[Int] => list
      case num => List(num)
    }
    rdd.collect().foreach(println)
    sc.stop()
  }
}
