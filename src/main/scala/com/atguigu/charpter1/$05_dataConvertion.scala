package com.atguigu.charpter1

/**
  * @author Shelly An
  * @create 2020/7/20 10:44
  */
object $05_dataConvertion {
  def main(args: Array[String]): Unit = {
    var a:Byte = 10
    var b:Short = 300
    var c:Int = 1000
    var d:Long = 10000

    b=a
    a=b.toByte

    val str:String = "10"
    str.toInt

    val str1:String = "10.1"
    //字符串中，`.`不一定代表小数点，直接使用toInt会报错，必须先转成浮点类型，再转成自己需要的数字类型
    str.toDouble.toInt
  }
}
