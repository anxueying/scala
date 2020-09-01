package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 13:12
  *        转换算子 distinct
  */
object Spark03_SortBy {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val list = List[Int](1,3,6,5,4,2)
    //scala 柯里化
    // list.sortBy(num=>num)(Ordering.Int.reverse)
    val rdd: RDD[Int] = sc.makeRDD(list,2)
    //spark 执行结果，全局排序
    rdd.sortBy(num=>num,false).collect().foreach(println)
    //如果分区内的数据是否会排序  (1,3,6) (5,4,2) --> (1,3,6) (2,4,5)??
    //不是的 最后的结果是 (1,2,3) (4,5,6)
    //数据在排序的过程中，会将数据打乱重新组合，所以sortBy也包含shuffle操作
    rdd.sortBy(num=>num).saveAsTextFile("outputSpark")
    sc.stop()
  }
}
