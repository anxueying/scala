package coreproject.app

import java.text.DecimalFormat

import coreproject.base.BaseApp
import coreproject.bean.UserVisitAction

/**
  * @author Shelly An
  * @create 2020/8/10 14:37
  */
object PageConversionRate extends BaseApp {
  override val outputPath: String = "output/PageConversionRate"

  def main(args: Array[String]): Unit = {
    runApp {
      //统计每一个页面的访问量 (k,v) ->(page_id,page_visit_count)
      val rdd = getAllBeans()
      val rdd1 = rdd.map(bean => (bean.page_id, 1)).reduceByKey(_ + _)
      val pvMap = rdd1.collect.toMap
      //生成广播变量以供使用
      val bc = sc.broadcast(pvMap)

      //todo 统计单跳页面
      //将数据，按照用户和sessionId进行分组
      val rdd3 = rdd.groupBy(bean => (bean.user_id, bean.session_id))
      //按用户，按会话，按时间排序
      //将每个用户和每个session按照操作的时间进行升序排序
      val rdd4 = rdd3.mapValues(iter => {
        iter.toList.sortBy(bean => bean.action_time)
      })
      //取排序后的操作的页面ID即可  [34，24，30],[...],[...]
      val rdd5 = rdd4.values.map(list => {
        for (elem <- list) yield {
          elem.page_id
        }
      })

      //两两组合，求单跳的页面
      // head 返回列表第一个元素
      //tail 返回一个列表，包含除了第一元素之外的其他元素
      val pvRDD = rdd5.flatMap(list=>list.zip(list.tail))

      //将所有的单跳页面进行分组，统计个数 ((fromPage,toPage),pvCount)
      val pvCountRDD = pvRDD.map((_,1)).reduceByKey(_+_)

      val formatter = new DecimalFormat(".00%")

      //统计单跳页面跳转绿
      val result = pvCountRDD.map {
        case ((fromPage, toPage), pvCount) =>
          fromPage + "-" + toPage + "=" + formatter.format(pvCount.toDouble / bc.value.getOrElse(fromPage, 1))
      }
      result.coalesce(1).saveAsTextFile(outputPath)
    }
  }


  def getAllBeans() = {
    sc.textFile("spark/inputSpark/user_visit_action.txt")
      .map(line => {
        val words = line.split("_")
        UserVisitAction(
          words(0),
          words(1).toLong,
          words(2),
          words(3).toLong,
          words(4),
          words(5),
          words(6).toLong,
          words(7).toLong,
          words(8),
          words(9),
          words(10),
          words(11),
          words(12).toLong,
        )
      })
  }
}
