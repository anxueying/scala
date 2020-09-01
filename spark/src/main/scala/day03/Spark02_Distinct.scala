package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 13:12
  *        转换算子 distinct
  */
object Spark02_Distinct {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val list = List[Int](1,2,3,4,1,2,3,4)

    val rdd: RDD[Int] = sc.makeRDD(list,2)

    rdd.saveAsTextFile("outputSpark")
    rdd.distinct.saveAsTextFile("outputSpark1")
    rdd.distinct(3).saveAsTextFile("outputSpark2")

    sc.stop()
  }
}
