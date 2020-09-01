package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 13:12
  *        转换算子 map
  */
object Spark01_TransformOperate {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val list = List[Int](1,2,3,4)
    val rdd: RDD[Int] = sc.makeRDD(list,2)
    val rdd1 = rdd.mapPartitions(
      iter =>{
        println("###########")
        iter.map(_*2)
      }
    ).collect()

    val rdd2: Array[Int] = rdd.map(
      num => {
        println("*********")
        num * 2
      }
    ).collect()

    sc.stop()
  }
}
