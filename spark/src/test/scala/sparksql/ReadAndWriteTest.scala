package sparksql

import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.junit.{After, Test}

/**
  * @author Shelly An
  * @create 2020/8/17 8:38
  */
class ReadAndWriteTest {
  private val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("My app")
  private val sparkSession: SparkSession = SparkSession.builder().config(conf).getOrCreate()

  @After
  def close(): Unit = {
    sparkSession.stop()
  }

  /**
    * json读
    */
  @Test
  def test1(): Unit = {
    //通用的读取
    val df1 = sparkSession.read.format("json").load("inputSpark/employees.json")
    df1.show()
    //专门读取json
    val df2 = sparkSession.read.json("inputSpark/employees.json")
    df2.show()
  }

  /**
    * json写
    */
  @Test
  def test2(): Unit = {
    val list = List(Emp("shelly", 20000), Emp("jason", 15000), Emp("tutu", 3000))
    val rdd = sparkSession.sparkContext.makeRDD(list, 1)
    import sparkSession.implicits._
    val ds = rdd.toDS()
    //通用的写
    ds.write.mode("overwrite").format("json").save("output/emp.json")
    //json专用的写
    ds.write.mode("ignore").json("output/emp1.json")
    //追加写
    ds.write.mode(SaveMode.Append).json("output/emp1.json")
  }

  /**
    * json读
    */
  @Test
  def test3(): Unit = {
    //通用的读取
    val df1 = sparkSession.read.format("json").load("inputSpark/employees.json")
    df1.show()
  }

  /**
    * csv读
    */
  @Test
  def test4(): Unit = {
    //通用的读取
    val df = sparkSession.read.format("csv").load("inputSpark/employees.csv")
    df.show()
    //专用的读
    val df1 = sparkSession.read.csv("inputSpark/employees.csv")
    df1.show()
  }

  /**
    * csv读
    * 1. 其他分隔符
    * 2. csv中包含列名
    */
  @Test
  def test5(): Unit = {
    //通用的读取
    val df = sparkSession.read.option("sep", ":").option("header", "true")
      .format("csv").load("inputSpark/employees1.csv")
    df.show()
    //专用的读
    val df1 = sparkSession.read.option("sep", ":").option("header", "true")
      .csv("inputSpark/employees1.csv")
    df1.show()
  }

  /**
    * csv写
    */
  @Test
  def test6(): Unit = {
    val list = List(Emp("shelly", 20000), Emp("jason", 15000), Emp("tutu", 3000))
    val rdd = sparkSession.sparkContext.makeRDD(list, 1)
    import sparkSession.implicits._
    val ds = rdd.toDS()
    //通用的写
    ds.write.option("sep", ":").mode("overwrite").format("csv").save("output/emp.csv")
    //csv专用的写
    ds.write.mode("ignore").csv("output/emp1.csv")
    //追加写
    ds.write.mode(SaveMode.Append).csv("output/emp1.csv")
  }

  /**
    * JDBC读
    */
  @Test
  def test7(): Unit = {
    val props = new Properties()
    /*
      JDBC中能写什么参数 参考JDBCOptions 223行
     */
    props.put("user", "root")
    props.put("password", "root")
    val df = sparkSession.read
      .jdbc("jdbc:mysql://localhost:3306/myemployees", "student", props)
    //全表查询 不传参数默认只显示前20条，传入参数n显示n条
    df.show(2)
    //全表查询 显示所有数据
    df.createTempView("student")
    sparkSession.sql("select * from student where 1=1").show()
    //指定查询
    sparkSession.sql("select * from student where sage is null").show()
  }

  /**
    * 写JDBC
    */
  @Test
  def test8(): Unit = {
    val list = List(Emp("shelly", 20000), Emp("jason", 15000), Emp("tutu", 3000))
    val rdd = sparkSession.sparkContext.makeRDD(list, 1)
    import sparkSession.implicits._
    val ds = rdd.toDS()

    val props = new Properties()
    /*
      JDBC中能写什么参数 参考JDBCOptions 223行
     */
    props.put("user", "root")
    props.put("password", "root")
    //表名可以是已经存在的表，也可以是一张新表（一般是新表）
    ds.write.jdbc("jdbc:mysql://localhost:3306/myemployees", "t1", props)
  }

  /**
    * 写JDBC
    */
  @Test
  def test9(): Unit = {
    val list = List(Emp("shelly", 20000), Emp("jason", 15000), Emp("tutu", 3000))
    val rdd = sparkSession.sparkContext.makeRDD(list, 1)
    import sparkSession.implicits._
    val ds = rdd.toDS()

    val props = new Properties()
    /*
      JDBC中能写什么参数 参考JDBCOptions 223行
     */
    props.put("user", "root")
    props.put("password", "root")
    //表名可以是已经存在的表，也可以是一张新表（一般是新表）
    ds.write.option("dbtable", "t2").option("user", "root").option("password", "root")
      .option("url", "jdbc:mysql://localhost:3306/myemployees").mode("append")
      .format("jdbc").save()
  }
}
