package com.atguigu.charpter3

/**
  * @author Shelly An
  * @create 2020/7/22 10:34
  *        继承的好处:
  *        1. 多态
  *        2. 代码复用
  *
  *        java中访问修饰符
  *        private   protect   public  default
  *
  *        scala中访问修饰符：
  *        private   protect   default(相当于public）
  *        子类不能继承父类private修饰的成员属性和方法
  *
  *        不能被继承
  *        1. 父类私有的成员或者方法
  *        2. final定义的class不能被继承
  *
  *        不能被override
  *        1. var定义的成员属性
  *        2. final修饰的成员属性不能被重写
  */
object $04_Extends {
  class Person{
    private val name:String = "lisi"
    var age:Int = _
    val address:String = "shenzhen"
    def say()= println("hello")
  }

  class Student1 extends Person{
    //未继承成员属性的定义
    val name:String = "jason"
    //已继承成员属性和方法的重写 val定义的无法重写
    override val address: String = "shanghai"
    override def say(): Unit = println("hello , my name is lisi")
  }

  def main(args: Array[String]): Unit = {
    val student:Person = new Student1
    student.age = 19
    //println(student.name)
    student.say()
    println("my age is "+student.age)
    println("I come from " + student.address)
  }
}
