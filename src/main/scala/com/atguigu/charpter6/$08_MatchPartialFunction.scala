package com.atguigu.charpter6

/**
  * @author Shelly An
  * @create 2020/7/27 15:14
  */
object $08_MatchPartialFunction {
  def main(args: Array[String]): Unit = {
    /**
      * 偏函数：就是没有match关键字的模式匹配
      */
    val func:PartialFunction[Any,Unit]={
      case x:Int => println("int类型")
      case x:String => println("String类型")
    }

    val a:Any = 10
    func(a)

    /**
      * 工作中一般不像上面定义
      */

    val list = List[(String, (String, (String, Int)))](
      ("A", ("0421", ("zhangsan", 10))),
        ("B", ("0421", ("lisi", 11))),
        ("C", ("0421", ("wangwu", 12)))
    )
    val age1 = list.map(_._2._2._2)

    val age2 = list.map(x => x match {
      case (schoolName, (className, (studentName, age))) => age
    })


    //最终形态：工作中用的最多的形态
    val age3 = list.map({
      case (schoolName, (className, (studentName, age))) => age
    })
    println(age3.toBuffer)

  }
}
