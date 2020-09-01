package streamingproject.app

import org.apache.spark.streaming.Minutes
import org.apache.spark.streaming.dstream.DStream
import streamingproject.bean.AdsInfo

/**
  * @author Shelly An
  * @create 2020/8/22 10:39
  *         最近一小时广告点击量
  *         统计数据的范围：  最近1h （需要窗口）
  *         统计的步长： 随意
  *
  *         1：List [15:50->10,15:51->25,15:52->30]
  *         广告ID ：List{ 时间-->点击总量，时间--->点击总量...}
  *
  *         AdsInfo(1597131632947,华南,深圳,104,3,2020-08-11,15:40)  => ((广告,hm),1) =>
  *         ((广告,hm),N) => (广告,(hm,N))  =>  (广告， List{  (hm,N),(hm,N),(hm,N),(hm,N) })
  */
object Function3Demo extends BaseApp {
  def main(args: Array[String]): Unit = {
    runApp {
      context.checkpoint("output/function2")

      //读取最新一批数据 从kafka获取数据，封装为ds, 泛型为自定义类
      val ds: DStream[AdsInfo] = getAllBeans(getDStream())
      val result: DStream[(String, List[(String, Int)])] = ds.window(Minutes(60))
        .map(bean => ((bean.adsId, bean.hmString), 1))
        .reduceByKey(_ + _)
        .map {
          case ((adsId, hm), count) => (adsId, (hm, count))
        }.groupByKey()
        .mapValues(iter => iter.toList.sortBy(_._1))
      result.print(100)

    }
  }
}
