package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/22 9:09
  */
object $13_Abstract {
  def main(args: Array[String]): Unit = {
    /**
      * 控制抽象：
      * 可以简单看成是一个函数，函数比较特殊，它是一个代码块
      *
      */

    m2({
      println("my")
      10
    })


  }

  def m2(x: Int) = println(x)

  def m4(func: =>Unit) = {
    func
    func
    func
  }

  /**
    * 控制抽象 => 类型都可
    * @param op1   =>Boolean  传入代码块（返回值为boolean）
    * @param op2   =>Unit  传入代码块（无返回值）
    */
  def myWhile(op1: =>Boolean)(op2: =>Unit):Unit ={
    if(op1) {
      op2
      myWhile(op1)(op2)
    }
    else return
  }
}
