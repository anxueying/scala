package sqlproject.function

import java.text.DecimalFormat

import org.apache.spark.sql.{Encoder, Encoders}
import org.apache.spark.sql.expressions.Aggregator


/**
  * @author Shelly An
  * @create 2020/8/10 9:28
  *
  *         SELECT
  *         area,product_name,count(*) clickcount, MyAgg(cityName)
  *         FROM t1
  *         GROUP BY  area,product_name
  *
  *       3.0推荐方式
  *
  *       输入：cityName  String
  *       缓冲区：两个值
  *       (String，Long)  每个城市的点击量
  *       Long  当前商品在此区域总点击量
  *       输出：城市备注  String
  *
  */
class MyAggFunction2 extends Aggregator[String,MyBuff,String]{

  //初始化缓冲区 zeroValue
  override def zero: MyBuff = MyBuff(0l,Map[String,Long]())

  //分区内聚合
  override def reduce(b: MyBuff, a: String): MyBuff = {
    //输入数据就是城市名，直接取
    val cityName = a

    //区域点击量 每进来一条数据，点击量加1
    b.sum = b.sum + 1l
    //取出buffer中的第二个元素map （cityName,clickCount)
    val map = b.map
    //取map中的城市点击量 ，如没有，复制为0（如果这是这个城市的第一条，那就是没有）
    val cityClickCount = map.getOrElse(cityName, 0l) + 1l

    //更新buffer中的map
    b.map = map.updated(cityName, cityClickCount)

    b
  }

  //分区间聚合
  override def merge(b1: MyBuff, b2: MyBuff): MyBuff = {
    val map1 = b1.map
    val map2 = b2.map
    //左向折叠
    /*
        def foldLeft[B](z: B)(op: (B, A) => B): B = {
          var result = z
          this foreach (x => result = op(result, x))
          result
        }
     */
    //合并相同城市（key）的点击量，并更新到buffer1的map中
    b1.map = map2.foldLeft(map1) {
      case (map, (cityName, cityClickCount)) =>
        val newValue = map.getOrElse(cityName, 0l) + cityClickCount
        map.updated(cityName, newValue)
    }

    //计算总点击量
    b1.sum = b2.sum + b1.sum

    b1
  }

  //格式化器
  private val myFormat = new DecimalFormat(".00%")

  //返回结果
  override def finish(reduction: MyBuff): String = {
    //取出map (城市，点击量)
    val map = reduction.map

    //取前2，超过2个城市用其他
    val sortedList = map.toList.sortBy(-_._2).take(2)

    //区域点击量
    val areaClickCount = reduction.sum

    //计算其他
    val otherClickCount = areaClickCount - sortedList(0)._2 - sortedList(1)._2

    val result = sortedList:+("其他",otherClickCount)

    //计算占比
    val finalResult = result.map {
      case (cityName, cityClickCount) =>
        cityName + myFormat.format(cityClickCount.toDouble / areaClickCount)
    }

    //生成符合要求的字符串
    finalResult.mkString(",")
  }

  //buffer的Encoder类型的解码器  buffer是自定义类型MyBuff 因此 使用Encoders.product[T]
  override def bufferEncoder: Encoder[MyBuff] =Encoders.product[MyBuff]

  //结果的Encoder类型的解码器  输出结果是字符串，因此使用Encoders.STRING
  override def outputEncoder: Encoder[String] = Encoders.STRING
}

/**
  * 自定义Buffer类型
  * @param sum 区域点击量
  * @param map (城市名, 城市点击量)
  */
case class MyBuff(var sum:Long ,var map: Map[String,Long])