package com.atguigu.charpter3

import scala.beans.BeanProperty

/**
  * @author Shelly An
  * @create 2020/7/22 14:11
  */
object $05_Extends {

  class Person{
    @BeanProperty
    val name:String = "person"
  }

  class Student extends Person {
    @BeanProperty
    override val name: String = "student"
  }

  class Worker extends Person {
    override val name: String = "worker"
  }

  def main(args: Array[String]): Unit = {
    val s:Person=new Student
    //scala自动生成get、set方法
    println(s.name)  //student
    println(s.getName) //student

    /**
      * 在java中，多态指的是方法的多态
      * 在scala中，多态指的是属性和方法的多态
      */
    val w:Person=new Worker
    println(w.name)  //worker
    println(w.getName)  //worker
  }
}
