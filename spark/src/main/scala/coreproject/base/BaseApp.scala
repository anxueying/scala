package coreproject.base

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/5 15:12
  */
abstract class BaseApp {
  var sc: SparkContext = null
  //当前应用程序输出结果的目录
  val outputPath: String

  def runApp(op: => Unit): Unit = {
    start()
    try {
      //传入运行逻辑
      op
    } catch {
      case exception: Exception => exception.printStackTrace()
    } finally {
      sc.stop()
    }
  }


  /**
    * 每次运行前，自动删除输出目录 使用hadoop的包
    */

  def start(): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("My app")

    sc = new SparkContext(conf)
    val fs = FileSystem.get(new Configuration())
    val path = new Path(outputPath)
    if (fs.exists(path)) {
      //递归删除
      fs.delete(path, true)
    }
  }
}
