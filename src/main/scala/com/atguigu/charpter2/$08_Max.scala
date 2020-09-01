package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 14:34
  *        最大值
  */
object $08_Max {
  def main(args: Array[String]): Unit = {
    val arr=Array[Int](2,3,5,6,7,1,20)
    println(max(arr, (x: Int, y: Int) => math.max(x, y)))
    //简写
    println(max(arr, math.max(_, _)))


  }

  def max(array: Array[Int],func:(Int,Int)=>Int)={
    var max=array(0)
    for (i  <- 1 until array.length) {max = func(max,array(i))}
    max
  }
}
