package Charpter7

/**
  * @author Shelly An
  * @create 2020/7/25 8:29
  */
object Scala02_Collection_Array {
  def main(args: Array[String]): Unit = {
    //采用其他方式创建数组 实质上调用了apply方法
    val array = Array(1,2,3,4)

    //添加数据 会产生新的数组，新的数组想怎么变就怎么变，还可以更改集合元素的数据类型
    //:+  在后面添加数据
    val array1 = array:+5
    println(array1.mkString(",")) // 1,2,3,4,5
    println(array.mkString(",")) //  1,2,3,4

    //+: 在前面加
    val array2 = array.+:(5)
    //如果集合的方法采用冒号结尾，那么运算规则从右向左执行 那怎么可以不用.调用+:呢
    val array3 = 5 +: array

    //++  增加多个数据（集合）
    val array4 = array2 ++ array3

  }
}
