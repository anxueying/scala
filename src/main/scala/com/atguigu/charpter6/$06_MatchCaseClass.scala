package com.atguigu.charpter6

import com.alibaba.fastjson.JSON


/**
  * @author Shelly An
  * @create 2020/7/27 14:34
  */
object $06_MatchCaseClass {
  case class Person(name:String,var age:Int,sex:Sex)  //val是默认的，可以不写
  /**
    * 枚举值
    */
  abstract class Sex()
  //样例对象一般作为枚举值使用
  case object Man extends Sex
  case object Woman extends Sex

  //元组嵌套过多时
  case class School(name:String,clazz:Clazz)
  case class Clazz(name:String,student:Student)
  case class Student(name:String,age:Int)

  def main(args: Array[String]): Unit = {
    /**
      * 样例类  定义语法：
      * case class 类名([val/var] 属性名:属性类型,属性名:属性类型,....)
      *
      * 一般用于封装数据
      */
    val zhangsan = Person("zhangsan",19,Man)
    println(zhangsan.name + "\t" + zhangsan.age +"\t"+zhangsan.sex)

    //匹配样例类
    zhangsan match{
      case Person(name, age, sex)=> println(s"name = ${name}, age=${age}, sex = ${sex}")
    }

    val shelly = School("atguigu",Clazz("0421",Student("shelly",18)))
    shelly match{
      case school: School => {
        val schoolName = school.name
        val className = school.clazz.name
        val studentName = school.clazz.student.name
        val studentAge = school.clazz.student.age
        println(schoolName+"\t"+className+"\t"+studentName+"\t"+studentAge)
      }
    }

    val personjson =
      """
        |{"name":"zhangsan","age":20}
      """.stripMargin
    //解析Json
    val person = JSON.parseObject(personjson,classOf[Person])
    println(person.name)

    //普通类的模式匹配 必须定义unpply方法
    val a = new ABC("special",19)
    a match{
      case a: ABC => println(a.name+"今年"+a.age)
      case _ => println("未匹配")
    }

    //定义在本object外的，还需定义apply方法
    val d = new DEF("def",10)
    d match {
      case d: DEF => println(d.name+"今年"+d.age)
      case _ => println("未匹配")
    }

  }

  class ABC(val name:String,val age:Int)
  //定义伴生对象
  object ABC{
    //None 没有值
    //Some(..) 有值
    /**
      *定义unapply方法
      * @param arg
      * @return 如果返回none，则    ；如果返回Some，则
      */
    def unapply(arg: ABC): Option[(String, Int)] = Some(arg.name,arg.age)
  }
}

class DEF(val name:String,val age:Int)
//定义伴生对象
object DEF{
  def apply(name: String, age: Int): DEF = new DEF(name,age)
  //None 没有值
  //Some(..) 有值
  /**
    *定义unapply方法
    * @param arg
    * @return 如果返回none，则    ；如果返回Some，则
    */
  def unapply(arg: DEF): Option[(String, Int)] = Some(arg.name,arg.age)
}