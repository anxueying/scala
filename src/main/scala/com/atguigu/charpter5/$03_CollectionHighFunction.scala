package com.atguigu.charpter5

/**
  * @author Shelly An
  * @create 2020/7/25 10:10
  */
object $03_CollectionHighFunction {
  def main(args: Array[String]): Unit = {

    val list = List[Int](3,4,8,8,1,10,2,1,4)
    val tuples = List[(String,Int)](("shelly",18),("jason",20),("xiaoxiao",2),("tutu",1))
    val stringList = List[String]("hello","ho","java","python")

    /**
      *  排序
      *  sorted
      *  sortBy
      *  sortWith
      */

    //sorted 默认升序
    val sort1 = list.sorted
    println(sort1)  //List(1, 1, 2, 3, 4, 4, 8, 8, 10)
    println(sort1.reverse)  //降序 List(10, 8, 8, 4, 4, 3, 2, 1, 1)

    //sortBy 根据指定字段进行排序 默认升序 ._2元组取值
    val sort2 = tuples.sortBy(x=>x._2)
    println(sort2) //List((tutu,1), (xiaoxiao,2), (shelly,18), (jason,20))

    //sortWith 可以指定排序规则 < 升序  > 降序
    val sort3 = tuples.sortWith(_._2>_._2)  //降序  tuples.sortWith((x,y)=>x._2>y._2)
    println(sort3) //List((jason,20), (shelly,18), (xiaoxiao,2), (tutu,1))


    /**
      * 转换   一对多
      * flatten
      * flatMap
      */
    //一条数据产生一条结果可用  一条数据多条结果不能用这个
    val mapList = stringList.map(_.length)
    println(mapList) //List(5, 2, 4, 6)
    //将每一组数据变成一个对象
    val persons = tuples.map(x=>new Person(x._1,x._2))

    //压平 一对多
    val list1 =List[List[String]](List("name", "relation"), List("shelly", "jason"))
    val flatten = list1.flatten
    println(flatten)  //List(name, relation, shelly, jason)
    //只压第二层
    val list2 =List[List[List[String]]](List(List("name", "relation")), List(List("shelly", "jason")))
    println(list2.flatten)  //List(List(name, relation), List(shelly, jason))
    println(list2.flatten.flatten) //List(name, relation, shelly, jason)

    //需求：生成结果 List(hello, python, hello, scala, hello, java)
    val strings = List[String]("hello python","hello scala","hello java")
    println(strings.map(_.split(" ")).flatten)
    //用flatMap更简洁
    println(strings.flatMap(_.split(" ")))

    /**
      * 聚合 多对一
      * reduce 将上一次运行的结果作为下一次运行的参数
      * reduceRight 从右向左
      */
    //x 上一次运行的结果  y是当前要聚合的元素
    val result = list.reduce((x,y)=>x-y)
    //3,4,8,8,1,10,2,1,4
    //第一次运行  x= 3, y=4 结果 -1
    //第二次运行  x= -1, y=8 结果 -9
    //第三次运行  x= -9, y=8 结果 -17
    //................
    println(s"result = ${result}")  //-35

    val resultRight = list.reduceRight((x,y)=>y-x)
    println(s"resultRight = ${resultRight}")  // -33

    /**
      * 叠加 与reduce区别：多了初始值
      * fold
      * foldRight
      */
    println(list.fold(100)((x, y) => x + y))  //141
    //3,4,8,8,1,10,2,1,4
    //第一次运行  x= 100, y=3 结果 103
    //第二次运行  x= 103, y=4 结果 -107
    //................
    println(list.foldRight(100)((x, y) => x + y)) //141

    /**
      * 过滤
      * filter(函数，boolean条件为过滤出的数据满足的条件）
      * 做需求时要尽量 减少数据量  过滤，去重，列裁剪
      */
    //过滤出 年龄大于5的数据
    println(tuples.filter(_._2 > 5)) //List((shelly,18), (jason,20))


    /**
      * 分组
      */
      //求每个班级的平均分
    val student = List[(String,String,Double)] (
        ("A","zhangsan",88),("B","lisi",92),("A","wangwu",100),("C","zhaoliu",55),("A","tianqi",68)
      )

    val scoreAvg = student.groupBy(x => x._1).map(x => {
      val className = x._1
      val studentList = x._2
      val sumScore = studentList.map(y => y._3).sum
      (className, sumScore / studentList.length)
    })
    println(scoreAvg)
  }
}
