package sparksql

import org.apache.spark.sql.Row
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.types.{DataType, DoubleType, StructField, StructType}

/**
  * @author Shelly An
  * @create 2020/8/14 21:11
  *        提供MySum的实现 mySum(salary)
  *        聚集函数的实现非常类似累加器 或 aggregateByKey
  */
class MySum extends UserDefinedAggregateFunction{
  //Schema 元数据  inputSchema 输入数据的元数据
  //声明聚集函数传入的字段类型 本例：salary的类型
  /*::  从右向左开始元素，依次把每个元素追加到数组中
   * val struct =
   *   StructType(
   *     StructField("a", IntegerType, true) ::
   *     StructField("b", LongType, false) ::
   *     StructField("c", BooleanType, false) :: Nil)
   */
  override def inputSchema: StructType = StructType(StructField("sumData",DoubleType)::Nil)

  //缓冲区的类型 buffer(缓冲区)可以理解为累加器的zeroValue，用来保存临时计算的结果
  override def bufferSchema: StructType = StructType(StructField("sumData",DoubleType)::Nil)

  //最终返回的结果的类型
  override def dataType: DataType = DoubleType

  // 函数是否是确定性的，指传入相同的输入是否返回相同的输出
  override def deterministic: Boolean = true

  //初始化buffer
  // MutableAggregationBuffer当做数组
  // abstract class MutableAggregationBuffer extends Row
  //Row中  def apply(i: Int): Any = get(i)
  override def initialize(buffer: MutableAggregationBuffer): Unit = buffer(0)=0.0

  //分区内计算  input：输入的数据 buffer：缓冲区
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    buffer(0) = input.getDouble(0) + buffer.getDouble(0)
  }

  //分区间合并
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    buffer1(0) = buffer2.getDouble(0) + buffer1.getDouble(0)
  }

  override def evaluate(buffer: Row): Any = buffer(0)
}
