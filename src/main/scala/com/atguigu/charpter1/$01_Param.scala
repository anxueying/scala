package com.atguigu.charpter1

/**
  * @author Shelly An
  * @create 2020/7/20 9:43
  */
object $01_Param {
  def main(args: Array[String]): Unit = {
    /**
      * 语法 val/var 变量名:变量类型=值   变量必须初始化！
      * val ： 不能重新赋值  相当于java中的final  （推荐）
      * var ： 可以重新赋值
      */

    //分号可以省略
    val name:String="shelly"
    var age:Int=18

    //scala中定义变量可以省略变量类型，如果省略，会自动推断。
    // 用cmd命令行运行命令即可看出自动推断的类型是什么
    val address = "深圳"
    println(address)

    //除非一行中要写两行语句，那需要用;分隔  但一般不会这样做！！
    print(name); print(age)
  }
}
