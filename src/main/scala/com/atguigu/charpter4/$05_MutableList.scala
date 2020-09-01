package com.atguigu.charpter4

import scala.collection.mutable.ListBuffer

/**
  * @author Shelly An
  * @create 2020/7/27 11:38
  */
object $05_MutableList {
  def main(args: Array[String]): Unit = {
    /**
      * 可变List
      * 创建方式：ListBuffer[数据类型](初始元素)
      */

    val list = ListBuffer[Int]()

    list.append(10)
    /**
      * 新增方法 参考可变数组的即可
      * 更新
      * update(索引, 元素)  如果下标位置不存在元素，则新增，但不可越界
      * updated(索引, 元素)  生成新的list
      *
      * 删除 参考可变数组
      * remove(n) 从头删除n个元素
      * remove(m,n) 从索引m开始（包含索引m位置）删除多少个元素
      *
      */

      //updated更新
    val list1 = list.updated(0,6)
    println(s"list = ${list}") // ListBuffer(10)
    println(s"list1 = ${list1}") //ListBuffer(6)

    //update更新
    list1.update(0,1)
    println(s"list1 = ${list1}") //ListBuffer(1)

    // 添加元素
    list1 ++= list
    println(s"list1 = ${list1}") //ListBuffer(1, 10)

    //删除元素
    list1.remove(1,1)
    println(s"list1 = ${list1}") //ListBuffer(1, 10)

    //可变--> 不可变
    val imList = list1.toList
    //不可变 --> 可变
    val muList = imList.toBuffer

  }
}
