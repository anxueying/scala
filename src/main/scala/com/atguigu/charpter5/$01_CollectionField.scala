package com.atguigu.charpter5

/**
  * @author Shelly An
  * @create 2020/7/25 9:20
  */
object $01_CollectionField {
  def main(args: Array[String]): Unit = {
    /**
      * 集合常用属性
      * 1. 获取集合的大小   size
      * 2. 获取集合的长度   length
      * 3. 是否包含  contains
      * 4. 转字符串  mkString
      */
    val list = List[Int](3,4,8,1,10)
    println("集合的大小：" + list.size)
    println("集合的长度：" + list.length)
    println("集合是否包含10：" + list.contains(10))
    println("toString()的打印是这样的" + list.toString())
    println("mkString()的打印是这样的，分隔符是\t" + list.mkString("\t"))

    //遍历打印
    list.foreach((x:Int)=>println(x))
    //简化 省略数据类型
    list.foreach((x)=>println(x))
    //简化 只有一个参数
    list.foreach(x=>println(x))
    //简化 参数只用到一次
    list.foreach(println(_))

  }
}
