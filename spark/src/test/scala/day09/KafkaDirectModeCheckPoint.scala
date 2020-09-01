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
object KafkaDirectModeCheckPoint {
  def main(args: Array[String]): Unit = {

    //会自动处理这个异常，不会让程序终止，但是仍然不会消费到d
    def rebuild(): StreamingContext = {
      val conf = new SparkConf().setMaster("local[*]").setAppName("My app")
      val context = new StreamingContext(conf, Seconds(3))

      //周期性的目录
      context.checkpoint("kafka")

      val kafkaParams = Map[String, Object](
        "bootstrap.servers" -> "hadoop102:9092",
        "client.id" -> "c1",
        "group.id" -> "atguigu",
        "enable.auto.commit" -> "false",
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
        if (record.value() == "d") {
          throw new UnknownError("出异常了！")
        }
        record.value().split(" ")
      })
      val result = ds1.map((_, 1)).reduceByKey(_ + _)

      result.print(100)

      context
    }

    //不是伴生对象，调用方法应该如下，而非context.getActiveOrCreate
    val context = StreamingContext.getActiveOrCreate("kakfa", rebuild)
    context.start()
    context.awaitTermination()

  }
}
