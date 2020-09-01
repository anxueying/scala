package coreproject.app

import coreproject.acc.CategoryAcc
import coreproject.base.BaseApp

/**
  * @author Shelly An
  * @create 2020/8/5 15:32
  */
object PopularCategoriesDemo3 extends BaseApp{
  override val outputPath: String = "output/PopularCategories"

  def main(args: Array[String]): Unit = {
    runApp{
      val categoryAcc = new CategoryAcc
      sc.register(categoryAcc)
      //读取数据
      val rdd = sc.textFile("spark/inputSpark/user_visit_action.txt")
      //过滤掉搜索的
      val rdd1 = rdd.filter(line=>line.split("_")(5) == "null")
      //一次性封装所有数据
      rdd1.foreach(line => {
        val words = line.split("_")
        if (words(6) != "-1") {
          //品类 ，点击1次
         categoryAcc.add(words(6),"click")
        } else if(words(8) != "null"){
          //下单
          val categories = words(8).split(",")
          for (elem <- categories)  categoryAcc.add(elem,"order")
        } else if(words(10) != "null"){
          //支付
          val categories = words(10).split(",")
          for (elem <- categories)   categoryAcc.add(elem,"pay")
        }else{
          Nil
        }
      })

      val result = categoryAcc.value.values.toList.sortBy(x=>x).reverse.take(10)

      sc.makeRDD(result,1).saveAsTextFile(outputPath)
    }
  }
}
