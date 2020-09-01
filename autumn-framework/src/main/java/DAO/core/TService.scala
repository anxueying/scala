package DAO.core

/**
  * @author Shelly An
  * @create 2020/8/1 12:03
  */
trait TService {
  /**
    * 分析 这里必须有Any，不然继承的类无法有返回值
    */
  def analysis():Any
}
