package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 14:10
  *        需求：对数组中的元素进行聚合，具体怎么聚合由外部决定
  */
object $06_Reduce {
  def main(args: Array[String]): Unit = {
    val arr=Array[Int](2,3,5,6,7,1,2,7,3,6,20)

    //最初实现方式
    println(reduce(arr, _ + _))
    //源码实现方式
    println(reduceScala(arr, _ + _))
    //自带reduce方法
    arr.reduce(_+_)

  }

  def reduce(array: Array[Int],func:(Int,Int)=>Int) ={
    var sum = 0;
    for (elem <- array) {sum = func(sum,elem)}
    sum
  }

  def reduceScala(array: Array[Int],func:(Int,Int)=>Int) ={
    var sum = array(0);
    for (i<- 1 until array.length) {sum = func(sum,array(i))}
    sum
  }
}
