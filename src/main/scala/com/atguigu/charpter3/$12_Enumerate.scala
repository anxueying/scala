package com.atguigu.charpter3

/**
  * @author Shelly An
  * @create 2020/7/28 19:56
  */
object $12_Enumerate {
  def main(args: Array[String]): Unit = {
    println(Color.RED.id+"\t" + Color.RED)
  }

  //先初始化，因此此print先执行
  println("初始化")
}

/**
  * JDK1.5之后提供的
  * 枚举类：需要继承Enumeration
  */
object Color extends Enumeration{
  val RED = Value(1,"red")
  val YELLOW = Value(2,"yellow")
  val BLUE = Value(3,"blue")
}

/**
  * 应用类
  */
object Application extends App{
  println("我是应用类")
}