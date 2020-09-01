package com.atguigu.charpter1

/**
  * @author Shelly An
  * @create 2020/7/20 15:09
  */
object $09_for {
  def main(args: Array[String]): Unit = {
    //to  range 1 to 10 1 1 .... 10  左右闭合的
    1.to(10)
    //until  range 1 until 10 1 2 .. 9  左闭右开
    0 until 10

    val arr = Array("hello", "world")
    //for循环的格式
    for (i <- arr.indices) {
      println(arr(i))
    }
    //守卫：
    //嵌套for循环：
    for (i <- arr.indices if i % 2 == 1;k=i*i; j <- 0 to k) {
      println(arr(i) + j)
    }

    //定义返回值：数组
    val array = for(i<-arr) yield {
      i+i
    }
    println(array.toBuffer)

  }
}
