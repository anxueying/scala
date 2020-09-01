package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 14:05
  *        定义一个方法，对数组中的每个元素进行操作，具体怎么操作由外部决定
  */
object $05_Map {
  def main(args: Array[String]): Unit = {
    val arr=Array[Int](2,3,5,6,7,1,20)

    println(map(arr, (x: Int) => x * x).toBuffer)
  }

  def map(array: Array[Int],func:Int=>Int)={
    for (elem <- array) yield {func (elem)}
  }
}
