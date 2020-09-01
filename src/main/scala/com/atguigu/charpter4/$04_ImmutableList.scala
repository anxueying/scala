package com.atguigu.charpter4

/**
  * @author Shelly An
  * @create 2020/7/27 11:15
  */
object $04_ImmutableList {
  def main(args: Array[String]): Unit = {
    /**
      * 不可变List
      * 创建方式： List[类型](初始元素)
      */
    val list = List[Int](3,6,8,0,1,3)
    //创建不可变list的另一个方式  case object Nil extends List[Nothing]
    val list_1 = 2::Nil
    /**
      * 当后面需要创建一个空list时，有两种选择
      * 1. 创建可变List
      * 2. 使用Nil创建不可变list，
      *
      * 具体操作如下
      */
    //变量要赋值list类型
      var result:List[Int] = Nil
    //否则这里无法赋值
    result=list


    //新增  ++  ++:  +:  :+
    val list2 = list ++ List(4,1,0)
    println(list2.toBuffer)  //(3, 6, 8, 0, 1, 3, 4, 1, 0)

    val list3 = list2.+:(10)
    println(list3.toBuffer) //(10, 3, 6, 8, 0, 1, 3, 4, 1, 0)

    val list4 = list2.:+(11)
    println(list4.toBuffer) //(3, 6, 8, 0, 1, 3, 4, 1, 0, 11)

    //::添加单个元素 相当于 +:
    val list5 = 20::list2  //不使用.的话必须20在前  和+:一样
    println(list5.toBuffer) //(20, 3, 6, 8, 0, 1, 3, 4, 1, 0)
    //:::添加list中的所有元素  相当于 ++:

    //更新 updated   多了一个d
    val list6 = list.updated(0,1)
    println(list6.toBuffer) //(1, 6, 8, 0, 1, 3)

    //查看
    println(list(1)) //6

    //删除
    //从头删除n个元素 超过数组长度也不会报错
    val list7 = list6.drop(2)
    println(list7.toBuffer) //(8, 0, 1, 3)
  }
}
