package day05

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/4 9:24
  *    统计 每一个省份，广告被点击数量排行  前三的广告
  *    输出： {省份1 ,{top1ad,top2ad,top3ad}}
  */
object Exercise {
  def main(args: Array[String]): Unit = {
    //时间戳 省份id 城市id 用户id 广告id
    //1516609143867 6 7 64 16
    /**
      * 思路：
      * 1. 数据的粒度：数据的最基本单位所代表的意义  一行数据代表对一个广告的一次点击
      * 2. 分析逻辑 ((省份，广告), 1) ==>groupByKey==>拆分key(省份, (广告, N))==>排序 取前三
      */

    val conf = new SparkConf().setMaster("local[*]").setAppName("My App")
    val sc = new SparkContext(conf)
    val datas = sc.textFile("spark/inputSpark/agent.log")
    val rdd = datas.map(line => {
      val words = line.split(" ")
      ((words(1), words(4)), 1)
    })
    //((省份，广告), N)
    val rdd1 = rdd.reduceByKey(_+_)
    val rdd2 = rdd1.map({
      case ((province, ads), displayCount) => (province, (ads, displayCount))
    })
    val rdd3 = rdd2.groupByKey()
    val result = rdd3.mapValues(iter => {
      iter.toList.sortBy(-_._2).take(3)
    })
    result.coalesce(1).saveAsTextFile("spark/outputSpark")

    sc.stop()
  }
}
