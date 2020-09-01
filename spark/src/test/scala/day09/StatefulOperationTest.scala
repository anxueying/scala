package day09

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.junit.{After, Test}

/**
  * @author Shelly An
  * @create 2020/8/21 9:42
  */
class StatefulOperationTest {
  private val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("My app")
  private val context = new StreamingContext(conf, Seconds(2))
  private val dsReceiver: ReceiverInputDStream[String] = context.socketTextStream("hadoop103", 4444)

  /**
    * UpdateStateByKey 每一次计算需要用到之前任务的结果
    */
  @Test
  def test1(): Unit = {
    //必须要配置ck目录，因为需要获取之前批次的数据信息
    context.checkpoint("output/updateByKey")

    //因为需要返回一个option，因此，计算结果需要用Some包一下
    // Option[A] (sealed trait) 有两个取值:
    //    1. Some[A] 有类型A的值
    //    2. None 没有值
    val result: DStream[(String, Int)] = dsReceiver.flatMap(_.split(" ")).map((_, 1)) //DStream[String] 输入流中的每行数据
      //updateStateByKey(func())  采集的所有key的个数有多少个，func()就要调多少次
      .updateStateByKey((seq: Seq[Int], opt: Option[Int]) => Some(seq.sum + opt.getOrElse(0)))
    result.print(100)
  }

  /**
    * WindowOperations 无状态
    */
  @Test
  def test2(): Unit = {
    val result: DStream[(String, Int)] = dsReceiver.flatMap(_.split(" ")).map((_, 1)).reduceByKeyAndWindow(
      _ + _, windowDuration = Seconds(2), Seconds(2)
    )
    result.print(100)
  }

  /**
    * WindowOperations 无状态
    */
  @Test
  def test3(): Unit = {
    val result: DStream[(String, Int)] = dsReceiver.flatMap(_.split(" ")).map((_, 1)).reduceByKeyAndWindow(
      _ + _, windowDuration = Seconds(4), Seconds(2)
    )
    result.print(100)
  }

  /**
    * WindowOperations 有状态
    */
  @Test
  def test4(): Unit = {
    context.checkpoint("output/reduceByKeyAndWindow")
    val result: DStream[(String, Int)] = dsReceiver.flatMap(_.split(" ")).map((_, 1)).reduceByKeyAndWindow(
      _ + _, _ - _, windowDuration = Seconds(4), Seconds(2)
    )
    result.print(100)
  }

  /**
    * WindowOperations 有状态，过滤
    */
  @Test
  def test5(): Unit = {
    context.checkpoint("output/reduceByKeyAndWindow")
    val result: DStream[(String, Int)] = dsReceiver.flatMap(_.split(" ")).map((_, 1)).reduceByKeyAndWindow(
      _ + _, _ - _, windowDuration = Seconds(4), Seconds(2), filterFunc = _._2 != 0
    )
    result.print(100)
  }

  /**
    * window
    */
  @Test
  def test6(): Unit = {
    val result: DStream[(String, Int)] = dsReceiver.window(Seconds(4), Seconds(2)) //先规定好窗口
      .flatMap(_.split(" ")).map((_, 1))
      .reduceByKey(_ + _)
    result.print(100)
  }


  /**
    * saveAsTextFiles
    */
  @Test
  def test7(): Unit = {
    val result: DStream[(String, Int)] = dsReceiver.window(Seconds(4), Seconds(2)) //先规定好窗口
      .flatMap(_.split(" ")).map((_, 1))
      .reduceByKey(_ + _)
    //每批结果都会保存，输出目录为 prefix+时间戳+suffix
    result.repartition(1).saveAsTextFiles("a", "b")
  }

  /**
    * foreachRDD
    */
  @Test
  def test8(): Unit = {
    val result: DStream[(String, Int)] = dsReceiver.window(Seconds(4), Seconds(2)) //先规定好窗口
      .flatMap(_.split(" ")).map((_, 1))
      .reduceByKey(_ + _)
    //每批结果都会保存，输出目录为 prefix+时间戳+suffix
    result.foreachRDD(rdd => println(rdd.collect.mkString(",")))
  }

  @After
  def init(): Unit = {
    //运行程序
    context.start()

    //启动分线程，执行关闭
    new Thread() {
      //判断是否需要关闭的函数
      def ifShouldNotStop(): Boolean = {
        //读取一个标记（数据库、文件系统）如路径  /..../_stop
        val rdd: RDD[String] = new SparkContext(conf).textFile("spark/inputSpark/ifStop.log")
        rdd.toString().toBoolean
      }
      //关闭
      override def run(): Unit = {
        //因为是流式处理，判断一次相当于当前状态要不要关，我们要的是每次处理都判断
        while (ifShouldNotStop()) {
          //如果是true 就睡一会儿 不关闭
          // 如果是false 就跳出while 运行context.stop()
          Thread.sleep(5000)
        }
      }
      context.stop(true,true)
    }.start()

    //当前线程阻塞，后续的代码都不会执行
    context.awaitTermination()
  }

}
