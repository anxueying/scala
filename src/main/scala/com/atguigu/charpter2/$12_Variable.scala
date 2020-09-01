package com.atguigu.charpter2

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

/**
  * @author Shelly An
  * @create 2020/7/21 16:29
  */
object $12_Variable {
  def main(args: Array[String]): Unit = {
    //读取HDFS前7天的数据
    //数据存储目录： /user/user_info/20200721
    //array:_* 将数组中的元素逐个传递
    read(getPaths(7):_*)
  }

  def getPaths(n:Int)={
    val calendar = Calendar.getInstance()
    val formatDate = new SimpleDateFormat("yyyyMMdd")
    for(i <- 1 to n) yield{
      calendar.setTime(new Date())
      calendar.add(Calendar.DAY_OF_YEAR,-i)
      s"/user/user_info/${formatDate.format(calendar.getTime)}"
    }
  }

  def read(path:String*)={
    println(path.toBuffer)
  }
}
