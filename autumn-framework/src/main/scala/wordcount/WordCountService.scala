package wordcount

import DAO.core.TService

/**
  * @author Shelly An
  * @create 2020/8/1 12:08
  *        服务特质
  */
class WordCountService extends TService{
  private val wordCountDao = new WordCountDao()
  /**
    * 分析 每次只需要改这里就可以了
    */
  override def analysis()= {
    val rdd =  wordCountDao.fromFile("inputSpark")

    val rdd1 = rdd.flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey((x,y)=>x+y)

    rdd1.collect()
  }
}
