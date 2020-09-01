package day02

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Before, Test}

import scala.collection.mutable

/**
  * @author Shelly An
  * @create 2020/8/2 21:14
  */
/**
  * 自定义累加器 模拟longAccumulator
  */
class MyLongAccumulator extends AccumulatorV2[Long, Long] {
  private var sum: Long = 0

  //判断是否归零
  override def isZero: Boolean = sum == 0

  //复制累加器
  override def copy(): AccumulatorV2[Long, Long] = new MyLongAccumulator

  //重置累加器 归0
  override def reset(): Unit = sum = 0

  //累加
  override def add(v: Long): Unit = sum += v


  //合并结果
  override def merge(other: AccumulatorV2[Long, Long]): Unit = {
    sum += other.value
  }

  //返回结果
  override def value: Long = sum
}

/**
  * 实验不为0的累加器
  *
  * @param arg 副本会对其归0
  */
class MyLongAccumulator1(arg: Int) extends AccumulatorV2[Long, Long] {
  private var sum: Long = arg

  //判断是否归零
  override def isZero: Boolean = true

  //复制累加器
  override def copy(): AccumulatorV2[Long, Long] = new MyLongAccumulator1(arg)

  //重置累加器 归0 //测试10
  override def reset(): Unit = sum = 10

  //累加
  override def add(v: Long): Unit = {
    sum += v
  }

  //合并结果
  override def merge(other: AccumulatorV2[Long, Long]): Unit = {
    sum += other.value
  }

  //返回结果
  override def value: Long = sum
}

//自定义累加器实现wordCount
class WordCountAccumulator extends AccumulatorV2[String, mutable.Map[String, Int]] {
  private val result: mutable.Map[String, Int] = mutable.Map[String, Int]()

  override def isZero: Boolean = result.isEmpty

  override def copy(): AccumulatorV2[String, mutable.Map[String, Int]] = new WordCountAccumulator

  override def reset(): Unit = result.clear()

  //累加单词
  override def add(v: String): Unit = {
    //判断当前Map中是否有这个单词  有 value加1  无 放入(单词,1)
    result.put(v, result.getOrElse(v, 0) + 1)
  }

  //合并累加器
  override def merge(other: AccumulatorV2[String, mutable.Map[String, Int]]): Unit = {
    val toMergeMap = other.value
    for ((word, count) <- toMergeMap) {
      result.put(word, result.getOrElse(word, 0) + count)
    }
  }

  override def value: mutable.Map[String, Int] = result
}

class AccumulateTest {
  /**
    * 累加器实现wordCount
    * 输入：单词
    * 输出：所有单词的统计结果
    */
  @Test
  def wordCountTest(): Unit = {
    val rdd = sc.textFile("inputSpark/word.txt")
    val rdd1 = rdd.flatMap(line=>line.split(" "))
    val wcACC = new WordCountAccumulator
    sc.register(wcACC)
    rdd1.foreach(x=>wcACC.add(x))
    println(wcACC.value)
  }


  @Test
  def myAccumulatorTest(): Unit = {
    val list = List(1, 2, 3, 4)
    val rdd = sc.makeRDD(list, 2)
    //创建累加器
    val mySum = new MyLongAccumulator1(10)
    //注册累加器（通知spark告诉它这是累加器）
    sc.register(mySum, "mySum")
    //使用累加器的add方法进行累加操作
    rdd.foreach(x => mySum.add(x))
    //在Driver端使用value获取累加器的值
    println(mySum.value)

    //Thread.sleep(Long.MaxValue)

  }

  /**
    * 累加器实现
    */
  @Test
  def test(): Unit = {
    val list = List(1, 2, 3, 4)
    val rdd = sc.makeRDD(list, 2)
    //创建累加器
    val mySum = sc.longAccumulator("mySum")
    //使用累加器的add方法进行累加操作
    rdd.foreach(x => mySum.add(x))
    //在Driver端使用value获取累加器的值
    println(mySum.value)

    Thread.sleep(Long.MaxValue)
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
