package com.atguigu.charpter4

/**
  * @author Shelly An
  * @create 2020/7/25 15:18
  */
object $03_DimArray {
  def main(args: Array[String]): Unit = {
    val array = Array.ofDim[Int](2,3)
    array(0)(0) = 100
    array.foreach(arr=>arr.foreach(println))
  }
}
