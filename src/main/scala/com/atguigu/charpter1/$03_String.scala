package com.atguigu.charpter1

/**
  * @author Shelly An
  * @create 2020/7/20 10:16
  */
object $03_String {
  def main(args: Array[String]): Unit = {
    /**
      * java中获取字符串的方式
      * 1. new string
      * 2. " "
      * 3. 字符串的一些方法
      * 4. toString()
      *
      * scala中：
      * 1. ""
      * 2. 插值表达式
      * 3. """ """
      * 4. 一些方法
      */

    val name = "方式1"


    //可以不用大括号 但后面如果跟着别的变量，并没有空格，那可能会出现一些错误。最好还是留有大括号，提高代码可读性。
    //一般用于字符串拼接
    val param = s"hello ${name}"

    println(param)

    //写spark的sql语句时使用
    val sql =
    """
      |select * from table1
      |where id in
      |(
      |   select id from table2
      |   left join
      |   table3
      |   on table2.name = table3.name
      |)
    """.stripMargin

    println(sql)

    //字符串替换，用于配置文件中字符串替换
    val str = "hello %s".format("name")
    println(str)

  }
}
