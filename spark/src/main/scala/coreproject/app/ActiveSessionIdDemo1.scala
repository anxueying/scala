package coreproject.app

import coreproject.base.BaseApp

/**
  * @author Shelly An
  * @create 2020/8/10 14:08
  */
object ActiveSessionIdDemo1 extends BaseApp{
  override val outputPath: String = "output/ActiveSessionId"

  def main(args: Array[String]): Unit = {
    runApp{
      //①求出Top10热门品类 利用上一个需求的结果
      val rdd = sc.textFile("Output/PopularCategories/part-00000")
      //AccBean(15,6120,1672,1259)
      val top10Types = rdd.map(line => {
        line.split(",")(0).substring(8)
      })
      val bc = sc.broadcast(top10Types.collect)
      //​②过滤Top10热门品类的点击数据
      val rdd1 = sc.textFile("spark/inputSpark/user_visit_action.txt")
      //2019-07-17
      // 95  user_id
      // 26070e87-1ad7-49a3-8fb3-cc741facaddf session_id
      // 37 page_id
      // 2019-07-17 00:00:02 action_time
      // 手机
      // -1_-1 click_category_id click_product_id
      // null_null order_category_ids order_product_ids
      // null_null pay_category_ids pay_product_ids
      // 3 city_id
      val top10Click = rdd1.map(line => {
        val words = line.split("_")
        (words(6),words(2))
      }).filter(x=>bc.value.contains(x._1))

      //​③将点击数据按照  category-session分组，求出每个品类下，每个Session的点击总量
      val rdd3 = top10Click.map(x=>(x,1)).reduceByKey(_+_)
      //​④按照category分组，在每个category组下，按照点击总量进行降序排序，排序后取前10
      val rdd4 = rdd3.map {
        case ((category, sessionId), clickcount) => (category, (sessionId, clickcount))
      }.groupByKey()
      val result = rdd4.mapValues(iter => {
        iter.toList.sortBy(x => -x._2).take(10)
      })
      //存储结果
      result.saveAsTextFile(outputPath)

    }
  }
}
