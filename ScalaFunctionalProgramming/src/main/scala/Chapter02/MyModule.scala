package Chapter02

/**
  * @author Shelly An
  * @create 2020/7/21 18:16
  *
  */

//声明一个单例对象，即同时声明一个类和它的唯一实例
object MyModule {
  /**
    * abs方法
    *
    * @param n 接收一个Integer
    * @return 返回右手边的求值结果
    *         所有的表达式，包括if表达式都产生一个结果
    */
  //def 方法名 (方法参数):返回值类型=方法体
  //左手边的/签名 = 右手边的/定义
  def abs(n: Int): Int =
    if (n < 0) -n
    else n

  /**
    * 一个私有方法，只能被MyModule里的其他成员调用
    *{
    * 声明 ; 声明
    * 声明
    * } block 代码块 声明之间用;或换行符分割
    * @param x 接收一个integer
    * @return 返回一个string
    */
  //无需声明返回值类型，scala可以推断。
  //BUT  为了良好的代码风格，最好还是显式的声明返回值类型
  private def formatAbs(x: Int) = {
    //字符串里有2个占位符，%d代表数字
    //val是不可变变量
    val msg = "The absolute value of %d is %d"
    //将占位符替换为x和abs(x)
    msg.format(x, abs(x))
  }

  private def formatFactorial(x: Int) = {
    val msg = "The factorial value of %d is %d"
    //将占位符替换为x和abs(x)
    msg.format(x, factorial(x))
  }

  //将上面两个format函数泛化为一个
  def formatResult(name:String,n:Int,f:Int=>Int)={
    val msg = s"The %s value of %d is %d"
    //将占位符替换为x和abs(x)
    msg.format(name,n, f(n))
  }

  //调用纯函数内核的外壳 这样的方法称为过程，因为带有副作用
  //通常 返回值为unit类型则暗示方法有副作用
  def main(args: Array[String]): Unit = {
    //调用函数
    println(formatAbs(-42))
    println(formatFactorial(7))

    //使用泛化后的format
    println(formatResult("absolute", -42, abs))
    println(formatResult("factorial", 7, factorial))
  }

  /**
    * 阶乘
    * @param n
    * @return n的阶乘
    */
  def factorial(n:Int):Int={
    /**
      * 内部函数 / 局部定义函数 就把它理解成一个局部的字符串或整数
      * go函数只能在factorial函数内部引用
      */
    //如果编译器不能消除尾部调用，会给出编译错误
    @annotation.tailrec
    def go(n:Int,acc:Int):Int=
      if(n<=0) acc
      else go(n-1,n*acc)

    go(n,1)
  }

  //单态函数  查找字符串
  def findFirst(ss:Array[String],key:String):Int={
    def loop(n:Int):Int=
      if(n>ss.length) -1
      else if (ss(n)==key) n
      else loop(n+1)
    loop(0)
  }

  /**
    * 多态函数/泛型函数  查找字符串
    * @param ss
    * @param p 给每个元素做测试的函数替代掉判定key是否相等
    * @tparam A 类型作为参数替代了string类型 通常习惯用短的
    * @return 如果p匹配当前元素，就找到相匹配的元素，返回数组当前索引值
    */
  // 在调用函数时会强制检测，如果在Array[Int]中查找String，会报错
  def findFirst[A](ss:Array[A],p:A=>Boolean):Int={
    def loop(n:Int):Int=
      if(n>ss.length) -1
      else if (p(ss(n))) n
      else loop(n+1)

    loop(0)
  }
}
