package com.atguigu.charpter1

import scala.util.control.Breaks._
/**
  * @author Shelly An
  * @create 2020/7/20 15:38
  * java   continue  结束当次循环，开始下一次循环
  *        break 跳出循环
  * scala中没有continue和break
  * 跳出循环
  *   1. return
  *   2. 抛异常
  *
  */
object $10_while {
  def main(args: Array[String]): Unit = {
    var i = 0
    while (i <= 10) {
      println(s"i=${i}")
      //return 10
      //try在外面是break，在里面是continue，scala内部帮助实现了一个包
      i = i + 1
    }

    do{
      println(s"i=${i}")
      i = i + 1
    } while (i<=20)

    //continue
    while (i <= 30) {
      //捕获异常
      breakable{
        //正常代码
        i = i + 1
        //抛出异常
        if(i==25) break()
        //正常代码
        println(s"continue=${i}")
      }
    }

    //break
    //捕获异常
    breakable{
      while (i <= 40) {
        //抛出异常
        if(i==35) break()
        //正常代码
        println(s"break=${i}")
        i = i + 1
      }
    }

  }
}
