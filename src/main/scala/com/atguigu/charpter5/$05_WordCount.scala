package com.atguigu.charpter5

/**
  * @author Shelly An
  * @create 2020/7/25 14:01
  */
object $05_WordCount {
  def main(args: Array[String]): Unit = {
    val tupleList = List(("Hello Scala Spark World ", 4), ("Hello Scala Spark", 3), ("Hello Scala", 2), ("Hello", 1))

    val result = tupleList.flatMap(
                                                x => x._1.split(" ").map(y => (y, x._2))
                                              ).groupBy(z => z._1).map(
                                                z => (z._1, z._2.map(a => a._2).sum)
                                              )
    println(result)
  }

}
