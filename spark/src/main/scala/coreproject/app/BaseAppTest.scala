package coreproject.app

import coreproject.base.BaseApp

/**
  * @author Shelly An
  * @create 2020/8/5 15:17
  */
object BaseAppTest extends BaseApp{
  override val outputPath: String = "output/test"

  def main(args: Array[String]): Unit = {
    runApp{
      val list = List(1,2,3,4)
      val rdd = sc.makeRDD(list,2)
      rdd.saveAsTextFile(outputPath)
    }
  }
}
