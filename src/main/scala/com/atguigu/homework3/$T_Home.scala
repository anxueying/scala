package com.atguigu.homework3

import java.io.File
import scala.io.Source
import scala.util.Try

/**
  * @author Shelly An
  * @create 2020/7/28 16:10
  */
object $T_Home {
  def main(args: Array[String]): Unit = {
    val products = Source.fromFile("input/taobao.txt").getLines().toList
    val productType = Source.fromFile("input/producttype.txt").getLines().toList
    val source = addType(products, productType)
  }

  /**
    * 补充产品的主类和子类
    *
    * @param product     产品
    * @param productType 类别
    */
  def addType(product: List[String], productType: List[String]): Unit = {
    //2. 处理类别  List[（主类、子类、关键词）]
    val productSource = productType.flatMap(line => {
      val arr = line.split("\t")
      val mainType = arr(0)
      val subType = arr(1)
      arr.drop(2).map(x => (mainType, subType, x))
    }
    )

    val result = product.map(line => {
      //1. 切割 2016/11/14,A18164178225,CHANDO/自然堂 雪域精粹纯粹滋润霜50g 补水保湿 滋润水润面霜,139,26719,2704,自然堂
      val arr = line.split(",")
      val title = arr(2)

      //3. 判断标题是否包含关键词
      val containsList = productSource.filter(x => line.contains(x._3))
      //如果标题中未包含关键词

      if (line.contains("男")) {
        ("男士专用", containsList.head._2, arr(0), arr(1), arr(2), arr(3), arr(4), arr(5), arr(6))
      }else if((!containsList.isEmpty)&&(!line.contains("男"))){
        ("其他","其他", arr(0), arr(1), arr(2), arr(3), arr(4), arr(5), arr(6))
      } else{
        (containsList.head._1, containsList.head._2, arr(0), arr(1), arr(2), arr(3), arr(4), arr(5), arr(6))
      }
    })

    printToFile(new File("Output/taobaoResultTeacher.csv")) {
      p => result.foreach(p.println)
    }


  }

  /**
    * 1、查看每个品牌的商品数量，降序排序
    * @param source  addType处理结果
    * @return (品牌，商品数量） 降序
    */
  def query1(source:List[(String,String,String,String,String,String,String,String,String)])={
//    source.map(x=>(x._7,x._3)).distinct.groupBy(_._1).map(x=>(x._1,x._2.size))
  }

  def query2(source:List[(String,String,String,String,String,String,String,String,String)])={
//    source.map(x=>(x._7,x._5)).distinct.groupBy(_._1).map(x=>(x._1,x._2.map(y=>Try(y._2.toLong).getOrElse(0)).sum))
  }

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f);
    p.write("一级分类,二级分类,update_time,id,title,price,sale_count,comment_count,店名\n")
    p.write("rating_avg\n")
    try {
      op(p)
    }
    finally {
      p.close()
    }
  }
}
