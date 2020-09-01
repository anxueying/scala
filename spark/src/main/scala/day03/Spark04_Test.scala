package day03

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/8/1 13:12
  */
object Spark04_Test {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("RDD")
    val sc = new SparkContext(sparkConf)
    val list = List[Int](1,4,3,2,5,6)
    val rdd: RDD[Int] = sc.makeRDD(list,3)
    //第二个 编号为1
    val rdd1: RDD[Int] = rdd.mapPartitionsWithIndex(
      (index, iter) => {
        if (index == 1) {
          iter
        } else {
          //null 不可以 会发生空指针异常
          //Nil.iterator  可以  Nil集合就是空的
          iter.filter(x=>false)  // 可以 把迭代器的元素都过滤掉就是空的迭代器
        }
      }
    )

    rdd1.collect().foreach(println)

    sc.stop()
  }
}
