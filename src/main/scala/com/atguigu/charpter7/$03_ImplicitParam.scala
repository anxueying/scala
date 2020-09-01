package com.atguigu.charpter7

/**
  * @author Shelly An
  * @create 2020/7/28 10:36
  */
object $03_ImplicitParam {
  def main(args: Array[String]): Unit = {
    /**
      * 隐式转换参数
      * 1. 定义方式
      *   step1: 定义一个方法，方法中指定那个参数为后续的隐式转换传入参数
      *           def m1(x:Int,y:Int)(implicit z:Double)
      *   step2: 定义一个隐式参数 implicit val x:Double = 20
      * 2. 作用域和定义位置和隐式转换方法一样
      */

    println(m3(1, 2))  //13 自动传递
    //println(m3(1, 2)(10))  也可以手动传
  }
  implicit val aa:Double = 10.0

  def m3(x:Int,y:Int)(implicit c:Double)={
    x+y+c
  }
}
