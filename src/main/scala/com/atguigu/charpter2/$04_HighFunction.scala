package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/21 11:15
  */
object $04_HighFunction {
  def main(args: Array[String]): Unit = {
    val func1 = (x:Int,y:String)=>x+y
    val func2 = (x:Int,y:Int)=>x*y
    val func3 = (x:Int,y:Int)=>x-y
    val func4 = (x:Int,y:Int)=>x/y

//    println(m1(10, 2, func1)) 会报错
    println(m1(10,2,func2))
    //匿名函数 不能调用，用途：作为参数传递
    // 1. 函数类型可以省略，因为def m1时已经定义了
    println(m1(10,2,(x,y)=>x+y))

    //2. 如果函数的所有参数在=>右边都只使用了一次，可以用_代替
    //_形式最终会解析成函数，单个_构不成一个表达式，scala不会对单个_进行解析


    //_不能放在嵌套中，即不可以放在()中，除非（）只有_
    println(m1(10,2,(x,y)=>(x+10)*y))
    //println(m1(10,2,(_+10)*_)  报错

    //解析原理：最近的（）内是否可以构成表达式，可以，则（里面）翻译，否则跳出（）。

    println(m1(10,2,_+_))
    // 3. 函数参数只有一个的时候，()可以省略  函数类型可以省略
    println(func("hello",_*10))
  }

  def m1(x:Int,y:Int,func:(Int, Int)=>Int)={
    func(x,y)
  }

  val func = (x:String,func:String=>String)=>{
    func(x)
  }
}
