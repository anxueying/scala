package com.atguigu.homework3

import java.io.File

import com.atguigu.homework3.$01_Classify.printToFile

import scala.io.Source

/**
  * @author Shelly An
  * @create 2020/7/28 9:07
  *        1、查看每个品牌的商品数量，降序排序
  */
object $02_Demand {
  def main(args: Array[String]): Unit = {
    val data = Source.fromFile("Output/taobaoResult.csv").getLines().toList
    //((﻿2016/11/14,A18164178225,CHANDO/自然堂 雪域精粹纯粹滋润霜50g 补水保湿 滋润水润面霜,139,26719,2704,自然堂),((护肤品,面霜类),面霜))
    val productNum = data.map(
      x=>x.replaceAll("(","").replaceAll(")","").split(",")
    ).map({
      case Array(update_time, id, title, price, sale_count, comment_count, shop_name,first_type,second_type,third_type)=>
        (shop_name,id)
    }).distinct
    val shopOrderbyProNum = productNum.groupBy(_._1).map(x=>(x._1,x._2.length)).toList.sortBy(_._2).reverse
    printToFile(new File("Output/taobaoDemand1.csv")) {
      p => shopOrderbyProNum.foreach(p.println) // avgs.foreach(p.println)
    }
  }
}
