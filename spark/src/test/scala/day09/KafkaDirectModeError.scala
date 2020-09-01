package day09

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author Shelly An
  * @create 2020/8/10 16:32
  */
object KafkaDirectModeError {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("My app")
    val context = new StreamingContext(conf, Seconds(3))
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "hadoop102:9092",
      "client.id" -> "c1",
      "group.id" -> "atguigu",
      "enable.auto.commit" -> "true",
      "auto.commit.interval.ms" -> "500",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "auto.offset.reset" -> "earliest"
    )

    //有状态消费
    val ds: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](context,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](List("test1"), kafkaParams))

    val ds1 = ds.flatMap(record => {
//      if (record.value() == "d") {
//        throw new Exception("出异常了")
//      }
      record.value().split(" ")
    })
    val result = ds1.map((_,1)).reduceByKey(_+_)

    result.print(100)

    context.start()

    context.awaitTermination()

  }
}
