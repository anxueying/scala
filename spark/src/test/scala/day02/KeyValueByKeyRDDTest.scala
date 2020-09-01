package day02

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.junit.{After, Before, Test}

/**
  * @author Shelly An
  * @create 2020/8/2 21:14
  */


class KeyValueByKeyRDDTest {

  @Test
  def sortByKeyTest():Unit={
    val list = List(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98))


    val rdd: RDD[(String, Int)] = sc.makeRDD(list, 2)
    rdd.sortByKey(false,1).foreach(println)
  }


  @Test
  def combineByKeyExercise():Unit={
    val list = List(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98))

    val rdd: RDD[(String, Int)] = sc.makeRDD(list, 2)

    val rdd1:RDD[(String,(Int,Int))] = rdd.combineByKey(
      //zeroValue
      value => (value, 1),
      { case ((sum, count), value) => (sum + value, count + 1) },
      { case ((sum1, count1), (sum2, count2)) => (sum1 + sum2, count1 + count2) }
    )
    val result = rdd1.mapValues({case (sum,count) => sum.toDouble/count})

    result.foreach(println)
  }

  /**
    * (4,4)
    * (2,16)
    * (1,4)
    */
  @Test
  def combineByKeyTest():Unit={
    val list = List((1, 1), (2, 1), (2, 2), (4, 1),
      (1, 1), (2, 1), (2, 2), (4, 1))
    val rdd: RDD[(Int, Int)] = sc.makeRDD(list, 2)
    val result = rdd.combineByKey(
      //创建zeroValue 相同key的第一个value参与运算，之后就不参与分区内聚合了
      //相当于p1 : [v1,v2,v3....] => p1 = v1*2+v2+v3+.... => p1*p2*p3....
      value => value * 2,
      //分区内聚合
      (zeroValue: Int, value: Int) => zeroValue + value,
      //分区间聚合
      (zeroValue1: Int, zeroValue2: Int) => zeroValue1 * zeroValue2
    )
    result.foreach(println)
  }

  @Test
  def foldByKeyTest():Unit={
    val list = List((1, 1), (2, 1), (2, 2), (4, 1),
      (1, 1), (2, 1), (2, 2), (4, 1))
    val rdd: RDD[(Int, Int)] = sc.makeRDD(list, 2)
    val rdd1 = rdd.foldByKey(0)(_.max(_))
    rdd1.foreach(println)
  }


  /**
    * 取出每个分区内相同key的最大值，然后分区间相加
    * (4,2)
    * (2,4)
    * (1,2)
    * 分区内同时求最大和最小，分区间合并
    * (4,(1,1))
    * (2,(2,1))
    * (1,(1,1))
    * 求每个key对应的平均值
    * (4,1.0)
    * (2,1.5)
    * (1,1.0)
    */
  @Test
  def aggregateByKeyExercise():Unit={
    val list = List((1, 1), (2, 1), (2, 2), (4, 1),
      (1, 1), (2, 1), (2, 2), (4, 1))
    println("----------取出每个分区内相同key的最大值，然后分区间相加-----------------")

    val rdd: RDD[(Int, Int)] = sc.makeRDD(list, 2)
    //zeroValue 因为求max，而value是int类型，因此设置为int最小值，来与value做max运算
    val result1 = rdd.aggregateByKey(Int.MinValue)(
      (zeroValue,value) => {zeroValue.max(value)},
      (zeroValue1,zeroValue2) => {zeroValue1 + zeroValue2}
    )
    result1.foreach(println)

    println("---------分区内同时求最大和最小，分区间合并------------------")

    val result2 = rdd.aggregateByKey((Int.MinValue, Int.MaxValue))({
      case ((min, max), value) => (min.max(value), max.min(value))
    }, {
      case ((max1, min1), (max2, min2)) => (max1.max(max2), min1.min(min2))
    })
    result2.foreach(println)

    println("---------求每个key对应的平均值------------------")

    val result3 = rdd.aggregateByKey((0, 0))((zeroValue, value) => {
      (zeroValue._1 + value, zeroValue._2+1)
    }, (zeroValue1, zeroValue2) => {
      (zeroValue1._1 + zeroValue2._1, zeroValue1._2 + zeroValue2._2)
    }).map(x => (x._1, x._2._1.toDouble / x._2._2))
    result3.foreach(println)

    println("老师的第三个需求代码")
    val result4 = rdd.aggregateByKey((0, 0))({
      case ((sum, count), value) => (sum + value, count + 1)
    }, {
      case ((sum1, count1), (sum2, count2)) => (sum1 + sum2, count1 + count2)
    }).mapValues({ case (sum, count) => sum.toDouble / count })
    result4.foreach(println)
  }

  @Test
  def aggregateByKeyTest():Unit={

    val list = List((1, 1), (2, 1), (2, 2), (4, 1),
      (1, 1), (2, 1), (2, 2), (4, 1))

    val rdd: RDD[(Int, Int)] = sc.makeRDD(list, 2)
    val rdd1 = rdd.aggregateByKey(1)(_+_,_+_)
    rdd1.saveAsTextFile("outputSpark")
  }

  /**
    * 演示本地聚合 每个key有不止一个K-V对
    */
  @Test
  def reduceByKeyTest2():Unit={
    val list = List((1, 1), (2, 1), (2, 2), (4, 1),
      (1, 1), (2, 1), (2, 1), (4, 1))

    val rdd = sc.makeRDD(list,2)

    val rdd1 = rdd.reduceByKey(_+_)
    val rdd2 = rdd.groupByKey()

    rdd1.saveAsTextFile("output1")
    rdd2.saveAsTextFile("output2")

    Thread.sleep(Long.MaxValue)
  }

  /**
    * 演示本地聚合 每个key只有一个K-V对
    */
  @Test
  def reduceByKeyTest1():Unit={
    val list = List((1, 1), (2, 1), (3, 2), (4, 1),
      (1, 1), (2, 1), (3, 1), (4, 1))

    val rdd = sc.makeRDD(list,2)

    val rdd1 = rdd.reduceByKey(_+_)
    val rdd2 = rdd.groupByKey()

    rdd1.saveAsTextFile("output1")
    rdd2.saveAsTextFile("output2")

    Thread.sleep(Long.MaxValue)
  }

  @Test
  def reduceByKeyTest(): Unit = {
    val list = List((1, 1), (2, 1), (2, 2), (1, 1),
      (1, 1), (1, 1), (2, 1), (2, 1))
    val rdd = sc.makeRDD(list,2)
    //
    rdd.reduceByKey((v1,v2)=>v1+v2).saveAsTextFile("outputSpark")
  }


  var sc: SparkContext = null

  /**
    * 每次运行前，自动删除输出目录 使用hadoop的包
    */
  @Before
  def start(): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("My app")

    sc = new SparkContext(conf)
    val fs = FileSystem.get(new Configuration())
    val path = new Path("outputSPark")
    if (fs.exists(path)) {
      //递归删除
      fs.delete(path, true)
    }
  }


  /**
    * 每次运行后，关闭资源
    */
  @After
  def stop() = {
    sc.stop()
  }
}
