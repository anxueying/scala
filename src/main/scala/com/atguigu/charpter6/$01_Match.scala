package com.atguigu.charpter6

import scala.io.StdIn

/**
  * @author Shelly An
  * @create 2020/7/27 10:35
  */
object $01_Match {
  def main(args: Array[String]): Unit = {
    /**
      * 模式匹配基本语法
      *  变量 match{
      *   case 值1 =>
      *   case 值2 =>
      *  }
      */

    val line = StdIn.readLine()
    val result = line match {
      //如果想要表达匹配某个范围的数据，就需要在模式匹配中增加条件守卫。
      case x if x.contains("hello") => "hello" //守卫
      //不用break语句，自动中断case。
      case "nice to meet you" => "nice to meet you too"
      case x if x.contains("bye") => "byebye"
      /*
      如果所有case都不匹配，那么会执行case _ 分支
      相当于把line赋值到_ 这里放x也一样，
      所以要放在所有case语句的末尾。如果放x，
      在这个case中可以继续使用x，如果不想使用这个变量，则用_
      */
      case _ => "ok"

    }

    println(result)

  }
}
