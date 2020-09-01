package com.atguigu.charpter5

/**
  * @author Shelly An
  * @create 2020/7/25 9:28
  */
object $02_CollectionMethod {
  def main(args: Array[String]): Unit = {
    val list = List[Int](3,4,8,8,1,10)
    val listTemp = List[Int](3,4,8,10,7,6)

    //drop 删除前多少个，保留剩下的元素
    val list1 = list.drop(2)
    println(list1)  //List(8, 8, 1, 10)

    //dropRight 删除后多少个，保留剩下的元素
    val list2 = list.dropRight(2)
    println(list2)  //List(3, 4, 8, 8)

    //distinct  去重
    val list3 = list.distinct
    println(list3) //List(3, 4, 8, 1, 10)

    //head 获取第一个元素
    val head = list.head
    println(head) //3

    //last 获取最后一个元素
    val last = list.last
    println(last) //10

    //init 获取除开最后一个元素的所有元素
    val list4 = list.init
    println(list4)  //List(3, 4, 8, 8, 1)

    //init 获取除开第一个元素的所有元素
    val list6 = list.tail
    println(list6)  //List(4, 8, 8, 1, 10)

    //take 获取前n个元素
    val list7 = list.take(4)
    println(list7)  //List(3, 4, 8, 8)

    //takeRight 获取后n个元素
    val list8 = list.takeRight(4)
    println(list8)  //List(8, 8, 1, 10)

    //isEmpty 判断集合是否为空
    val empty = list.isEmpty
    println("集合是否为空：" + empty)  //集合是否为空：false

    //max 获取最大值 min 获取最小值
    val max = list.max
    val min = list.min
    println("max: "+max+", min: " + min)   //max: 10, min: 1

    //sum 元素求和 （string是不行的）
    val sum = list.sum
    println("和：" + sum)  //和：34

    //reverse 集合反转
    val list5 = list.reverse
    println(list5) //List(10, 1, 8, 8, 4, 3)

    //sliding(每次滑出来的元素,步长)  滑窗
    /*List(3, 4, 8)
      List(8, 8, 1)
      List(1, 10)*/
    val iterator = list.sliding(3,2)
    iterator.foreach(println(_))

    //intersect 交集 两个集合相同的元素
    val list9 = list.intersect(listTemp)
    println(list9) //List(3, 4, 8, 10)

    //diff 差集  list中去掉和listTemp相同的元素（是真的减法，只会减一次）  相当于--
    val list10 = list.diff(listTemp)
    println(list10) //List(8, 1) 你看，还有8对吧

    //union 并集 两个集合元素拼接  不会自动去重
    val list11 = list.union(listTemp)
    println(list11) //List(3, 4, 8, 8, 1, 10, 3, 4, 8, 10, 7, 6)

    //zip 拉链 获得元组
    val person = List[String]("name","age","sex")  //多出的会被截掉
    val zhangsan = List[Any]("zhangsan",18)
    val tuples = person.zip(zhangsan)
    println(tuples)  //List((name,zhangsan), (age,18))

    //unzip 反拉链
    val unzip = tuples.unzip
    println(unzip) //(List(name, age),List(zhangsan, 18))
  }
}
