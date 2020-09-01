package com.atguigu.charpter4

/**
  * @author Shelly An
  * @create 2020/7/27 12:01
  */
object $06_ImmutableSet {
  def main(args: Array[String]): Unit = {
    /**
      * 不可变Set
      * 创建方式：Set[数据类型](初始值)
      *
      * set和list区别
      * 1. 无序  有序
      * 2. 不重复  重复
      */
      //声明
    val s1 = Set[Int](1,2,3,4,3)
    println(s1) //(1, 2, 3, 4)

    //添加元素
    val s2 = s1 +10
    println(s2) //(10, 1, 2, 3, 4)

    val s3 = s1++Set(7,3,2)
    println(s3) //(1, 2, 7, 3, 4)

    //不能更新

    //删除
    val s4 = s3-7
    println(s4) //(1, 2, 3, 4)

    val s5 = s4 -- List(1,2)
    println(s5) //(3, 4)

    //查看
    println(s5(0))  //false
    println(s5(3))  //true
  }
}
