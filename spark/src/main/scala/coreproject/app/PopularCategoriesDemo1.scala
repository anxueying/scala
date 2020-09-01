package coreproject.app

import coreproject.base.BaseApp

/**
  * @author Shelly An
  * @create 2020/8/5 15:32
  */
object PopularCategoriesDemo1 extends BaseApp{
  override val outputPath: String = "output/PopularCategories"

  def main(args: Array[String]): Unit = {
    runApp{
      //读取数据
      val rdd = sc.textFile("spark/inputSpark/user_visit_action.txt")
      //过滤掉搜索的
      val rdd1 = rdd.filter(line=>line.split("_")(5) == "null")

      println("统计点击数...")
      //点击
      val click = rdd1.flatMap(line => {
        val words = line.split("_")
        if (words(6) != "-1") {
          //品类 ，点击1次
          List((words(6), 1))
        } else {
          Nil
        }
      })
      val clickResult = click.reduceByKey(_+_)
      println("统计下单数...")
      //下单
      val order = rdd1.flatMap(line => {
        val words = line.split("_")
        if (words(8) != "null") {
          //品类 ，下单1次
          val categories = words(8).split(",")
          for (elem <- categories) yield {(elem,1)}
        } else {
          Nil
        }
      })
      val orderResult = order.reduceByKey(_+_)
      println("统计支付数...")
      //支付
      val pay = rdd1.flatMap(line => {
        val words = line.split("_")
        if (words(10) != "null") {
          //品类 ，支付1次
          val categories = words(10).split(",")
          for (elem <- categories) yield {(elem,1)}
        } else {
          Nil
        }
      })
      val payResult = pay.reduceByKey(_+_)

      //自己试试fullOuterJoin怎么搞
      val joinResult = clickResult.leftOuterJoin(orderResult).leftOuterJoin(payResult)

      val result = joinResult.map({
        case (categoryId,((clickCount,orderCount),payCount)) =>
          (categoryId,(clickCount,orderCount.getOrElse(0),payCount.getOrElse(0)))
      })

      val writeData = result.sortBy(_._2,false).take(10)

      sc.makeRDD(writeData,1).saveAsTextFile(outputPath)
    }
  }
}
