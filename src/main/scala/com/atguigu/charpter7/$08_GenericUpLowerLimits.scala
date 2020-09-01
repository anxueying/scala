package com.atguigu.charpter7

/**
  * @author Shelly An
  * @create 2020/7/28 14:27
  */
object $08_GenericUpLowerLimits {
  class GrandPa
  class Parent extends GrandPa
  class Sub1 extends Parent
  class Sub2 extends Sub1

  def main(args: Array[String]): Unit = {
    /**
      * 上界：要求类型最高只能是哪个类型
      * 定义方式： T <: 具体类型（它本身或它的子类）
      */
    m1(new Parent)
    m1(new Sub1)


    //m1(new GrandPa) //超过上界


    /**
      * 下界：要求类型最低只能是哪个类型 限制不住 因为T的子类也可以赋值给父类的对象
      * 定义方式： T >: 具体类型（它本身或它的父类）
      */
    val sub2:Sub2 = new Sub2

    m2(sub2)

    //m3(new GrandPa) //越上界
    m3(new Sub2)
  }

  def m1[T<:Parent](x:T):T={
    x
  }


  def m2[T>:Sub1](x:T):T={
    x
  }

  //必须先写下界，再写上界  T>:下界<:上界
  def m3[T>:Sub1<:Parent](x:T):T={
    x
  }

}
