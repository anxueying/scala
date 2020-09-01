package com.atguigu.charpter3

/**
  * @author Shelly An
  * @create 2020/7/22 9:31
  */
object $01_ClassDemo {

  /**
    * scala中没有public关键字，默认就是public。
    * class中没有内容时，{}可以省略
    */
  class Person

  def main(args: Array[String]): Unit = {
    //()默认构造器   scala中创建对象时，使用默认构造器，则()可以省略
    val person = new Person
    println(person)
  }
}
