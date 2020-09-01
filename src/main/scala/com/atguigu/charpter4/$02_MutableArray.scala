package com.atguigu.charpter4

import scala.collection.mutable.ArrayBuffer

/**
  * @author Shelly An
  * @create 2020/7/25 14:49
  */
object $02_MutableArray {
  def main(args: Array[String]): Unit = {
    /**
      * 可变数组的创建方法
      * 1. ArrayBuffer[数据类型](初始元素)
      */
    val arr = ArrayBuffer[Int]()

    /**
      * 1. 添加
      * 不带= 会生成新的数组
      * 带=  不会生成新的数组
      */
    val arr1 = arr.+:(10)
    println(s"arr = ${arr}, arr1 = ${arr1}")
    //arr = ArrayBuffer(), arr1 = ArrayBuffer(10)

    val arr2 = arr.+=:(10)
    println(s"arr = ${arr}, arr2 = ${arr2} , 是否为同一地址：${arr2.eq(arr)}")
    //arr = ArrayBuffer(10), arr2 = ArrayBuffer(10) , 是否为同一地址：true

    val arr3 = arr1 ++: arr2
    println(s"arr3 = ${arr3}, arr1 = ${arr1} , arr2 = ${arr2}" )
    //arr3 = ArrayBuffer(10, 10), arr1 = ArrayBuffer(10) , arr2 = ArrayBuffer(10)

    val arr4 = arr1 ++=: arr2
    println(s"arr4 = ${arr4}, arr1 = ${arr1} , arr2 = ${arr2} , arr2与arr4是否为同一地址：${arr2.eq(arr4)}" )
    //arr4 = ArrayBuffer(10, 10), arr1 = ArrayBuffer(10) , arr2 = ArrayBuffer(10, 10) , arr2与arr4是否为同一地址：true


    /**
      * 2.修改
      * 注意：index必须在有效元素范围内
      */
    arr3(1) = 11
    println(arr3) //ArrayBuffer(10, 11)

    arr4.insert(1,3,4,5,6)
    println(arr4) //ArrayBuffer(10, 3, 4, 5, 6, 10)

    arr4.update(1,11)
    println(arr4) //ArrayBuffer(10, 11, 4, 5, 6, 10)


    /**
      * 3. 删除
      * 如果删除的 元素不存在  不会报错
      * 和+一样有带=和不带=
      */
    val arr5 = arr3 - 10
    println(arr5) //ArrayBuffer(11)
    println(arr3) //ArrayBuffer(10, 11)

    val arr6 = arr3 -= 10
    println(arr6) //ArrayBuffer(11)
    println(arr3) //ArrayBuffer(11)

    val arr7 = arr4 -- Array(10,15)
    println(arr7) //ArrayBuffer(11, 4, 5, 6, 10)

    //remove
    arr7.remove(1)  //索引
    println(arr7) //ArrayBuffer(11, 5, 6, 10)

    /**
      * 4. 查看
      */
    arr7.foreach(x=>println(x+1))

  }
}
