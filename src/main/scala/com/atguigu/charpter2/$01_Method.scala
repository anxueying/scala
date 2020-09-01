package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 9:10
  */
object $01_Method {
  /**
    * 方法定义的基本语法：
    *    def 方法名(参数名:参数类型,....) : 返回值类型 = { 方法体 }
    *  1. 方法体中只有一行代码，{}可以省略
    *  2. 定义方法的时候，如果要用{}的最后一行的表达式的结果值作为方法的返回值，那么返回值的类型可以省略
    *     如果方法体中有return，则必须定义返回值类型
    *  3. 方法不需要返回值的时候 = 可以省略
    *  4. 方法没有参数时，() 可以省略
    *       未省略   调用时可用可不用
    *       已省略   调用时不可用()
    */
  def main(args: Array[String]): Unit = {
    val result = add(1,2)
    println(result)

    //方法里定义方法  方法就是函数，函数就是一个对象的体现
    //方法体中有return
    def minus(x: Int,y: Int): Int ={
      if (x<=y) return 0
      x-y
    }

    //方法内定义函数并引用，必须先声明函数再调用，放在函数前面不行哦
    val result1 =minus(result,1)
    println(result1)

    val result2 = multi(result1,result)
    println(result2)

    myPrint
    myPrintln
  }

  //返回值是最后一行
  def add(x: Int,y: Int): Int ={
    x+y
  }

  //只有一行 省略{}
  def multi(x: Int,y: Int) = x*y

  //没有返回值 没有参数
  def myPrint{print("啥也没有，")}
  def myPrintln=println("=,{}不能同时省略")
}
