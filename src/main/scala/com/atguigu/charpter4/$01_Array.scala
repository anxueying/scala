package com.atguigu.charpter4

/**
  * @author Shelly An
  * @create 2020/7/25 11:03
  */
object $01_Array {
  def main(args: Array[String]): Unit = {
    /**
      * scala数组分为  可变与不可变针对数组长度而言，数组的元素是可变的
      * 1. 可变数组
      * 2. 不可变数组
      */

    /*
      *  不可变数组定义语法
      *    1. new Array[类型](数组长度)  初始元素全部为0
      *    2. Array[类型](值1，值2)
      */
   val array1 = new Array[Int](10)
    //array没有实现toString方法
    println(array1.toBuffer)  //ArrayBuffer(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

    val array2 = Array[Int](2,4,6)
    println(array2.toBuffer) //ArrayBuffer(2, 4, 6)

    /**
      * 1. 新增元素 工作中一般没有顺序要求 记住一个就可以了
      */

    //1. 新增一个元素

    //+: 添加在前面
    val arr3 = array2.+:(10)
    println(arr3.toBuffer) //ArrayBuffer(10, 2, 4, 6)
    println(array2.toBuffer)  //ArrayBuffer(2, 4, 6)
    println(arr3==array2)  //false
    //在scala中 == 和equals用法一样，想比较地址值的话可以用以下两种方法
    val bool1 = array2.eq(arr3)
    val bool2 = array2.hashCode()==arr3.hashCode()

    //:+ 添加在后面
    val arr4 = arr3.:+(11)
    println(arr4.toBuffer) //ArrayBuffer(10, 2, 4, 6, 11)

    //2. 新增一个数组或集合的所有的元素进行添加
    val arr1 = arr3.++(arr4)
    println(arr1.toBuffer) //ArrayBuffer(10, 2, 4, 6, 10, 2, 4, 6, 11)
    val arr2 = arr3.++:(arr4)
    println(arr2.toBuffer) //ArrayBuffer(10, 2, 4, 6, 11, 10, 2, 4, 6)

    /**
      * 2. 修改元素
      */
    //索引从0开始
    array1(3) = 10
    println(array1.toBuffer) //ArrayBuffer(0, 0, 0, 10, 0, 0, 0, 0, 0, 0)

    /**
      * 3. 获取值
      */
    println(array1(3))  //10

    /**
      * 4. 删除 基本没用
      */
      //删除前面n个元素
    val array3 = array1.drop(3)
    println(array3.toBuffer) //ArrayBuffer(10, 0, 0, 0, 0, 0, 0)
    //删除后面n个元素
    val array4 = array1.dropRight(3)
    println(array4.toBuffer) //ArrayBuffer(0, 0, 0, 10, 0, 0, 0)

    /**
      * 5. 遍历
      */
    for (elem <- arr1) {
      println(elem)
    }

    println("--------foreach-------------")
    //一般使用foreach 在spark中有时会转不了，必须通过 _形式
    arr1.foreach(println)

  }
}
