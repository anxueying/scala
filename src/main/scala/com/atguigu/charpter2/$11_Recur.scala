package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 16:25
  */
object $11_Recur {
  def main(args: Array[String]): Unit = {

    println(m1(5))
  }

  /**
    * 递归方法
    * @param n 入参
    * @return 必须定义返回值类型
    */
  def m1(n:Int):Int={
    if(n==1){
      1
    }else{
      n*m1(n-1)
    }
  }
}
