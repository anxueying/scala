package com.atguigu.charpter4

/**
  * @author Shelly An
  * @create 2020/7/27 9:13
  */
object $08_Tuple {
  def main(args: Array[String]): Unit = {
    /**
      * 元组：工作中用的最多的
      * 元素最多只能有22个
      *
      * 定义语法
      * 1. (值1，值2，...)
      * 2. 二元元祖  K->V 键值对 （很多输出格式都是这种）
      *
      * 元组不可以被改变
      * 元组获取值的方式：元组的变量名._角标
      */
    val tuple = ("zhang",18,"male")

    //以下两者等价
    val tuple1 = ("zhang",18)
    val tuple2 = "zhang"->18

    val list = List[(String,Int,String)](("zhang",18,"male"),("li",19,"female"))

    //获取元组的值
    val value1 = tuple._1
    val value2 = tuple._2
    val value3 = tuple._3
    println(value1+"\t"+value2+"\t"+value3)

    //元组迭代器 用的较少
    val iterator = tuple.productIterator
    for (elem <- iterator) {
      println(elem)
    }

    list.foreach(x=>println(x._2))

    //case class
    //(school_name,(class_name,(student_name,student_age)))
    val list2 = List[(String,(String,(String,Int)))] (
      ("aa",("1001",("zhangsan",20))),
      ("bb",("1002",("wangwu",30))),
      ("aa",("1001",("lisi",50)))
    )

    list2.foreach ({
      case (school_name,(class_name,(student_name,student_age))) => println(student_name)
    })

  }
}
