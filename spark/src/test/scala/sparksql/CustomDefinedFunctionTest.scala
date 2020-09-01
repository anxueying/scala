package sparksql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SparkSession, functions}
import org.junit.{After, Test}

/**
  * @author Shelly An
  * @create 2020/8/14 8:40
  */
class CustomDefinedFunctionTest {
  private val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("My app")
  private val sparkSession: SparkSession = SparkSession.builder().config(conf).getOrCreate()
  /**
    * 自定义UDAF: 多进一出
    * 3.x版本：DSL中使用
    */
  @Test
  def test4(): Unit = {
    val myAvg = new MyAvg2
    //不需要注册，因为不在sql里用
    val df = sparkSession.read.json("inputSpark/employees.json")
    import sparkSession.implicits._
    val ds = df.as[Emp]
    val avgColumn = myAvg.toColumn
    ds.select(avgColumn.name("avgSalary")).show()
  }

  /**
    * 自定义UDAF: 多进一出
    * 3.x版本：认为UserDefineAggregateFunction过时，因为它是弱类型的。推荐使用Aggregator
    */
  @Test
  def test3(): Unit = {
    val myAvg = new MyAvg
    //注册时和2.x版本不太一样 functions.udaf(myAvg)
    sparkSession.udf.register("myAvg",functions.udaf(myAvg))
    val df = sparkSession.read.json("inputSpark/employees.json")
    df.createTempView("emp")
    sparkSession.sql("select myAvg(salary) from emp").show()
  }


  /**
    * 自定义UDAF: 多进一出
    * 相当于聚集函数  max count avg sum min
    *
    * 两套体系
    * 2.x版本：UserDefineAggregateFunction
    * 3.x版本：认为UserDefineAggregateFunction过时，因为它是弱类型的。推荐使用Aggregator
    */
  @Test
  def test2(): Unit = {
    val mySum = new MySum
    sparkSession.udf.register("mySum", mySum)
    val df = sparkSession.read.json("inputSpark/employees.json")
    df.createTempView("emp")
    sparkSession.sql("select mySum(salary) from emp").show()
    //df.show()
  }

  /**
    * 自定义UDF 一进一出
    */
  @Test
  def test1(): Unit = {
    //注册udf
    sparkSession.udf.register("hello", (name: String) => "hello:" + name)
    val df = sparkSession.read.json("inputSpark/employees.json")
    df.createTempView("emp")
    sparkSession.sql("select hello(name) from emp").show()

    df.show()
  }

  @After
  def close(): Unit = {
    sparkSession.stop()
  }
}

