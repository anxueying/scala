package DAO.core

import DAO.util.EnvUtil
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 11:39
  *         当写一个应用程序时直接继承这个类，直接使用即可
  *         因为应用程序不想使用main方法，还需继承App，因此此处使用trait来定义Application。
  *         当然，application继承app这个方法也可
  *         特质的名字前面加一个T来表示更清晰易懂
  */
trait TApplication {

  //控制抽象：把代码逻辑作为参数传递给一个函数
  def execute(op: => Unit) = {
    val conf:SparkConf = new SparkConf().setMaster("local").setAppName("My app")

    val sparkContext = new SparkContext(conf)

    EnvUtil.setEnv(sparkContext)
    //此处模仿breakable
    try {
      op
    } catch {
      case ex:Exception => ex.printStackTrace()
    } finally {
      sparkContext.stop()
    }
  }
}
