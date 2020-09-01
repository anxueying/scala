package wordcount

import DAO.core.TController

/**
  * @author Shelly An
  * @create 2020/8/1 12:06
  */
class WordCountController extends TController{
  private val wordCountService = new WordCountService()

  /**
    * 调度 不做任何逻辑操作 只是调用一下，并把结果处理
    */
  override def dispatcher(): Unit = {

    //分析
    val result: Array[(String, Int)] = wordCountService.analysis()
    //处理结果
    result.foreach(println)
  }
}
