package day09

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author Shelly An
  * @create 2020/8/10 16:32
  */
object KafkaDirectModeKafka {

  def main(args: Array[String]): Unit = {

    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("My app")

    val context = new StreamingContext(conf, Seconds(2))

    //kafkaParams ConsumerConfig
    val kafkaParams: Map[String, Object] = Map[String, Object](
      "bootstrap.servers" -> "hadoop102:9092",
      "client.id" -> "c1",
      "group.id" -> "atguigu3",
      "enable.auto.commit" -> "false",
      "auto.commit.interval.ms" -> "500",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "auto.offset.reset" -> "earliest"
    )

    //使用工具类创建      消费数据
    val ds: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream[String, String](context,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](List("test1"), kafkaParams))

    ds.foreachRDD( rdd =>{

      //kafka实现步骤1
      // Array[OffsetRange]
      val offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges

      //处理逻辑
      println(rdd.flatMap(record => {
//        if (record.value() == "d") {
//          //模拟异常
//          // throw new UnknownError("出异常了！")
//          throw new Exception("出异常了！")
//        }
        record.value().split(" ")
      }).map((_, 1)).reduceByKey(_ + _).collect().mkString(","))

      //kafka实现步骤2
      // 提交offset
      ds.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)

    })

    //运行程序
    context.start()

    context.awaitTermination()


  }

}
