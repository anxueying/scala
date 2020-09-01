package com.atguigu.charpter2

/**
  * @author Shelly An
  * @create 2020/7/24 22:43
  */
object $00_Stack {
  def main(args: Array[String]): Unit = {
    //java -> method -> 压栈 -> 栈帧 -> 弹栈
    //思考： test 压栈 弹出 sum压栈 这时x没了已经 怎么回事？
    //反编译：发现编译器在 test弹出，会将i再声明一次，将使用外部变量作为sum的参数传入
    //在2.11版本的scala这个代码会生成三个字节码文件，闭包被编译为匿名函数类
    /** 2.11版本之后，函数在使用外部变量时，
      * 如果外部变量失效时，会将这个变量包含到当前的函数内部，形成闭合的使用效果，改变变量的生命周期
      * 将这种操作称之为closure（闭包）
      * @param x
      * @return sum函数
      */
    def test(x: Int)={
      /**
        *
        * @param y
        * @return 两数的和
        */
      def sum(y:Int)={
        x+y
      }
      sum _
    }

    println(test(10)(20))
  }
}
