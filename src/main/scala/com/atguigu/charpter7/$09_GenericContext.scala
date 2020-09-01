package com.atguigu.charpter7


/**
  * @author Shelly An
  * @create 2020/7/28 14:38
  */
object $09_GenericContext {

  implicit val person = new Person[String]

  class Person[T] {

    def sayHello(x: T) = println(s"${x}")
  }

  def main(args: Array[String]): Unit = {
    m1[String]("汪汪")
    m2[String]("喵喵")
  }


  def m1[T](a: T)(implicit x: Person[T]) = {
    x.sayHello(a)
  }

  //上下文

  def m2[T: Person](a: T) = {
    val person = implicitly[Person[T]]
    person.sayHello(a)
  }
}
