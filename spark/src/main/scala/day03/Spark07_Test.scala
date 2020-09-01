package day03

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 18:29
  */
object Spark07_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("rdd")
    val sc = new SparkContext(sparkConf)
    //将List("Hello", "hive", "hbase", "Hadoop")根据单词首写字母进行分组。
    val list = List("Hello", "hive", "hbase", "Hadoop")

    val rdd = sc.makeRDD(list)

    rdd.groupBy(
      s =>
        //s.charAt(0)
        //s.substring(0,1)
        s(0)
    ).collect().foreach(println)
    //(H,CompactBuffer(Hello, Hadoop))
    //(h,CompactBuffer(hive, hbase))
    sc.stop()
  }
}
