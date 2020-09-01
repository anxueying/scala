package com.atguigu.charpter4
import scala.collection.mutable.Map
/**
  * @author Shelly An
  * @create 2020/7/27 13:05
  */
object MutableMap {
  def main(args: Array[String]): Unit = {

    val map1 = Map[String,String]( "name"->"lisi" )
    val map2 = Map[String,String]( ("name","zhangsan") )
    //新增
    val map3 = map1 + (("age","20"))
    println(map3) //Map(age -> 20, name -> lisi)

    map1 += (("age","30"))
    println(map1) //Map(age -> 30, name -> lisi)

    map1 ++= Map[String,String]("aa"->"bb","cc"->"dd")
    println(map1) //Map(cc -> dd, age -> 30, name -> lisi, aa -> bb)

    map1.put("ee","ff")
    println(map1) //Map(ee -> ff, cc -> dd, age -> 30, name -> lisi, aa -> bb)
    //获取 get如果key不存在也会报错
    val value = map1.get("aa")
    println(value) //Some(bb)
    println(map1.getOrElse("aaa", "value")) //value

    //修改
    map1.put("ee","ee")
    println(map1) //Map(ee -> ee, cc -> dd, age -> 30, name -> lisi, aa -> bb)
    //删除
    map1.-=("ee")
    println(map1) //Map(cc -> dd, age -> 30, name -> lisi, aa -> bb)
    //没有key不会报错
    map1.-=("eee")
    println(map1) //Map(cc -> dd, age -> 30, name -> lisi, aa -> bb)

    map1.remove("cc")
    println(map1) //Map(age -> 30, name -> lisi, aa -> bb)
    //遍历
    map1.foreach(println(_))
    //key
    map1.keys.foreach(println(_))
    //value
    map1.values.foreach(println(_))
  }
}
