package com.atguigu.homework3

import java.io.File

import scala.io.Source

/**
  * @author Shelly An
  * @create 2020/7/27 19:32
  */
object $01_Classify {
  def main(args: Array[String]): Unit = {
    val data = Source.fromFile("input/taobao.txt").getLines().toList
    val productType = Source.fromFile("D:\\IdeaProjects\\scala\\input\\producttype.txt").getLines().toList

    //三级分类按 ((1,2),3)存好
    val pType = productType.map(x => x.split("\t")).flatMap(x => {
      val key12 = (x(0), x(1))
      val tuples = for (i <- 2 until x.length) yield {
        (key12, x(i))
      }
      tuples.toList
    })

    //处理淘宝数据
    //update_time,id,title,price,sale_count,comment_count,店名
    //2016/11/14,A18164178225,CHANDO/自然堂 雪域精粹纯粹滋润霜50g 补水保湿 滋润水润面霜,139,26719,2704,自然堂
    val dataList = data.map(x => x.split(",")).map({
      case Array(update_time, id, title, price, sale_count, comment_count, shop_name) =>
        (update_time, id, title, price, sale_count, comment_count, shop_name)
    })


    //1. 如果title中出现了男士、男生、男关键字，那么为男士专用
    //2.如果title中包含某一个类别的关键字，那么取出主类与子类补充到元数据
    val infoProduct = dataList.map(x => {
      val t = pType.filter(y => x._3.contains(y._2))
      if (t.isEmpty) {
        (x, (("其他", "其他"), "其他"))
      }else{
        if (x._3.contains("男")) {
          (x, (("男士专用", "男士专用"), "男士专用"))
        } else if (t != null) {
          (x, t(0))
        } else {
          (x, (("其他", "其他"), "其他"))
        }
      }
    })



    printToFile(new File("Output/taobaoResult.csv")) {
      p => infoProduct.foreach(p.println) // avgs.foreach(p.println)
    }
  }

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit)
  {
    val p = new java.io.PrintWriter(f);
    p.write("asin,")
    p.write("rating_avg\n")
    try { op(p) }
    finally { p.close() }
  }
}
