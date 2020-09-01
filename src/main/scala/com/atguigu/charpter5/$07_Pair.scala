package com.atguigu.charpter5

/**
  * @author Shelly An
  * @create 2020/7/25 15:57
  *        sprak本来就是并行的 后续不太会用这个
  */
object $07_Pair {
  def main(args: Array[String]): Unit = {
    val list = List(1,2,3,5,6)
    /**
      * main
      * main
      * main
      * main
      * main
      */
    list.foreach(x=>println(Thread.currentThread().getName))

    /**
      * scala-execution-context-global-15
      * scala-execution-context-global-16
      * scala-execution-context-global-12
      * scala-execution-context-global-13
      * scala-execution-context-global-14
      */
    list.par.foreach(x=>{
      println(Thread.currentThread().getName)
    })

  }
}
