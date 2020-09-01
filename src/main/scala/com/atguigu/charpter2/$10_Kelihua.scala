package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 16:12
  * 闭包： 函数体中调用不属于函数本身的变量
  */
object $10_Kelihua {

  def main(args: Array[String]): Unit = {
    println(m1(2)(3,4))

    //运行结果相同
    println(m2(2)(3, 4))
  }

  /**
    * 有多个参数列表的方法称之为柯里化
    * @param x 参数列表1 参数1
    * @param y 参数列表2 参数1
    * @param z 参数列表2 参数2
    * @return 返回结果
    */
  def m1(x:Int)(y:Int,z:Int)=x+y+z

  //实际上相当于这个样子
  def m2(x:Int)={
    val func=(y:Int,z:Int)=>x+y+z
    func
  }
}
