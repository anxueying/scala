package coreproject.app

import coreproject.base.BaseApp

/**
  * @author Shelly An
  * @create 2020/8/5 15:32
  */
object PopularCategoriesDemo2 extends BaseApp{
  override val outputPath: String = "output/PopularCategories"

  def main(args: Array[String]): Unit = {
    runApp{
      //读取数据
      val rdd = sc.textFile("spark/inputSpark/user_visit_action.txt")
      //过滤掉搜索的
      val rdd1 = rdd.filter(line=>line.split("_")(5) == "null")


      //一次性封装所有数据
      val datas = rdd1.flatMap(line => {
        val words = line.split("_")
        if (words(6) != "-1") {
          //品类 ，点击1次
          List((words(6), 1,0,0))
        } else if(words(8) != "null"){
          //下单
          val categories = words(8).split(",")
          for (elem <- categories) yield {(elem, 0,1,0)}
        } else if(words(10) != "null"){
          //支付
          val categories = words(10).split(",")
          for (elem <- categories) yield {(elem, 0,0,1)}
        }else{
          Nil
        }
      })

      val dataMap = datas.map({
        case (id, cc, oc, pc) => (id, (cc, oc, pc))
      })
      val result = dataMap.reduceByKey({
        case ((x, y, z), (a, b, c)) => (a + x, b + y, c + z)
      })
      val writeData = result.sortBy(_._2,false).take(10)

      sc.makeRDD(writeData,1).saveAsTextFile(outputPath)
    }
  }
}
