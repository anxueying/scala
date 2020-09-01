package DAO.util

import org.apache.spark.SparkContext

/**
  * @author Shelly An
  * @create 2020/8/1 11:54
  *         从当前线程中抽取共享对象，在此处定义
  */
object EnvUtil {

  private val scLocal: ThreadLocal[SparkContext] = new ThreadLocal[SparkContext]()

  /**
    * 获取sc 具体功能
    * @return SparkContext
    */
  def getEnv(): SparkContext = {
    scLocal.get()
  }

  /**
    * 设置sc Application
    * @param sc SparkContext
    */
  def setEnv(sc: SparkContext): Unit = {
    scLocal.set(sc)
  }
}
