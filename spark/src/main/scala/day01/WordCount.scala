package day01

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Shelly An
  * @create 2020/7/29 9:35
  */
object WordCount {
  /**
    * 数据可以从HDFS读取，也可以从本地文件系统（windows）读取
    * main中的程序，使用相对路径  input/hello1.txt，是相对于project
    *
    * hadoop  MapReduce
    * 1， 定义mapper、reducer
    * 2. 定义driver
    *     ① configuration  ： job的配置
    *     ② job.getInstance(conf) ：基于配置创建Job
    *     ③ 各种设置job
    *     ④ 提交 job.waitForCompletion()
    *           构建Cluster
    *           构建JobSubmitter
    *           Job ---> 实例化为 --> JobContextImpl --> 包装并传递为 --> Mapper(或Reducer)中的context
    *
    *           JobContextImpl：应用上下文，这个对象记录了应用的来龙去脉
    *             上文：应用的各种配置
    *             下文：使用应用完成某种功能，如写出，获取文件系统，获取切片对象等等
    *      在Driver中，需要完成的就是构建应用上下文，由应用上下文和集群进行交互，提交和运行作业!
    *     *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *   *
    *  spark
    *  1. 定义各种RDD
    *  2. driver
    *  ① configuration  ： job的配置
    *  ② 基于configuration构建应用上下文
    */
  def main(args: Array[String]): Unit = {

    /**
      *
      *
      *
      * SparkConf spark的configuration，整个应用的配置对象，
      *           一旦创建之后，对自动读取系统中的spark.*开头的变量。
      *           用户也可以调用对象，手动设置，手动设置优先级更高
      *
      * SparkContext spark函数的入口,应用上下文。
      *              一个sparkcontext就代表和Spark集群的一个连接！
      *              可以用来创建RDD,累加器，广播变量，进行编程！
      *
      *              RDD,累加器，广播变量：
      *              他们三个都是spark中的编程模型，类似Mapper,Reducer,Combiner
      */

    // 创建spark的配置对象
    val conf:SparkConf = new SparkConf().setMaster("local").setAppName("My app")
    //创建应用上下文
    val sparkContext = new SparkContext(conf)

    //编程代码
    //读取文件
    /**
      * textFile只可读取text文件utf-8编码，位置hdfs或本地（需保证每个节点都可读取）。返回一个RDD[String]
      * 本质是使用hadoop提供的inputformat来读取文件中的数据。
      *     inputformat --> TextInputFormat  --> 将数据封装成k，v对
      *                                           k：每行数据的偏移量
      *                                           v：一行内容
      *  因此，可知 RDD[String] 返回的是每行的内容
      *  暂且理解RDD是一个集合，可以使用scala中的集合操作的方法，操作RDD
      *  Spark中提供了和Scala集合操作同名，同功效的方法
      *
      * 将RDD中定义的方法，称为算子！
      * 分为两类：
      * 1. Transform operation: 转换算子      将一个RDD转为另一个RDD
      *                         懒执行！不会创建Job
      * 2. Action operation ： 行动算子       真正提交Job，运行Job
      */

    /**
      * 转换算子 将一个RDD转为另一个RDD  这时任务还没开始 可以通过看web页面，发现没有创建任务
      */
    val rdd = sparkContext.textFile("inputSpark")

    //切分单词
    val rdd1 = rdd.flatMap(line=>line.split(" "))
    //分组
    val result = rdd1.groupBy(word => word).map({
      case (word,wordList) => (word,wordList.size)
    })

    //在控制台打印
    /**
      * collect 将结果收集到Driver中 ---分布式计算，在集群中运行，因此需要将结果收集
      * Action Operation 行动算子  真正提交并运行job
      */
    println(result.collect().mkString(","))

    //关闭上下文  保证一个JVM中，只能有一个active的SparkContext！
    sparkContext.stop()
  }
}
