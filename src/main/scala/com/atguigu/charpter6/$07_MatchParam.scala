package com.atguigu.charpter6

/**
  * @author Shelly An
  * @create 2020/7/27 15:24
  */
object $07_MatchParam {
  def main(args: Array[String]): Unit = {
    /**
      * 赋值时匹配 基本也用不到
      */
    //元组
    val (name,age)=("zhangsan",10)
    println(age)
    //list
    val List(x,y,_*) = List(1,2,3,4)
    println(x)

    /**
      * for循环   替代品： 有返回值  map  无返回值 foreach
      * 因此下面的基本不用
      */
    val map = Map[String,Int](("zhngsan",20))
    //for循环赋值匹配
    for ((name,age) <- map) {
      println(name)
    }

  }
}
