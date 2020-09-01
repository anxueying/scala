package com.atguigu.charpter1

/**
  * @author Shelly An
  * @create 2020/7/20 10:50
  */
object $06_Operator {
  def main(args: Array[String]): Unit = {
    /**
      * java中
      * 1. 算术操作符  + - * / ++ -- %
      * 2. 逻辑操作符 && || ! & |
      * 3. 赋值操作符 = += -= /= *=
      * 4. 关系操作符 > < >= <= ==
      * 5. 位运算符 << >> >>>
      * 6. 三元运算符 xx?a:b
      *
      * scala中也是这些，不过没有++、--、三元运算符
      * 其中本质区别是，scala是真正的面向对象，其中的运算符是对象的方法
      */

    var a = 10;
    a + 1

    /**
      * scal方法的调用方式 当参数只有一个时括号可以省略
      * 1. 对象.方法名（参数）
      * 2. 对象 方法名 （参数.....)
      */
    a.+(1)
  }
}
