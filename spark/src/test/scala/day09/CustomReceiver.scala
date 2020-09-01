package day09

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

/**
  * @author Shelly An
  * @create 2020/8/10 15:14
  */
object CustomReceiver {
  def main(args: Array[String]): Unit = {
    //cpu至少得有2核  一个运行driver 一个运行reciever 否则就只有reciever了
    val conf = new SparkConf().setMaster("local[*]").setAppName("My app")
    val context = new StreamingContext(conf,Seconds(3))
    val queue = mutable.Queue[RDD[String]]()

    val myReceiver = new MyReceiver("hadoop103",4444)
    val ds = context.receiverStream(myReceiver)
    val result = ds.flatMap(line=>line.split(" ")).map((_,1)).reduceByKey(_+_)

    result.print(100)
    //运行程序
    context.start()

    //程序启动后，不能停止，需要一直计算。阻塞当前程序，直到结束
    context.awaitTermination()

  }
}

class MyReceiver(host:String,port:Int) extends Receiver[String](StorageLevel.MEMORY_ONLY){
  /**
    * 采集之前执行一些安装
    *   模拟从hadoop 3333 读取数据
    * 需要组件：
    *
    */
  var socket:Socket =null
  var reader:BufferedReader = null
  override def onStart(): Unit = {
    try {
      socket = new Socket(host, port)
    } catch {
      case exception: Exception =>{
        //处理异常并退出此方法
        restart("连接异常，重新连接中")
        return
      }
    }

    println("成功建立连接，开始收集数据")

    //尝试读取每行数据，以行为单位封装
    reader = new BufferedReader(new InputStreamReader(socket.getInputStream,"utf-8"))


    //开始收数据
    receiverData()

  }

  /**
    * 在停止接收数据前，清理之前创建的组建
    */
  override def onStop(): Unit = {
    if (socket!=null) {
      socket.close()
      socket=null
    }

    if (reader!=null) {
      reader.close()
      reader=null
    }
  }


  def receiverData()={
    //新开启一个线程收集数据
    new Thread(){
      //设置当前线程为守护线程 当前线程依附于Reciever所在的main线程
      //如果一个JVM中，只有守护线程，JVM就会关闭
      setDaemon(true)

      //收数据的逻辑
      override def run(): Unit = {
        try {
          var line = reader.readLine()
          //收到一行数据
          while (socket.isConnected && line != null) {
            //存储数据
            store(line)
            //继续读下一行
            line = reader.readLine()
        }
        }catch {
          case exception: Exception =>
        }finally {
          onStop()
          restart("收集数据异常，正在重试")
        }
      }
    }.start()
  }
}