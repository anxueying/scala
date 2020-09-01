package com.atguigu.charpter6

/**
  * @author Shelly An
  * @create 2020/7/27 11:02
  */
object $04_MatchList {
  def main(args: Array[String]): Unit = {
    val list = List[Any](0, 1, "hello", 6, 7)

    list match {
      //数组有两个个元素
      case List(x, y) => println(x, y)
      //数组有5个元素，只取前三个，且第三个元素必须是string
      case List(x, y, z: String, _, _) => println(x, y, z)
      //数组有3个元素，只取最后一个 如果想case以x元素结尾，前面元素长度不限，则做不到（除非写22个case）
      case List(_, _, z) => println(z)
      //数组以0开头，后面n个元素，n>=0
      case List(0, _*) => println("数组以0开头，元素的个数>=1")
    }

    list match {
      case x::y::z::Nil => println("当前list有三个元素")
      case head::x=>println(s"匹配尾位元素${x}剩余部分${head}")
      case x::tail=>println(s"匹配首位元素${x}剩余部分${tail}")  //tail可以换成任意字符串

    }

    //List匹配类型
    //如指定List[Int](元素),则下面case为x:List[String]还是x:List[Int]都一样
    // 因为在运行时 存在泛型擦除    与Array不同
    val list1 = List(1, 2, 3)
    list1 match {
      case x:List[Double] => println("浮点数")  //浮点数
      case x:List[String] => println("字符串")
      case x:List[Int] => println("整数")
    }

  }
}
