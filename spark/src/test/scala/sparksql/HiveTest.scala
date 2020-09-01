package sparksql

import java.util.Properties

import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.junit.{After, Test}

/**
  * @author Shelly An
  * @create 2020/8/17 8:38
  */
class HiveTest {
  private val sparkSession = SparkSession.builder()
    .config("spark.sql.warehouse.dir", "hdfs://master:9820/user/hive/warehouse")
    .enableHiveSupport().master("local[*]").appName("sparksql").getOrCreate()

  @After
  def close(): Unit = {
    sparkSession.stop()
  }


  @Test
  def test1(): Unit = {
    //插入数据
    sparkSession.sql("insert into table myidea.idea values('shelly')").show()
    //查询数据
    sparkSession.sql("select * from myidea.idea").show()
  }

  @Test
  def test2(): Unit = {
    val list = List(Emp("jack", 2000), Emp("mike", 3000))
    val rdd = sparkSession.sparkContext.makeRDD(list,1)
    import sparkSession.implicits._
    val ds = rdd.toDS()
    // ds写入到hive
    // 直接写
    ds.write.saveAsTable("temp")
    // insert into
    //ds.write.mode("append").saveAsTable("t100")
    // insert overwrite
    //ds.write.mode("overwrite").saveAsTable("t100")
  }
  
  
  @Test
  def test3():Unit={
    sparkSession.sql("use myidea")
    sparkSession.sql("select * from idea").show()
  }
}