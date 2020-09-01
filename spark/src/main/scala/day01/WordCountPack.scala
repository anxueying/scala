package day01

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/7/29 11:37
  */
object WordCountPack {
  def main(args: Array[String]): Unit = {
    // 创建spark的配置对象 集群不写死
    val conf:SparkConf = new SparkConf().setAppName("My app")
    //创建应用上下文
    val sparkContext = new SparkContext(conf)
    val rdd = sparkContext.textFile(args(0))

    /**
      * Mapper:  单词 ---> (单词，1)
      * Reducer:   (单词，1), (单词，1)  ---->  (单词，N)
      *
      * reduceByKey: 将相同的key的value进行聚合，聚合后，使用函数，对聚合后的value进行reduce操作！
      * 要求RDD中的数据，必须是key-value
      */
    val rdd1 = rdd.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey((x,y)=>x+y)
      .collect().mkString(",")

    println(rdd1)

    //关闭上下文
    sparkContext.stop()
  }
}
