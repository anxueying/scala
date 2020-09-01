package day02

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/7/31 21:34
  */
object Spark03_RDD_Par {
  def main(args: Array[String]): Unit = {

    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    //todo RDD的分区和并行度

    /**
      * RDD的分区主要用于分布式计算，可以将数据发送到不同的executor执行计算，和并行有关系
      *
      * 并行度：
      * 表示在整个集群执行时，同时执行任务的数量
      *
      * RDD的分区数量是可以在创建时更改的。如果不更改，那么使用默认的分区
      */

    val list: List[Int] = List[Int](1,2,3,4)
    //默认创建的时候分区就已经有了
    val number: RDD[Int] = sc.makeRDD(list)
    //将RDD保存成分区文件即可看出来,12个=>默认有12个分区，说明idea运行环境是12核
    number.saveAsTextFile("outputSpark")

    sc.stop()
  }
}
