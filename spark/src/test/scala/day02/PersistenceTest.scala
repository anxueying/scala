package day02

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Before, Test}

/**
  * @author Shelly An
  * @create 2020/8/2 21:14
  */
class PersistenceTest {

  /**
    * 广播变量
    */
  @Test
  def test(): Unit = {
    val list = List(1, 2, 3, 4, 5, 6, 7, 8)
    val rdd1 = sc.makeRDD(list, 2)
    val list1 = List(1, 2, 3, 4, 5, 6, 7,11,23,12,30)
    //传统写法
    //rdd1.filter(x=>list1.contains(x))

    val bc = sc.broadcast(list1)
    rdd1.filter(x=>bc.value.contains(x)).foreach(println)
  }


  @Test
  def test1(): Unit = {
    val list = List(1, 2, 3, 4)
    val rdd1 = sc.makeRDD(list, 2)
    sc.setCheckpointDir("ck")
    rdd1.checkpoint()
    val rdd2 = rdd1.map(x => {
      println("map")
      (x, 1)
    })

    rdd2.checkpoint()
    //缓存
    rdd2.cache()
    rdd2.collect()
    rdd2.saveAsTextFile("outputSpark")
    Thread.sleep(Long.MaxValue)
  }

  var sc: SparkContext = null

  /**
    * 每次运行前，自动删除输出目录 使用hadoop的包
    */
  @Before
  def start(): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("My app")

    sc = new SparkContext(conf)
    val fs = FileSystem.get(new Configuration())
    val path = new Path("outputSPark")
    if (fs.exists(path)) {
      //递归删除
      fs.delete(path, true)
    }
  }


  /**
    * 每次运行后，关闭资源
    */
  @After
  def stop() = {
    sc.stop()
  }
}
