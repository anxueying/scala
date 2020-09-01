package com.atguigu.charpter7

import java.util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializeFilter

import scala.beans.BeanProperty

/**
  * @author Shelly An
  * @create 2020/7/28 11:03
  *        使用fastJson，将对象转json时必须加@BeanProperty
  */
object $05_ImplicitJson {
  def main(args: Array[String]): Unit = {
    /**
      * java和scala的集合类型转换
      */
    import scala.collection.JavaConverters._
    val listJava = new util.ArrayList[Int]()
    //Java List -> Scala List
    val listJtoS = asScalaBuffer(listJava)
    listJtoS.append(0)
    //Scala List -> Java List
    val listStoJ = asJavaCollection(listJtoS)
    //listStoJ.add(1)

    //import scala.collection.JavaConversions._  //市面上用的版本  无需调用方法 自动转

    /**
      * 将json转对象
      */
    val json =
      """
        |{"clazz":[{"className":"1001"},{"className":"1002"},{"className":"1003"}],"schoolName":"A"}
      """.stripMargin

    //json转对象
    val school = JSON.parseObject(json,classOf[School])

    println(school.schoolName)
//    school.clazz.foreach(x=>println(x.className)) 市面版本的写法
    val clazzes = asScalaBuffer(school.clazz)
    clazzes.foreach(x=> println(x.className))
    /**
      *将对象转为json
      */
    val str = JSON.toJSONString(school, null.asInstanceOf[Array[SerializeFilter]])
    println(str)
  }


}

//使用fastJson将对象转json的时候必须加上@BeanProperty
case class School(@BeanProperty schoolName:String,@BeanProperty clazz:util.List[Clazz])
case class Clazz(@BeanProperty className:String)

