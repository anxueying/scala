package day02

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}
import org.junit.{After, Before, Test}

/**
  * @author Shelly An
  * @create 2020/8/2 21:14
  */
class DependencyTest {

  @Test
  def dependencyTest():Unit={
    val list = List(1, 2, 3, 4)
    val rdd1 = sc.makeRDD(list,2)
    //查看依赖关系 List() 由内存创建 无依赖
    println(rdd1.dependencies)
    val rdd2 = rdd1.map(x=>x+4)
    val rdd3 = rdd2.map(x=>x+2)
    val rdd4 = rdd3.map(x=>x+1)
    //查看依赖关系 List(org.apache.spark.OneToOneDependency@3d278b4d)  1对1依赖（窄依赖）--独生子女
    println(rdd4.dependencies)
    val rdd5 = rdd4.map(x=>(x,1))
    val rdd6 = rdd5.groupByKey(3)
    //查看依赖关系 List(org.apache.spark.ShuffleDependency@5477a1ca)  1对n依赖（宽依赖/shuffle依赖）
    println(rdd6.dependencies)
  }

  @Test
  def consanguinity(): Unit = {
    val list = List(1, 2, 3, 4)
    val rdd1 = sc.makeRDD(list,2)
    val rdd2 = rdd1.map(x=>x+4)
    val rdd3 = rdd2.map(x=>x+2)
    val rdd4 = rdd3.map(x=>x+1)
    val rdd5 = rdd4.map(x=>(x,1))
    val rdd6 = rdd5.groupByKey(3)
    //查看血缘关系
    println(rdd6.toDebugString)
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
