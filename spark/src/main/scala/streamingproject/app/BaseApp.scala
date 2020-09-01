package streamingproject.app

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import streamingproject.bean.AdsInfo

/**
  * @author Shelly An
  * @create 2020/8/21 20:50
  */
abstract class BaseApp {
  val context =new StreamingContext(new SparkConf().setMaster("local[*]").setAppName("My app"), Seconds(5))

  def runApp(op: => Unit)={

    op

    context.start()

    context.awaitTermination()

  }

  //提供方法，从Kafka中读取数据，返回DS
  def getDStream(): InputDStream[ConsumerRecord[String, String]]={

    val kafkaParams: Map[String, Object] = Map[String, Object](
      "bootstrap.servers" -> "hadoop102:9092",
      "client.id" -> "c1",
      "group.id" -> "atguigu",
      "enable.auto.commit" -> "true",
      "auto.commit.interval.ms" -> "500",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "auto.offset.reset" -> "earliest"
    )

    //使用工具类创建      消费数据
    val ds: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](context,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](List("mytest"), kafkaParams))

    ds
  }

  def getAllBeans(ds: InputDStream[ConsumerRecord[String, String]])={
    val beans: DStream[AdsInfo] = ds.map(record => {
      val words: Array[String] = record.value().split(",")
      AdsInfo(words(0).toLong, words(1), words(2), words(3), words(4))
    })
    beans
  }

}
