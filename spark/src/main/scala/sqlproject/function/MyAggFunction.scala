package sqlproject.function

import java.text.DecimalFormat

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types._

/**
  * @author Shelly An
  * @create 2020/8/20 11:22
  *         城市备注： 城市名+占比
  *         占比=当前城市点击量/地区点击量
  *         函数名 MyAgg(cityName)
  *
  *         SELECT
  *         area,product_name,count(*) clickcount, MyAgg(cityName)
  *         FROM t1
  *         GROUP BY  area,product_name
  *
  *         聚集函数运行的范围：每个组
  *
  *         输入：cityName  String
  *         缓冲区：两个值
  *         (String，Long)  每个城市的点击量
  *         Long  当前商品在此区域总点击量
  *         输出：城市备注  String
  */

// 2.0实现
class MyAggFunction extends UserDefinedAggregateFunction {

  //输入数据类型
  override def inputSchema: StructType = StructType(StructField("city", StringType) :: Nil)


  /*缓冲区类型
    * case class MapType(
    * keyType: DataType,
    * valueType: DataType,
    * valueContainsNull: Boolean) extends DataType {
    */
  override def bufferSchema: StructType = StructType(StructField("areaClickCount", LongType)
    :: StructField("cityClick", MapType(StringType, LongType))
    :: Nil)

  //返回数据类型
  override def dataType: DataType = StringType

  //是否为稳定函数
  override def deterministic: Boolean = true

  //初始化 buffer 可以看做是数组
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    //第一个元素 区域点击量
    buffer(0) = 0l
    //第二个元素  城市点击量
    buffer(1) = Map[String, Long]()
  }

  //分区内计算
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    //输入数据就是城市名，直接取
    val cityName = input.getString(0)

    //区域点击量 每进来一条数据，点击量加1
    buffer(0) = buffer.getLong(0) + 1l
    //取出buffer中的第二个元素map （cityName,clickCount)
    val map = buffer.getMap[String, Long](1)
    //取map中的城市点击量 ，如没有，复制为0（如果这是这个城市的第一条，那就是没有）
    val cityClickCount = map.getOrElse(cityName, 0l) + 1l

    //更新buffer中的map
    buffer(1) = map.updated(cityName, cityClickCount)

  }

  //分区间合并 buffer2 合并到 buffer1
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    val map1 = buffer1.getMap[String, Long](1)
    val map2 = buffer2.getMap[String, Long](1)
    //左向折叠
    /*
        def foldLeft[B](z: B)(op: (B, A) => B): B = {
          var result = z
          this foreach (x => result = op(result, x))
          result
        }
     */
    //合并相同城市（key）的点击量，并更新到buffer1的map中
    buffer1(1) = map2.foldLeft(map1) {
      case (map, (cityName, cityClickCount)) =>
        val newValue = map.getOrElse(cityName, 0l) + cityClickCount
        map.updated(cityName, newValue)
    }

    //计算总点击量
    buffer1(0) = buffer2.getLong(0) + buffer1.getLong(0)
  }

  //格式化器
  private val myFormat = new DecimalFormat(".00%")

  //返回值
  override def evaluate(buffer: Row): Any = {
    //取出map (城市，点击量)
    val map = buffer.getMap[String, Long](1)

    //取前2，超过2个城市用其他
    val sortedList = map.toList.sortBy(-_._2).take(2)

    //区域点击量
    val areaClickCount = buffer.getLong(0)

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
}
