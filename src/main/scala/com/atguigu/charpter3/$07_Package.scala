package com.atguigu.charpter06


import scala.util.control.Breaks._
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import com.atguigu.charpter3.yyy.{Person1 => _, _}
import scala.collection.mutable.HashMap
import java.util.{HashMap=>JavaHashMAP}
/**
  * 包的命名规范: 全部小写[数字、字母、_、.]。不能用数字开头。不能使用关键字
  *   一般来说: com.公司名.项目名.模块
  *
  * java:
  *   1、声明包： 文件第一行通过 package 包名
  *   2、导入包 ： 文件声明包之后 通过import 包名
  * scala
  *   1、声明包
  *     1、第一行通过 package 包名
  *     2、通过package 包名{ .. }
  *   2、导入包
  *     1、文件声明包之后 通过import 包名
  *     2、scala可以在任何地方导入包.scala导入包的时候有作用域
  *     3、scala中可以通过 import scala.collection.mutable.{ArrayBuffer,ListBuffer} 一次导入多个类
  *     4、一次导入包中全部类： import scala.collection.mutable._
  *     5、导入包下除开个别类的所有类: import com.atguigu.charpter06.yyy.{Person1=>_,_}
  *     6、给类取别名,一般用于scala和java中有同名类： import java.util.{HashMap=>JavaHashMAP}
  *   3、包对象
  *     package object 包名
  *     包对象中属性、方法、函数可以在包中任何地方使用
  */
object $07_Package {

  def main(args: Array[String]): Unit = {
    //new不出person1
    val person = new Person2

    val arr = new ArrayBuffer[Int]()
    val list = new ListBuffer[Int]

    val map = new HashMap[String,String]()
    val map1 = new JavaHashMAP[String,String]()
  }

  def max(x:Int,y:Int) = {

    var i = 0
    while (i<=5)
    {
      println(s"i=${i}")
      breakable{
        if(i==5) break()
      }
    }
  }
}
class Person

package xxx{
  class Person
}
