package com.atguigu.charpter1

/**
  * @author Shelly An
  * @create 2020/7/20 11:28
  */
object $08_if {
  def main(args: Array[String]): Unit = {
    /**
      * 1. 单 if
      * 2. 双 if-else
      * 3. 多 if-else if- else
      * 4. 嵌套 if{if...}
      *
      * scala中if-else是有返回值的，返回值就是满足条件的分支的{}的结果只
      */

    var a = 10

    var result = if (a == 10) {
      println("hello")
      "end"
    }

    println(result)

    result = if (a > 10) {
      println(">10")
      20
    } else {
      println("<=10")
      if (a <= 5) {
        println("<=5")
      } else if (a <= 8) {
        println("<=8")
      } else {
        println("不套了")
      }
    }

    //方法
    val unit = m1(1, 10)

  }

  def m1(x: Int, y: Int): String = {
    if (x <= 5) {
      y + 1
    } else if (x <= 8) {
      x + 1.1
    } else {
      println("*" * 10)
    }
    "end"
  }
}
