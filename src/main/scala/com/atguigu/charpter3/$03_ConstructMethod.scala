package com.atguigu.charpter3

import scala.beans.BeanProperty

/**
  * @author Shelly An
  * @create 2020/7/22 10:09
  */
object $03_ConstructMethod {
  /**
    * scala中构造器分为两种
    * 1. 主构造器 在类名后面的() 表示主构造器
    *    参数定义方式： val/var 属性名：属性类型
    *     如果有val/var, 则该参数就是class成员属性
    *     如果没有，则在构造器中使用至少一次，才是class成员属性，
    *               但是该属性为private，并且scala不会提供其默认的get/set方法。因此不可以在外部使用。
    *               如果没有使用，则该参数相当于不存在。
    *
    *    默认值。带名参数。
    * 2. 辅助构造器
    * 在class内部定义
    * 定义方式：第一行必须调用其他的辅助构造器或主构造器，参数名前不可加val/var
    * 定义语法 def this(参数名:参数类型,....) = {
    *     this()
    * }
    */

  class Person(name:String,val age:Int,var address: String){
    //在class中，var定义的成员属性，可以通过_赋予默认值，且必须指定数据类型
    @BeanProperty
    var sex:Int = _
    //  sex, className也是成员属性
    val className:String = "大数据"

    def say() = println(s"name=${name}")

    //辅助构造器
    def this(name: String){
      this(name,10,"shenzhen")
    }

    def this(name:String,className: String,sex:Int){
      this(name)
    }
  }

  class Student(var name: String = "",val age:Int)


  def main(args: Array[String]): Unit = {
    val shelly = new Person("shelly",18,"shenzhen")
    shelly.say()

    val student = new Student(age = 10)
    student.name = "张三"
    println(student.name)

    //辅助构造器
    val jason = new Person("Jason")
    println(jason.address)
  }
}
