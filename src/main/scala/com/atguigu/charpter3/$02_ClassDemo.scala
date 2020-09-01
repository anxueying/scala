package com.atguigu.charpter3

import scala.beans.BeanProperty

/**
  * @author Shelly An
  * @create 2020/7/22 9:34
  */
object $02_ClassDemo {
  class Person{
    //定义成员变量
    @BeanProperty
    val name:String="Shelly"
    //封装数据 case Class XX(name:String,age:Int)
    @BeanProperty  //加上注解，就有了java的get、set方法 因为阿里的工具包要求成员变量必须有对应的get/set方法
    var age:Int = 18
    //scala中会对成员变量生成默认的get、set方法



    def say() = println (s"name = ${name}")
    def say(string: String) = println (string + s"name = ${name}")

    val func = (x:Int,y:Int)=>x+y
    //val func = (x:Int,y:Int,z:Int)=>x+y+z  是不行的


  }

  def main(args: Array[String]): Unit = {
    val person = new Person
    println(person.name+"\t"+person.age)

    person.age=20
    println(person.name+"\t"+person.age)

    //成员变量的set方法 $eq其实就是=
    person.age_$eq(200)

    //成员变量的get方法 是一个没有参数()的成员变量方法，返回值是该成员变量的值
    println(person.getAge())

    person.say()
    person.say("my name is")
  }
}
