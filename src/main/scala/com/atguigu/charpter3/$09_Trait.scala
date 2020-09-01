package com.atguigu.charpter3

/**
  * @author Shelly An
  * @create 2020/7/22 16:09
  *         单继承
  */
object $09_Trait {

  /**
    * 创建Trait的语法：trait 特质名{}
    * 在特质中既可以创建抽象方法，也可以创建具体方法
    * 在特质中既可以创建抽象属性，也可以创建具体属性
    */
  trait Logger {
    val name: String
    val age: Int = 20

    def mm(): String

    def nn() = {
      print("logger")
    }
  }

  class Log

  trait Logger2 {
    def nn() = {
      print("logger2")
    }
  }

  /**
    * class混入特质：
    * 1. 如果要继承父类（是类哦，不是特质！！），用with
    * 2. 如果不继承父类（是类哦，不是特质！！），那么第一个特质用extends 其他特质用with
    */

  //Log  父类
  class LoggerInfo extends Log with Logger {
    override val name: String = "shelly"

    override def mm(): String = "mm"

  }

  //Logger 第一个特质
  class LoggerDebug extends Logger with Logger2 {
    override val name: String = "shelly"

    override def mm(): String = "mm"

    //如果不重写，scala会混乱
    //super 指的是最后一个特质
    override def nn(): Unit = super.nn()
  }


  def main(args: Array[String]): Unit = {
    val info = new LoggerDebug

    println(info.mm())
    info.nn()
    println("\t" + info.name + "\t" + info.age)

    //对象混入特质 对于解耦非常有用
  }
}
