package streamingproject.app

import java.sql.{Connection, PreparedStatement}

import org.apache.spark.streaming.dstream.DStream
import streamingproject.bean.AdsInfo
import streamingproject.utils.JDBCUtil

/**
  * @author Shelly An
  * @create 2020/8/22 10:39
  *         实时统计每天各地区各城市各广告的点击总流量，并将其存入MySQL。
  *
  *         AdsInfo(1598013910484,华南,深圳,103,2,2020-08-21,20:45) =>
  *         ((日期，地区，城市，广告),1) => 聚合 ((日期，地区，城市，广告),N)
  *
  *         总点击量是有状态的，需要使用updateStateByKey
  *
  *         写入数据库的时候，
  *   1. 注意数据的字段长度
  *   2. 要用update还是insert呢？
  *         用update要用主键更新，那第一次的数据没办法更新。
  *         用insert into的话，主键冲突，mysql也没有insert overwrite
  *         所以insert后要使用 on duplicate key update 字段=values(字段) 更新这个主键对应的字段
  *
  *         INSERT INTO area_city_ad_count VALUES ('2020-08-21','华南','深圳','103',2)
  *         ON DUPLICATE KEY UPDATE count = VALUES(count)
  */
object Function2Demo extends BaseApp {
  def main(args: Array[String]): Unit = {
    runApp {

      context.checkpoint("output/function2")

      //读取最新一批数据 从kafka获取数据，封装为ds, 泛型为自定义类
      val ds: DStream[AdsInfo] = getAllBeans(getDStream())
      //ds.print(10)

      //统计完成的结果
      val result: DStream[((String, String, String, String), Int)] =
        ds.map(ads => ((ads.dayString, ads.area, ads.city, ads.adsId), 1))
        .updateStateByKey((values: Seq[Int], state: Option[Int]) => Some(values.sum + state.getOrElse(0)))

      //存入mysql
      result.foreachRDD(rdd => {
        rdd.foreachPartition(iter => {
          //每个分区创造一个connect连接
          val connection: Connection = JDBCUtil.getConnection()
          //准备sql
          val sql =
            """
              |INSERT INTO area_city_ad_count VALUES (?,?,?,?,?)
              |ON DUPLICATE KEY UPDATE count = count + ?
            """.stripMargin

          val ps: PreparedStatement = connection.prepareStatement(sql)

          //rdd分区中的每个数据都执行写出
          iter.foreach {
            case ((day, area, city, adsId), count) => {
              //填充sql
              ps.setString(1, day)
              ps.setString(2, area)
              ps.setString(3, city)
              ps.setString(4, adsId)
              ps.setInt(5, count)
              ps.setInt(6, count)

              //执行写入
              ps.executeUpdate()
            }
          }

          //每个分区写完时关闭资源
          ps.close()
          connection.close()
        })
      })
    }
  }
}
