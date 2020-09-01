package sqlproject.app

import org.apache.spark.sql.{SparkSession, functions}
import sqlproject.function.{MyAggFunction, MyAggFunction2}

/**
  * @author Shelly An
  * @create 2020/8/19 21:25
  */
object MainApp {
  def main(args: Array[String]): Unit = {
    //自定义UDAF函数
    val myAgg = new MyAggFunction
    val myAggNew = new MyAggFunction2

    val sparkSession = SparkSession.builder()
      .config("spark.sql.warehouse.dir", "hdfs://hadoop102:9820/user/hive/warehouse")
      .enableHiveSupport().master("local[*]").appName("sparksql")
      .getOrCreate()

    //注册UDAF函数
    //2.0
    sparkSession.udf.register("myAgg",myAgg)
    //3.0
    sparkSession.udf.register("myAggNew",functions.udaf(myAggNew))

    sparkSession.sql("use sparksql")

    val sql1 =
      """
        |SELECT c.area, p.product_name, c.city_name
        |			FROM (
        |				SELECT city_id, click_product_id
        |				FROM sparksql.user_visit_action
        |				WHERE click_product_id != -1
        |			) v
        |				LEFT JOIN sparksql.city_info c ON v.city_id = c.city_id
        |				LEFT JOIN sparksql.product_info p ON v.click_product_id = p.product_id
      """.stripMargin

    val df1 = sparkSession.sql(sql1)
    df1.createTempView("t1")

    val sql2 =
      """
        |SELECT
        |		  area,product_name,count(*) clickcount,myAgg(city_name) city_demo,myAggNew(city_name) city_demoNew
        |		FROM t1
        |  GROUP BY  area,product_name
      """.stripMargin

    val df2 = sparkSession.sql(sql2)
    df2.createTempView("t2")

    val sql3 =
      """
        | SELECT  area,product_name,clickcount,city_demo,city_demoNew,RANK () OVER(PARTITION by area order by clickcount DESC ) rn
        |	FROM t2
      """.stripMargin

    val df3 = sparkSession.sql(sql3)
    df3.createTempView("t3")

    val sql4 =
      """
        | SELECT area,product_name,clickcount,city_demo,city_demoNew
        | FROM t3
        | where rn<=3
      """.stripMargin

    val df4 = sparkSession.sql(sql4)
    //保存为文件
    df4.coalesce(1).write.mode("overwrite").csv("spark/output/sparksql.csv")

    sparkSession.close()
  }
}
