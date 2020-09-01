package com.atguigu.charpter7

import java.sql.{Connection, DriverManager, PreparedStatement}

import scala.util.Try

/**
  * @author Shelly An
  * @create 2020/7/28 9:42
  */
object $01_Exception {
  def main(args: Array[String]): Unit = {
    /**
      * try-catch-finally 没有throws
      * Try(表达式).getOrElse(异常处理方式)
      */

    val list = List[String]("1,shelly,18,shenzhen","10.0,jason,20,shenzhen","3,tutu,1x,shenzhen")
    val result = list.map(x => x.split(",")).map({
      case Array(id, name, age, address) =>
        val v_id = Try(id.toInt).getOrElse(0)
        val v_age = Try(age.toInt).getOrElse(1)
        (v_id, name, v_age, address)
    })
    result.foreach(println)

    /**
      * (1,shelly,18,shenzhen)
      * (0,jason,20,shenzhen)
      * (3,tutu,1,shenzhen)
      */


    m2(1,0) //因为m2进行了捕获处理 所以m1可以运行
    m1(1,0)

  }

  def m1(x:Int,y:Int)={
    //抛出异常
    println("m1运行")
    if(y==0) throw new Exception
    x/y
  }

  /**
    * 在工作中只有获取外部链接时才会使用此种方式，比如：获取mysql数据库连接后的资源关闭
    * @param x
    * @param y
    */
  def m2(x:Int,y:Int)={
    //捕获异常
    try{
      x/y
    }catch {
      case e:Exception=>e.printStackTrace()
    }finally {
      println("运行结束")
    }
    println("强大的功能")
  }


  def insert()={
    var connection:Connection = null
    var statement:PreparedStatement = null
    try{
      //1. 获取数据库连接
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/default")
      //2. 获取statement对象
      statement = connection.prepareStatement(
        "insert into table a values (?,?);"
      )
      //3. 给参数赋值  sql语句
      statement.setString(1,"zhagnsan")
      statement.setInt(1,20)
      statement.setString(1,"shenzhen")

      //4. 执行sql
      statement.execute()
    }catch {
      case e:Exception=>e.printStackTrace()
    }finally {
      //5. 资源关闭
      statement.close()
      connection.close()
    }
  }
}
