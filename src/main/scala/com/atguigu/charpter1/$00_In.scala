package com.atguigu.charpter1

import scala.io.{Source, StdIn}

/**
  * @author Shelly An
  * @create 2020/7/24 9:27
  */
object $00_In {
  def main(args: Array[String]): Unit = {
    //从控制台中获取输入，转化为int值
    val age = StdIn.readInt()
    println(s"age = ${age}")

    //从相对路径中读取文件数据
    //idea中的相对路径是以Project的根（root）路径为基准
    val strings = Source.fromFile("input/word.txt").getLines()
    while (strings.hasNext) {
      println(strings.next())
    }
  }
}
