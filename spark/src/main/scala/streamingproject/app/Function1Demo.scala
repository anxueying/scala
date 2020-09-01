package streamingproject.app


import java.sql.{Connection, PreparedStatement, ResultSet}

import org.apache.spark.streaming.dstream.DStream
import streamingproject.bean.AdsInfo
import streamingproject.utils.JDBCUtil

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
  * @author Shelly An
  * @create 2020/8/22 10:39
  *         实现实时的动态黑名单机制：将每天对某个广告点击超过 100 次的用户拉黑
  *
  *         当天，计算当天的数据！
  *         将这天中，所有用户对所有广告点击的次数进行累加！
  *         一旦发现，某个用户，对某个广告点击超过100次，将用户加入黑名单！
  *
  *         有状态！  将一天的数据累计！
  *         自己维护状态！不需要调用有状态的算子！
  *         ①每批数据，计算完成之后，将数据写入到数据库
  *         ②下一批数据计算时，从数据库读取数据，累加状态，将状态写入数据库
  *         有表记录状态：user_ad_count
  *
  *         有黑名单表，记录写入的用户名：black_list
  *
  *
  *         逻辑：  ①每批数据到达之后，先计算当前这批数据，每个用户，对每个广告的点击次数
  *         (日期，101,1,10),(日期,102,1,10)..
  *
  *
  *         ③将状态先合并 ， 再写入到  user_ad_count
  *
  *         ④写入之后，再查询 user_ad_count 看哪些 用户超过了 100
  *
  *         ⑤再将复合条件的写入黑名单
  *
  */
object Function1Demo extends BaseApp {
  def main(args: Array[String]): Unit = {
    runApp {
      val ds: DStream[AdsInfo] = getAllBeans(getDStream())
      val userClick: DStream[((String, String, String), Int)] =
        ds.map(bean => ((bean.dayString, bean.userId, bean.adsId), 1)).reduceByKey(_ + _)

      //合并状态 查询之前保存的状态
      userClick.foreachRDD(rdd => {
        //这为啥用foreach，不用foreachPartition : 因为用foreach写入不太方便？？为啥？？
        rdd.foreachPartition(iter => {
          val connection: Connection = JDBCUtil.getConnection()
          val sql: String =
            """
              |insert into user_ad_count values(?,?,?,?)
              |on duplicate key update count=?
            """.stripMargin

          val ps: PreparedStatement = connection.prepareStatement(sql)



          iter.foreach {
            case ((day, userId, adsId), count) => {
              ps.setString(1, day)
              ps.setString(2, userId)
              ps.setString(3, adsId)
              ps.setInt(4, count)
              ps.setInt(5, count)

              ps.executeUpdate()
              // 因为上面已经对key做了聚合，因此不同分区不会有重复的key，各自更新各自的map没有问题

            }
          }
          ps.close()
          //查询当前哪些用户超过100
          val sql1: String =
            """
              |select userid from user_ad_count
              |where count>100
            """.stripMargin
          val rs: ResultSet = connection.prepareStatement(sql1).executeQuery()
          val
          blackList: ListBuffer[String] = ListBuffer[String]()

          while (rs.next()) {
            blackList.append(rs.getString("userid"))
          }

          //写入黑名单
          val sql2: String =
            """
              |insert into black_list values(?)
              |on duplicate key update userid=?
            """.stripMargin

          val ps1: PreparedStatement = connection.prepareStatement(sql2)
          for (elem <- blackList) {
            ps1.setString(1, elem)
            ps1.setString(2, elem)
            ps1.executeUpdate()
          }
          ps1.close()
          connection.close()
        })
      })
    }
  }
}

