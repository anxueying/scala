package day09

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

/**
  * @author Shelly An
  * @create 2020/8/10 15:02
  */
object QueueDStream {
  def main(args: Array[String]): Unit = {
    //cpu至少得有2核  一个运行driver 一个运行reciever 否则就只有reciever了
    val conf = new SparkConf().setMaster("local[*]").setAppName("My app")
    val context = new StreamingContext(conf,Seconds(3))
    val queue = mutable.Queue[RDD[String]]()
    //尝试true和false的不同
    val ds = context.queueStream(queue,true,null)
    val result = ds.flatMap(line=>line.split(" ")).map((_,1)).reduceByKey(_+_)
    val rdd = context.sparkContext.makeRDD(List("hello hi lily","hello hi"),1)

    result.print(100)
    //运行程序
    context.start()

    //想队列中放测试数据
    for (elem <- 1 to 10) {
      queue.enqueue(rdd)
      Thread.sleep(1000)
    }

    //程序启动后，不能停止，需要一直计算。阻塞当前程序，直到结束
    context.awaitTermination()


  }
}
