package com.atguigu.homework2

import scala.io.Source

/**
  * @author Shelly An
  * @create 2020/7/27 15:47
  *        获取农产品种类最多的三个省份
  *         香菜	  2.80	2018/1/1	山西汾阳市晋阳农副产品批发市场	山西	汾阳
  */
object $02_MostProductProvince {
  def main(args: Array[String]): Unit = {
    val product = Source.fromFile("D:\\IdeaProjects\\scala\\input\\product.txt","utf-8").getLines().toList

    //val productProvince = product.map(x=>x.split("\t")).filter(x=>x.length>=6).map(x=>(x(4),x(0))).distinct
    //采用模式匹配
    val productProvince = product.map(x=>{
      x.split("\t")
      //找到数据符合的，然后转为元组才可以用模式匹配
    }).filter(x=>x.size>=6).map(arr=>(arr(0),arr(1),arr(2),arr(3),arr(4),arr(5)))
     .map({
      case(farm,price,date,market,province,city)=>(province,farm)
    }).distinct

    //山西 香菜
    val result = productProvince.groupBy(x=>x._1).map(x=>(x._1,x._2.size)).toList.sortBy(x=>x._2).reverse

    //取最多的三个省
    //val top3 = result.dropRight(result.size-3)
    val top3 = result.take(3)
    top3.foreach(println)
    /*
    (北京,169)
    (江苏,167)
    (山东,134)
     */
    }
}
