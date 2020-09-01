package day09

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.junit.{After, Before, Test}

/**
  * @author Shelly An
  * @create 2020/8/21 9:42
  */
class OperationTest {
  private val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("My app")
  private val context = new StreamingContext(conf, Seconds(2))
  private val dsReceiver: ReceiverInputDStream[String] = context.socketTextStream("hadoop103", 4444)

  /**
    * transform()
    */
  @Test
  def test1(): Unit = {
    val ds: DStream[Int] = dsReceiver.transform(rdd => rdd.map(str => str.toInt))
    ds.print(100)
  }

  /**
    * join 两个ds的元素必须都是k-v结构
    */
  @Test
  def test2(): Unit = {
    val data: DStream[(String, Int)] = context.socketTextStream("hadoop102", 4444).map((_,1))
    val dsKeyValue: DStream[(String, Int)] = dsReceiver.map((_,1))
    val result: DStream[(String, (Int, Int))] = dsKeyValue.join(data)

    result.print(100)

  }

  @After
  def init(): Unit = {
    context.start()
    context.awaitTermination()
  }

}
