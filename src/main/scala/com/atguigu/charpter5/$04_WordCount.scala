package com.atguigu.charpter5

/**
  * @author Shelly An
  * @create 2020/7/25 14:01
  */
object $04_WordCount {
  def main(args: Array[String]): Unit = {
    val stringList = List("Hello Scala Hbase kafka", "Hello Scala Hbase", "Hello Scala", "Hello")

    val strings = stringList.flatMap(_.split(" "))
    //单个_构不成表达式, 这里不能简化

    /**
      * strings.groupBy(x=>x)的表现形式
      * (hello,List(hello,hello,....))
      * (scala,List(scala,scala,...))
      * ............
      */

    val mapResult = strings.groupBy(x=>x).map(x=>(x._1,x._2.size))

    println(mapResult)
  }

}
