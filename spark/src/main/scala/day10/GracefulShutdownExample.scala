package day10

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author Shelly An
  * @create 2020/8/22 9:26
  */
object GracefulShutdownExample {
  //程序出现异常后打一个标记，标记存放在可靠的外部系统如hdfs、redis等。
  val shutdownMarker = "hdfs://hadoop102:9820/shutdownmarker"
  var stopFlag: Boolean = false

  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("SparkStreamingGracefulShutdown")

    val ssc = new StreamingContext(conf, Seconds(5))
    val lines = ssc.socketTextStream("hadoop102", 4444)
    lines.print()
    ssc.start()
    val checkIntervalMillis = 10000
    var isStopped = false

    //Driver 不断重复扫描标记，判断是否需要停止作业。
    while (!isStopped) {
      println("calling awaitTerminationOrTimeout")
      isStopped = ssc.awaitTerminationOrTimeout(checkIntervalMillis)
      if (isStopped)
        println("confirmed! The streaming context is stopped. Exiting application...")
      else
        println("Streaming App is still running. Timeout...")
      checkShutdownMarker
      if (!isStopped && stopFlag) {
        println("stopping ssc right now")
        ssc.stop(true, true)
        println("ssc is stopped!!!!!!!")
      }
    }
  }

  /**
    * 检查这个文件是否存在，存在即
    */
  def checkShutdownMarker = {
    if (!stopFlag) {
      val fs = FileSystem.get(new Configuration())
      stopFlag = fs.exists(new Path(shutdownMarker))
    }

  }
}
