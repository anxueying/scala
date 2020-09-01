package com.atguigu.homework2

import scala.io.Source

/**
  * @author Shelly An
  * @create 2020/7/27 15:34
  *        求出哪些省份没有农产品市场
  *
  *        香菜	2.80	2018/1/1	山西汾阳市晋阳农副产品批发市场	山西	汾阳
  */
object $01_NoAgriculturalMarket {
  def main(args: Array[String]): Unit = {
    val product = Source.fromFile("D:\\IdeaProjects\\scala\\input\\product.txt","utf-8").getLines().toList
    val haveProvince = product.map(x=>x.split("\t")).filter(x=>x.length>=6).map(x=>x(4)).toSet

    val allProvince = Source.fromFile("D:\\IdeaProjects\\scala\\input\\allprovince.txt","utf-8").getLines().toSet

    val noProvice = allProvince -- haveProvince

    println(noProvice)  //河北去不掉
    //Set(香港, 澳门, 海南, 西藏, 云南, ﻿河北, 贵州, 台湾)
  }
}
