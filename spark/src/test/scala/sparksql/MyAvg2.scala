package sparksql

import org.apache.spark.sql.{Encoder, Encoders}
import org.apache.spark.sql.expressions.Aggregator

/**
  * @author Shelly An
  * @create 2020/8/17 0:07
  */

/**
  * DSL中使用UDAF 对比SQL中使用 Aggregator[Double,MyBuf,Double] 第一个是emp.salary
  * Aggregator[Emp,MyBuf,Double] 第一个直接是emp
  */
class MyAvg2 extends Aggregator[Emp,MyBuf,Double]{
  override def zero: MyBuf = MyBuf(0.0,0)

  override def reduce(b: MyBuf, a: Emp): MyBuf = {
    b.sum += a.salary
    b.count += 1
    b
  }

  override def merge(b1: MyBuf, b2: MyBuf): MyBuf = {
    b1.sum += b2.sum
    b1.count += b2.count
    b1
  }

  override def finish(reduction: MyBuf): Double = reduction.sum/reduction.count

  override def bufferEncoder: Encoder[MyBuf] = Encoders.product[MyBuf]

  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}

case class Emp(name:String,salary:Double)