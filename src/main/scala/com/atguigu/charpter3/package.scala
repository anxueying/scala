package com.atguigu

/**
  * @author Shelly An
  * @create 2020/7/22 14:50
  *        包对象中定义的变量和方法、函数可以在包中任何地方使用
  *        包对象的名称和包名要保持一致
  */
package object charpter3 {
  val packageObjectName = "charpter3"

  def myPrint{print("charpter3 lalala")}

  val func=(x:Int,y:Int)=>x+y
}
