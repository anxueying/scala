package com.atguigu.charpter7

import scala.io.{BufferedSource, Source}
import java.io.File

/**
  * @author Shelly An
  * @create 2020/7/28 10:22
  */
object $02_ImplicitMethod {
  def main(args: Array[String]): Unit = {
    /**
      * 隐式转换：最大的作用是解耦，悄悄的将一个类型转为另一个类型
      * 不好维护，只会使用spark中几个固定的隐式转换，别的不用
      * 1. 定义方式
      *  implicit def 方法名(参数名：待转换类型）：转换类型={}
      * 2. 使用的时机
      *   ① 变量类型与目标类型不一致
      *   ② 如果对象使用了不属于对象本身的方法
      * 3. 作用域
      *   ① 必须定义在object中，或者projectObject。
      *   ② 想使用包，必须在作用域。会自动使用。否则
      *       - 如果定义在object中，需要import包
      *       - 如果定义在class中，则需extend class
      */
    val a:Int = 10.0
    println(s"a = ${a}")

    //隐式调用file2BufferSource
    val file = new File("input/test.txt")
    file.foreach(println)
  }

  /**
    * 隐式转换方法
    * @param x 待转换类型
    * @return 转换类型
    */
  implicit def m1(x:Double):Int={
    println(s"x=${x}")
    x.toInt
  }

  /**
    * 读文件
    * @param file java
    * @return 文件
    */
  implicit def file2BufferSource(file:File):Iterator[String]={
    Source.fromFile(file).getLines()
  }
}
