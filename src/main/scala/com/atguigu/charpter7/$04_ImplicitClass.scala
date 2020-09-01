package com.atguigu.charpter7

/**
  * @author Shelly An
  * @create 2020/7/28 10:42
  */
object $04_ImplicitClass {
  def main(args: Array[String]): Unit = {
    /**
      * 隐式转换类
      * 1. 定义方式：implicit class xx
      * 2. 必须定义在其他的class、object、package object中
      */
    //让dog说话
    val dog = new Dog
    dog.sayHello()
  }


  //implicit def dogToPerson(d:Dog):Person = new Person  //不用隐式转换类，用隐式转换方法也可

  //隐式转换类
  implicit class Person(d:Dog){
    def sayHello(){println("Hello")}
  }
  class Dog{
    def eat(){println("汪汪")}
  }
}
