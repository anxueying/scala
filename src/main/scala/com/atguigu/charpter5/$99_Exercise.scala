package com.atguigu.charpter5

/**
  * @author Shelly An
  * @create 2020/7/25 11:27
  *         要求：可以手写出来scala就过关了
  */
object $99_Exercise {
  def main(args: Array[String]): Unit = {
    //在string中也是一个集合 可以直接使用集合的方法
    val strings = List("hello spark hello python", "hello scala", "hello flume hadoop")
    //每个字母在每个单词中出现了几次

    //x.groupBy(x => x) 的形式  (e, e),( h ,h), (l , ll), (o , o)

    //x先用flatmap变成(h,hello),list((h,hello),(h,hello),....)形式
    val wordList = strings.flatMap(_.split(" ")).distinct.flatMap(x => {
      x.map(y => (y, x))
    }).groupBy(x => x).map(x => (x._1, x._2.length)).mkString(",")

    println(wordList)



    //每个字母在每句话中出现了几次
    println(strings.distinct.flatMap(
      x => {
        x.split(" ").flatMap(y => y.map {
          z => (z, x)
        })
      }
    ).groupBy(x => x)
      .map(x => (x._1, x._2.length))
      .mkString(","))
}

}
