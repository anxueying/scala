package com.atguigu.charpter4
import scala.collection.mutable.Set
/**
  * @author Shelly An
  * @create 2020/7/27 12:45
  */
object $07_MutableSet {
  def main(args: Array[String]): Unit = {
    val s1 = Set[Int](1,2,3,4)

    //+和-都是一样的，就不赘述了 查看也是一样的
    s1+=10
    println(s1) //(1, 2, 3, 10, 4)

    s1 ++= List(2,4,6,8)
    println(s1) //(1, 2, 6, 3, 10, 4, 8)

    //true 添加  false  删除
    s1.update(0,true)
    println(s1) //(0, 1, 2, 6, 3, 10, 4, 8)

    s1.update(10,false)
    println(s1) //(0, 1, 2, 6, 3, 4, 8)

  }
}
