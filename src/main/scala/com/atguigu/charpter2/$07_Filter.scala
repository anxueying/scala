package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 14:23
  *        对数组中的元素进行过滤，要求保留符合要求的数据
  */
object $07_Filter {
  def main(args: Array[String]): Unit = {
    val arr=Array[Int](2,3,5,6,7,1,20)

    println(filter(arr, x => x % 2 == 0).toBuffer)
  }

  def filter(array: Array[Int],func:Int=>Boolean) ={
    //守卫  if语句不能放在yield中
    for (elem <- array if func(elem)) yield elem
  }
}
