package day01

import DAO.core.TApplication
import DAO.util.EnvUtil


/**
  * @author Shelly An
  * @create 2020/7/29 10:23
  *        封装环境（每个应用程序一样）
  *        封装逻辑（每个应用程序不一样）
  */
object WordCount3 extends App with TApplication {

    execute{
      val rdd = EnvUtil.getEnv().textFile("inputSpark")

      val rdd1 = rdd.flatMap(_.split(" "))
        .map((_,1))
        .reduceByKey((x,y)=>x+y)

      println(rdd1.collect().mkString(","))
    }

}
