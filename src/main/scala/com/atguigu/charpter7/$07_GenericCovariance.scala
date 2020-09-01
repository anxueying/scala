package com.atguigu.charpter7

/**
  * @author Shelly An
  * @create 2020/7/28 14:15
  */
object $07_GenericCovariance {
  class B()
  class C extends B()
  implicit val person = new Person[String]
  class Person[T]{def sayHello(x:T)=println(x)}
  class School[+T]
  class City[-T]
  def main(args: Array[String]): Unit = {
    var list1 = List[B](new B)
    var list2 = List[C](new C)

    list1 = list2
    //class com.atguigu.charpter7.$07_Generic$C
    list1.foreach(x=>println(x.getClass))

    /**
      * 非变
      */
    var p1 = new Person[B]
    var p2 = new Person[C]
    //p1 = p2
    /**
      * 协变  s1是s2的父类
      */
    var s1 = new School[B]
    var s2 = new School[C]
    s1 = s2
    println(s1.getClass) //class com.atguigu.charpter7.$07_Generic$School
    /**
      * 逆变 c2是c1的父类
      */
    var c1 = new City[B]
    var c2 = new City[C]
    //c1 = c2 不行的 因为是逆变
    c2 = c1
    println(c2.getClass) //class com.atguigu.charpter7.$07_Generic$City

  }
}
