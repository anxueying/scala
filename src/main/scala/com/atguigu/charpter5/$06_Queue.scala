package com.atguigu.charpter5

import scala.collection.immutable.Queue
import scala.collection.mutable

/**
  * @author Shelly An
  * @create 2020/7/25 15:55
  */
object $06_Queue {
  def main(args: Array[String]): Unit = {
    /**
      * 定义形式
      * 不可变：  Queue[数据类型](初始元素..)
      */
    val queue = Queue[Int](3,6,8,9)

    //增加元素
    val queue1 = queue ++ List(0,1,7)
    val queue2 = queue1.+:(10)
    val queue3 = queue2.enqueue(87)

    //出列元素
    val quequeData = queue3.dequeue
    println(quequeData) //出列 第一个的元素   (10,Queue(3, 6, 8, 9, 0, 1, 7, 87))

    //查看元素
    queue3.foreach(println)

    //修改
    val queue4 = queue3.updated(0,19) //索引，元素

    /**
      * 定义形式
      * 可变：  Queue[数据类型](初始元素..)
      */
    val mqueue = mutable.Queue[Int](9, 0, 1, 7, 87)

    val i = mqueue.dequeue()
    println(i)  //9
    println(mqueue) //Queue(0, 1, 7, 87)


  }
}
