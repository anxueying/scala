package com.atguigu.charpter3

/**
  * @author Shelly An
  * @create 2020/7/22 15:13
  */
object $08_ObjectClass {
  /**
    * scala将静态和非静态分开了
    *   object里   静态
    *   class里   非静态
    *
    * 伴生类和伴生对象
    * 1. object的名字和class的名字一样
    * 2. object必须和class在同一个源文件
    *
    * object称为伴生类,class称为伴生对象  两者可以互相访问私有方法、属性，只能在内部
    *
    */

  private val name = "lisi"


  def main(args: Array[String]): Unit = {
    val obj:$08_ObjectClass = new $08_ObjectClass
    println(obj.age)

    //通过apply之后
    val objApply:$08_ObjectClass = $08_ObjectClass.apply()
    //省略apply
    val objApplyNone:$08_ObjectClass = $08_ObjectClass()

    //未调用apply，赋值的是实例，而非对象
    val objClass = $08_ObjectClass

  }

  //scala.Array[T] 就是用伴生类、伴生对象,通过apply实现的，简化对象的创建，无需new
  def apply(): $08_ObjectClass = new $08_ObjectClass()
}

class $08_ObjectClass {
  private val age = 18
  println($08_ObjectClass.name)
}