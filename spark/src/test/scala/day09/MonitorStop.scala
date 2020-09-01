package day09


import java.net.URI

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.streaming.{StreamingContext, StreamingContextState}

/**
  * @author Shelly An
  * @create 2020/8/21 18:31
  */
class MonitorStop(ssc: StreamingContext) extends Runnable {
  override def run(): Unit = {
    val fs: FileSystem = FileSystem.get(new URI("hdfs://hadoop102:9820"),
      new Configuration(), "atguigu")

    while (true) {
      try
        Thread.sleep(5000)
      catch {
        case e: Exception => e.printStackTrace()
      }
      val state: StreamingContextState = ssc.getState()
      val bool: Boolean = fs.exists(new Path("hdfs://hadoop102:9820/stopSpark"))

      if (bool) {
        if (state == StreamingContextState.ACTIVE) {
          ssc.stop(true, true)
          System.exit(0)
        }
      }
    }
  }
}
