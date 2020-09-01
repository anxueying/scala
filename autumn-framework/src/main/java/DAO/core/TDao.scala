package DAO.core

import org.apache.spark.rdd.RDD
import DAO.util.EnvUtil

/**
  * @author Shelly An
  * @create 2020/8/1 12:04
  *         数据访问特质
  */
trait TDao {
  /**
    * 从集合中访问
    */
  def fromCollection(): Unit = {

  }

  /**
    * 从文件中访问
    */
  def fromFile(path: String): RDD[String] = {
    EnvUtil.getEnv().textFile(path)
  }
}