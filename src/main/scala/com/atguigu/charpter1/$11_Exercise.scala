package com.atguigu.charpter1

/**
  * @author Shelly An
  * @create 2020/7/24 17:01
  */
object $11_Exercise {
  def main(args: Array[String]): Unit = {
    val num = 9
    for {
      i <- 1 to 2*num by 2;j = (18-i)/2
    } {
      println(" "*j+ "*"*i)
    }
  }
}
