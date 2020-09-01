package sparksql

import org.apache.spark.sql.{Encoder, Encoders}
import org.apache.spark.sql.expressions.Aggregator

/**
  * @author Shelly An
  * @create 2020/8/15 8:50
  *         abstract class Aggregator[-IN, BUF, OUT]
  *         IN:输入的类型 -是泛型的协变异变  Double
  *         BUF: 缓冲区类型   记录两个值，总和/个数  可以用tuple，也可自定义一个缓冲区类型
  *         OUT: 输出的类型   Double
  *         模拟avg select myAvg(salary) from emp
  */
class MyAvg extends Aggregator[Double,MyBuf,Double] {
  //初始化缓冲区，初始化zeroValue  样例类不用new，直接有apply方法
  override def zero: MyBuf = MyBuf(0.0,0)

  //分区内运算 a 输入  b 缓冲区  将输入的每个值reduce到缓冲区
  override def reduce(b: MyBuf, a: Double): MyBuf = {
    b.sum += a
    b.count += 1
    b
  }

  //分区间合并 将b2中的值合并到b1
  override def merge(b1: MyBuf, b2: MyBuf): MyBuf = {
    b1.sum += b2.sum
    b1.count += b2.count
    b1
  }

  //返回结果
  override def finish(reduction: MyBuf): Double = reduction.sum/reduction.count

  /**
    * Encoder 特质 将jvm泛型对象T 转换成spark sql中相应的对象
    * 将JVM中的对象，转为DF或DS时，需要使用一个Encoder类型的解码器。spark提供了一个隐式的解码器
    *
    * Encoder 需要传入的是JVM中对象的类型
    *
    * 例如： 如下需要String类型的Encoder
    * List<String> data = Arrays.asList("abc", "abc", "xyz");
    * Dataset<String> ds = context.createDataset(data, Encoders.STRING());
    *
    * 通常情况下，不需要传入。spark都会隐式的传入。
    */
  //缓冲区的Encoder类型
  override def bufferEncoder: Encoder[MyBuf] = Encoders.product[MyBuf]

  //输出的Encoder类型
  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}

case class MyBuf(var sum: Double, var count: Int)


