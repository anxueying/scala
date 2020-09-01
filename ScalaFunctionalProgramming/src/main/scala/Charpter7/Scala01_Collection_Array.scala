package Charpter7

/**
  * @author Shelly An
  * @create 2020/7/25 8:29
  */
object Scala01_Collection_Array {
  def main(args: Array[String]): Unit = {
    //数组的字符串打印  [Ljava.lang.Object;@3c5a99da
    val array = new Array(5)
    println(array)

    //可以设定泛型，改变数组的类型  []表示泛型
    val array1 = new Array[String](5)
    //向数组中添加数据 scala中访问数据指定的元素要使用()
    array1(0) = "zhangsan0"
    array1(1) = "zhangsan1"
    array1(2) = "zhangsan2"
    array1(3) = "zhangsan3"
    array1(4) = "zhangsan4"


    //遍历数组中的数据
    //1. for循环
    for(s<-array1){
      println(s)
    }

    //2.mkString(分隔符)
    println(array1.mkString(","))  //zhangsan0,zhangsan1,zhangsan2,zhangsan3,zhangsan4
  }
}
