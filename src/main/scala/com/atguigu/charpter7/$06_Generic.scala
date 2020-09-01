package com.atguigu.charpter7

/**
  * @author Shelly An
  * @create 2020/7/28 14:03
  */
object $06_Generic {
  def main(args: Array[String]): Unit = {
    /**
      * 泛型
      */
    val i = m1(10)

    val xiaoxiao = new Children[String,Int]("xiaoxiao",8)
    xiaoxiao.address = "shenzhen"
    xiaoxiao.m1("hello")
  }

  class Children[T,A](name:T,age:A){
    var address:T= _
    def m1(x:T) =println(x)
  }


  //泛型方法
  def m1[A](x:A):String={
    x.toString
  }
}
