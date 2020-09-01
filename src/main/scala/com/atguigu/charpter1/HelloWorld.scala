package com.atguigu.charpter1

/**
  * @author Shelly An
  * @create 2020/7/20 9:25
  *        object 相当于java的class，其比较特殊：
  *        1. 单例对象：所有的对象都是一个
  *        2. object中所有的属性和方法都相当于java中static修饰的
  *
  * scala没有static关键字，因此main方法只能写在object中
  *
  */
object HelloWorld {
  /**
    * main方法
    * def  是方法修饰符，define
    *
    * 参数:参数类型
    * java中认为类型比较重要（参数类型 参数），scala中认为变量名比较重要（参数:参数类型）
    *
    * 返回值 Unit 相当于void
    * 格式    : 返回值类型 =
    *
    * @param args
    */
  def main(args: Array[String]): Unit = {
    println("Hello World")
  }
}
