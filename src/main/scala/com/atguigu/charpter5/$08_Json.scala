package com.atguigu.charpter5

import com.atguigu.charpter5.PMTJsonRootBean

import scala.collection.mutable.ArrayBuffer
import scala.io.Source



/**
  * @author Shelly An
  * @create 2020/7/25 16:00
  *
  */
object $08_Json {
  def main(args: Array[String]): Unit = {
    //D:\IdeaProjects\scala\input\test.json
    //D:\IdeaProjects\atguiguHadoop\myhadoop\pmt.json
    val source = Source.fromFile("D:\\IdeaProjects\\scala\\input\\test.json")
    val strings = source.getLines()
    val list = strings.toList.map(x => FindIPLocation.getIP(x))//.filter(x=>x!=null)
//    var num = 1
//    list.foreach(x=>{
//      if (x==null) {
//        println(num+"为空对象，请检查")
//      }
//      println(num +"\t"+ x.getProcessnode)
//      num += 1
//    })

    //需求1 原始请求
    //    val taskResult1 = list.filter(x=>x.getRequestmode=="1"&&x.getProcessnode.toInt>=1)
    //                                        .map(x=>(x.getProvince,x)).groupBy(x=>x._1)
    //                                        .map(x=>(x._1,x._2.length)).mkString(",")
    /**
      * 通用方法
      *
      * @param list json解析出的对象数组
      * @param f    需求函数
      * @return 结果
      */
    def info(list: List[PMTJsonRootBean], f: PMTJsonRootBean => Boolean) = {
      list.filter(f).map(x => (x.getProvince, x)).groupBy(x => x._1).map(x => (x._1, x._2.length))//.mkString(";").replaceAll(" -> ",":")
    }

    /**
      * 需求1  原始请求
      */
    val fun1 = (bean: PMTJsonRootBean) => {
      bean.getRequestmode == "1" && bean.getProcessnode.toInt >= 1
    }

    //println(info(list, fun1))

    /**
      * 需求2 有效请求
      */
    val fun2 = (bean: PMTJsonRootBean) => {
      bean.getRequestmode == "1" && bean.getProcessnode.toInt >= 2
    }

    //println(info(list, fun2))

    /**
      * 需求3 广告请求
      */
    val fun3 = (bean: PMTJsonRootBean) => {
      bean.getRequestmode == "1" && bean.getProcessnode.toInt == 3
    }

    //println(info(list, fun3))

    /**
      * 需求4 参与竞价数
      */
    val fun4 = (bean: PMTJsonRootBean) => {
      bean.getAdplatformproviderid.toInt >= 100000 &&
        bean.getIseffective == "1" &&
        bean.getIsbilling == "1" &&
        bean.getIsbid == "1" &&
        bean.getAdorderid != "0"
    }


    /**
      * 需求5 竞价成功数
      */
    val fun5 = (bean: PMTJsonRootBean) => {
      bean.getAdplatformproviderid.toInt >= 100000 &&
        bean.getIseffective == "1" &&
        bean.getIsbilling == "1" &&
        bean.getIswin == "1"
    }

    /**
      * 需求6 （广告主）展示数
      */
    val fun6 = (bean:PMTJsonRootBean) =>{
      bean.getRequestmode == "2" && bean.getIseffective == "1"
    }

    /**
      * 需求7 （广告主）点击数
      */
    val fun7 = (bean:PMTJsonRootBean) =>{
      bean.getRequestmode == "3" && bean.getIseffective == "1"
    }

    /**
      * 需求8 （媒介）展示数
      */
    val fun8 = (bean:PMTJsonRootBean) =>{
      bean.getRequestmode == "2" && bean.getIseffective == "1" && bean.getIsbilling == "1"
    }

    /**
      * 需求9 （媒介）点击数
      */
    val fun9 = (bean:PMTJsonRootBean) =>{
      bean.getRequestmode == "3" && bean.getIseffective == "1"  && bean.getIsbilling == "1"
    }

    /**
      * 需求10 DSP广告消费
      */
    val fun10 = (bean:PMTJsonRootBean) =>{
      bean.getAdplatformproviderid.toInt>=100000 &&
        bean.getIseffective == "1"  &&
        bean.getIsbilling == "1" &&
        bean.getIswin == "1" &&
        bean.getAdorderid.toInt >200000 &&
        bean.getAdcreativeid.toInt > 200000
    }

    def all_info(functions: Array[PMTJsonRootBean=>Boolean],list: List[PMTJsonRootBean]) ={
      val iterator = functions.iterator
      var funcNum = 0
      val buffer = ArrayBuffer[Map[String,Int]]()
      while (iterator.hasNext) {
        val func = iterator.next()
        funcNum += 1
        //println(s"----需求${funcNum}-----")
        val resultMap = info(list, func)
        buffer.append(resultMap)
      }
      buffer
    }
    //函数数组
    val funcArray = Array[PMTJsonRootBean=>Boolean](fun1,fun2,fun3,fun4,fun5,fun6,fun7,fun8,fun9,fun10)
    //调用所有函数
    val results = all_info(funcArray,list)
    //ArrayBuffer(Map(山东省 -> 1), Map(山东省 -> 1), Map(山东省 -> 1), Map(陕西省 -> 1, 山东省 -> 1), Map(), Map(), Map(陕西省 -> 1), Map(), Map(陕西省 -> 1), Map())
    //ArrayBuffer((Set(山东省),List(1)), (Set(山东省),List(1)), (Set(山东省),List(1)), (Set(陕西省, 山东省),List(1, 1)), (Set(),List()), (Set(),List()), (Set(陕西省),List(1)), (Set(),List()), (Set(陕西省),List(1)), (Set(),List()))
    //Array[(陕西省 -> 0,0,0,1,0,0,1,0,1,0),(山东省 -> 1,1,1,1,0,0,0,0,0,0)]
    val tuples = results.map(x=>(x.keySet,x.values.toList)).map(x=>(x._1,x._2.map(x=>{if(x.isNaN) 0 else x})))


  }
}
