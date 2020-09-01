package day09

import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.junit.Test

import scala.io.{BufferedSource, Source}

/**
  * @author Shelly An
  * @create 2020/8/22 9:36
  */
class SparkStreamStopTest {


  @Test
  def test(): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("My app")

    val context = new StreamingContext(conf, Seconds(2))


    // DS[String] :  输入流中的每行数据
    val ds: ReceiverInputDStream[String] = context.socketTextStream("hadoop102", 4444)

    val result: DStream[(String, Int)] = ds.window(Seconds(4), Seconds(2))
      .flatMap(_.split(" ")).map((_, 1))
      .reduceByKey(_ + _)

    result.foreachRDD(rdd => println(rdd.collect().mkString(",")))

    //运行程序
    context.start()

    //启动分线程，执行关闭
    new Thread() {

      //判断是否需要关闭
      def ifShouldNotStop(): Boolean = {

        // 读取一个标记（数据库，文件系统）   /应用程序/_stop
        ifStop()
        // true//不关闭
        //false //关闭
      }

      //关闭
      override def run(): Unit = {

        while (ifShouldNotStop()) {
          try
            Thread.sleep(5000)
          catch {
            case e: Exception => e.printStackTrace()
          }
        }

        // 关闭   stopGraceFully: 等收到的数据计算完成后再关闭
        context.stop(true, true)
        System.exit(0)
      }
    }.start()

    // 当前线程阻塞，后续的代码都不会执行！
    context.awaitTermination()

    //stop()

  }

  def ifStop(): Boolean = {
    //"inputSpark/ifStop.log"
    var bool: Boolean = false
    try
      Source.fromFile("ifstop/stopflag")
    catch {
      case e: Exception =>
        bool = true
    }
    bool
  }
}
