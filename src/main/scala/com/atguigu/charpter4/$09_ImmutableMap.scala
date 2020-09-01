package com.atguigu.charpter4

/**
  * @author Shelly An
  * @create 2020/7/27 12:50
  */
object $09_ImmutableMap {
  def main(args: Array[String]): Unit = {
    /**
      * 不可变Map
      * 创建方式：
      * 1. Map[K的类型,V的类型](K->V,K->V,...)
      * 2. Map[K的类型,V的类型]((K,V),(K,V),...)
      */

    val map1 = Map[String,String]("name"->"zhangsan","age"->"20","address"->"shenzhen")
    val map2 = Map[String,String](("name","zhangsan1"),("age","201"),("address","shenzhen1"))

    /**
      * 添加元素
      */
    //(()) 外层是元素的括号 内层是元组的括号
    val map3 = map1 + (("sex","male"))
    println(map3) //Map(name -> zhangsan, age -> 20, address -> shenzhen, sex -> male)

    val map4 = map2 + (("salary","1000"))
    println(map4) //Map(name -> zhangsan1, age -> 201, address -> shenzhen1, salary -> 1000)

    //相同key会覆盖掉
    val map5 = map3 ++ map4
    println(map5)  //Map(name -> zhangsan1, age -> 201, sex -> male, salary -> 1000, address -> shenzhen1)

    /**
      * 查看元素
      */
    //获取元素
    println(s"name= ${map5("name")}") //name= zhangsan1

    //获取元素 避免空指针异常  提醒别人这里可能返回null
    val option = map5.get("name")
    println(option.get) //zhangsan1
    //一般用这个 如没有则返回默认值
    val defaultName = map5.getOrElse("name1","wumingshi")
    println(defaultName) //wumingshi

    /**
      * 修改元素 不会改变原来的map
      */
    val map6 = map5.updated("name","lisi")

    /**
      * 遍历
      */
    map6.foreach(x=>println(x._1))
    //key
    map6.keys.foreach(println)
    //value
    map6.values.foreach(println)
  }
}
