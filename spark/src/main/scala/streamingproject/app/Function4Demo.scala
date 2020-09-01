package streamingproject.app

import java.sql.{Connection, PreparedStatement, ResultSet}

import org.apache.spark.streaming.dstream.DStream
import streamingproject.bean.AdsInfo
import streamingproject.utils.JDBCUtil

import scala.collection.mutable.ListBuffer

/**
  * @author Shelly An
  * @create 2020/8/22 10:39
  *
  */
object Function4Demo extends BaseApp {
  def main(args: Array[String]): Unit = {

    runApp {

      val ds: DStream[AdsInfo] = getAllBeans(getDStream())

      val ds2: DStream[((String, String, String), Int)] = ds.map(bean => ((bean.dayString, bean.userId, bean.adsId), 1))
        .reduceByKey(_ + _)

      ds2.foreachRDD(rdd =>{

        rdd.foreachPartition(iter => {
          //再写入到  user_ad_count
          val connection: Connection = JDBCUtil.getConnection()
          val sql=
            """
              |insert into user_ad_count values(?,?,?,?)
              |ON DUPLICATE KEY UPDATE COUNT= count + ?
              |
              |
              |""".stripMargin

          val ps: PreparedStatement = connection.prepareStatement(sql)

          iter.foreach{
            case ((day, userId, adsId), count) => {

              ps.setString(1,day)
              ps.setString(2,userId)
              ps.setString(3,adsId)
              ps.setInt(4,count)
              ps.setInt(5,count)

              ps.executeUpdate()
            }
          }

          ps.close()

          //查询当前哪些用户超过100
          val sql2=
            """
              |select userid from user_ad_count
              |where count >35
              |
              |""".stripMargin

          val ps2: PreparedStatement = connection.prepareStatement(sql2)

          val rs: ResultSet = ps2.executeQuery()

          // 当前的黑名单
          val blackList: ListBuffer[String] = ListBuffer[String]()

          while(rs.next()){
            blackList.append(rs.getString("userid"))
          }

          rs.close()
          ps2.close()

          val sql3=
            """
              |insert into  black_list values(?)
              |ON DUPLICATE KEY UPDATE userid = ?
              |
              |""".stripMargin


          val ps3: PreparedStatement = connection.prepareStatement(sql3)

          for (elem <- blackList) {
            ps3.setString(1,elem)
            ps3.setString(2,elem)
            ps3.executeUpdate()
          }

          ps3.close()

          connection.close()

        })

      })

    }

  }


}
