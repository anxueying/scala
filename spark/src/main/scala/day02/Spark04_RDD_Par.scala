package day02

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/7/31 21:34
  */
object Spark04_RDD_Par {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local[2]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    //todo RDD的分区和并行度
    val list: List[Int] = List[Int](1,2,3,4,5,6)
    //如果想要改变分区，可以使用第二个参数来代替默认值
    val number: RDD[Int] = sc.makeRDD(list,3)
    number.saveAsTextFile("outputSpark")
    sc.stop()
  }
}
