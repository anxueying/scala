package com.atguigu.charpter3

import scala.util.Random

/**
  * @author Shelly An
  * @create 2020/7/22 16:47
  */
object $11_InstanceOf {


  class Animal

  class Dog extends Animal{
    def adog() = print("a dog")
  }

  class Pig extends Animal{
    def aPig() = print("a pig")
  }

  def test(x:Int) ={
    if (x%2==0) {
      new Dog
    }else{
      new Pig
    }
  }

  def main(args: Array[String]): Unit = {
    val animal = test(Random.nextInt(10))
    //判断对象是否属于某个类型
    if (animal.isInstanceOf[Dog]) {
      //将对象强转为某个类型
      animal.asInstanceOf[Dog].adog()
    }else{
      animal.asInstanceOf[Pig].aPig()
    }


    //java 对象获取class   对象.getClass
    //java 类获取class   类.class

    //scala 对象获取  getClass根据实例来，实例是什么就是什么，不一定是animal
    val objclazz = animal.getClass
    println(objclazz)
    //scala 类获取
    val clazz = classOf[Animal]
    println(clazz)

  }
}
