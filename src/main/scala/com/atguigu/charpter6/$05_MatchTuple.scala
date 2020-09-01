package com.atguigu.charpter6

/**
  * @author Shelly An
  * @create 2020/7/27 14:27
  */
object $05_MatchTuple {
  def main(args: Array[String]): Unit = {
    //元素类型和匹配类型必须一致，如在设置时不知道类型，建议使用Any或者不设置
    val t1 = ("zhangsan",18,"male","shenzhen")

    t1 match {
        //元组元素有几个，这里就必须写几个
        //一般不设置接收的类型
      case (name,age,sex,address) =>println(s"${name} ${age} ${sex} ${address}")

    }
  }
}
