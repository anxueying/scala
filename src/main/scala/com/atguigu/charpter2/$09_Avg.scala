package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 14:42
  *        求均值
  */
object $09_Avg {
  def main(args: Array[String]): Unit = {
    val arr=Array[Int](2,3,5,6,7,1,20)

    println(avg(arr, _ / _))
  }

  def avg(array: Array[Int],func:(Int,Int)=>Double)={
    var sum = 0
    for (elem <- array) {
      sum = sum+elem
    }
    func(sum,array.length)
  }
}
