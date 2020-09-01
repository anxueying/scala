package com.atguigu.charpter1

/**
  * @author Shelly An
  * @create 2020/7/20 11:18
  */
object $07_Flow {
  def main(args: Array[String]): Unit = {
    /**
      * java中的流程控制
      * 1. 分支 if-else
      * 2. switch
      * 3. 循环
      *   for
      *   while
      *   do-while
      *
      *   scala 去掉了switch
      */

    //块表达式 {}包裹的一块代码 其返回值是{}最后一行表达式的结果值
    //for 和 while返回Unit
    var result: String = {
      println("hello")
      var s = "fanhuizhi"
      s
    }
  }
}
