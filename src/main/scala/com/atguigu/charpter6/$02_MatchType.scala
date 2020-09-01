package com.atguigu.charpter6

import scala.util.Random

/**
  * @author Shelly An
  * @create 2020/7/27 10:52
  */
object $02_MatchType {
  def main(args: Array[String]): Unit = {
    val list = List[Any]("hello",4,10.9,true,'a')
    val data = list(Random.nextInt(list.length))
    println(s"data = ${data}")

    val returnData = data match {
      case x: String => println("字符串类型")
        "hello "+x
      case x: Int =>
        println("整数类型")
        x * x
      case x: Double =>
        println("浮点数类型")
        x * x
      case x: Boolean =>
        println("布尔类型")
        if (x) 1 else 0
      case x: Char => println("字符类型")
        x.toString
    }

    println(returnData)

  }
}
