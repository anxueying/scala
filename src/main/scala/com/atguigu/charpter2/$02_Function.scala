package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 9:30
  */
object $02_Function {
  /**
    * 1. 函数的定义的语法  val 函数名 = （参数：参数类型，...最多22个参数) => { 函数体 }
    * 2.  函数不适用方法的简化原则  只有{}可以省略，当函数体只有一行
    * 3. 方法转成函数：方法名 _
    *
    * 方法和函数的区别：
    * 1. 重载：方法可以重载，函数不能重载
    * 2. 转换：方法可以转成函数，函数不能转成方法
    * 3. 存储：方法存放在方法区，函数存放在堆空间
    *
    * 函数可以作为变量值赋值给变量，不可使用默认值，不可使用可变参数
    */
  def main(args: Array[String]): Unit = {
    //不可用return 用了之后无返回值
    val add = (x:Int,y:Int) =>{
      if (x==10){
        0
      }else{
        x+y
      }
    }

    val hello =()=>println("省略了{}")

    // 如果不加()实际上是函数的引用
    val myHello = hello
    myHello()

    //函数调用时必须带()，无论有无参数
    hello()

    //方法转成函数 还能单个转，不能转全部 需要根据:(Int)=>Unit 来判断你转的是哪个方法
    val f1:(Int)=>Unit = myPrint
    val f2 = myPrint _
    f1(1)
    println("-----")
    f2()
  }

  //重写方法转函数
  def myPrint{print("啥也没有，")}
  def myPrint(x:Int){print(x)}
}
