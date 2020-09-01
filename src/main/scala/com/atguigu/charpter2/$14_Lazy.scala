package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/22 9:23
  *        在一定程度上节省内存空间
  */
object $14_Lazy {
  def main(args: Array[String]): Unit = {
    //只创建引用，当使用时才会赋值
    lazy val name:String = """SQL"""
    val age:Int = 20

    println(age)
  }
}
