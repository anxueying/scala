package com.atguigu.charpter3

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

/**
  * @author Shelly An
  * @create 2020/7/22 16:37
  *        将对象本身写入磁盘
  */
object $10_Trait {
  trait ReadAndWriteObject{
    //提示：必须序列化
    this:Serializable =>
    /**
      * 从磁盘读取对象
      * @return 对象
      */
    def read()={
      val ois = new ObjectInputStream(new FileInputStream("d:/obj.txt"))
      val obj = ois.readObject()
      ois.close()
      obj
    }

    /**
      * 将对象写入磁盘
      */
    def write()={
      val oos = new ObjectOutputStream(new FileOutputStream("d:/obj.txt"))
      oos.writeObject(this)
      oos.flush()
      oos.close()
    }
  }

  class Person(val name:String,var age:Int) extends ReadAndWriteObject with Serializable{

  }

  def main(args: Array[String]): Unit = {
    //val zs = new Person("张三",18)
    //zs.write()
    val ls = new Person("李四",18)
    val person = ls.read().asInstanceOf[Person]
    println(person.name + "\t" + person.age)
  }
}
