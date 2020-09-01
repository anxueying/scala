package day09

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author Shelly An
  * @create 2020/8/10 11:16
  *        core SparkContext 构建RDD
  *        sql SparkSession  构建 DF,DS
  *        streaming  StreamingContext   构建一个DStream
  *
  *        接收 netcat客户端发送的数据
  *        1. 连接客户端
  *        2. 接收数据，进行wordcount
  */
object WordCount {
  def main(args: Array[String]): Unit = {
    //cpu至少得有2核  一个运行driver 一个运行reciever 否则就只有reciever了
    val conf = new SparkConf().setMaster("local[*]").setAppName("My app")
    val context = new StreamingContext(conf,Seconds(3))
    //逻辑处理 建立一个基于TCP的socket，接收UTF8编码的文本数据，以\n作为每行的分割的DS
    val ds = context.socketTextStream("hadoop103",4444)

    //DS[String]：输入流中的每行数据  高度抽象原语
    val result = ds.flatMap(line=>line.split(" ")).map((_,1)).reduceByKey(_+_)

        //控制台打印
    result.print(1000)

    //运行程序
    context.start()

    //程序启动后，不能停止，需要一直计算。阻塞当前程序，直到结束
    context.awaitTermination()
  }
}
