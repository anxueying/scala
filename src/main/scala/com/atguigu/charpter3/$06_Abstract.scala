package com.atguigu.charpter3

/**
  * @author Shelly An
  * @create 2020/7/22 14:17
  */
object $06_Abstract {
  //抽象类 既可以定义抽象方法、也可以定义具体方法
  //抽象类 既可以定义抽象属性、也可以定义具体属性
  abstract class Person{
    //具体方法
    def hello() {println("hello")}
    //抽象方法
    def response():String
    //抽象方法 不定义返回值类型 默认为Unit
    def meet()

    //抽象属性
    val name:String
  }

  class Student extends Person{
    //实现抽象方法的时候，override可加可不加
    def response(): String = "Hello,nice to meet you"
    override def meet(): Unit = println("nice to meet you")

    //具体的方法必须要加上override
    override def hello(): Unit = println("Hello!!")

    override val name: String = "张三"
  }

  def main(args: Array[String]): Unit = {
    val s:Person = new Student
    s.hello()
    println(s.response())
    s.meet()
    println(s.name)

    //匿名类 其类型是该类的全类名
    val person = new Person {
      override def response(): String = "ByeBye"

      override def meet(): Unit = println("bye")

      override val name: String = "李四"
    }
    println(person.name)
  }
}
