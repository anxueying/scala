package com.atguigu.charpter6

/**
  * @author Shelly An
  * @create 2020/7/27 11:02
  */
object $03_MatchArray {
  def main(args: Array[String]): Unit = {
    val arr = Array[Any](0,1,"hello",6,7)

    arr match {
        //数组有一个元素
      case Array(x)=>println(x)
        //数组有5个元素，只取前三个，且第三个元素必须是string
      case Array(x,y,z:String,_,_)=>println(x,y,z)
        //数组有3个元素，只取最后一个
      case Array(_,_,z)=>println(z)
        //数组以0开头，后面n个元素，n>=1
      case Array(0,_*)=>println("数组以0开头，元素的个数>=1")
    }
  }
}
