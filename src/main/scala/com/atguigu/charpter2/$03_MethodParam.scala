package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 10:09
  *
  *         方法的参数（只适用于def方法，不适用于函数）
  * 1. 定义方法的时候，可以给参数一个默认值，后续在调用方法时，有默认值的参数就可以不用传参
  * 2. 带名参数：在方法调用时，将值指定传递哪个参数(参数名必须与方法定义的参数名保持一致）
  * 3. 可变参数  参数名：参数类型*   必须放在最后面，
  */
object $03_MethodParam {
  def main(args: Array[String]): Unit = {
    //默认值
    def add(x: Int = 0, y: Int = 1) = x + y

    println(add())

    //有默认值的参数放在后面
    def minus(x: Int, y: Int = 2) = x - y

    println(minus(10))

    //带名参数调用
    def multy(x: Int = 2, y: Int) = x * y
    //参数名必须和方法中定义的参数名保持一致
    println(multy(y = 10))

    //可变参数中，可以传入超过22个参数
    println(odds(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23))
    println(odds(1))
  }

  def odds(x: Int) = {
    val hello =()=>println("省略了{}")
    x * x
  }

  def odds(x: Int, y: Int) = {
    (x + y) * 10
  }

  //可变参数，x相当于一个数组，多种类型可以用any*
  def odds(x: Int*) = {
    x.sum * 10
  }

}
