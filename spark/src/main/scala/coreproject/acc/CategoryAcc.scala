package coreproject.acc

import coreproject.bean.AccBean
import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

/**
  * @author Shelly An
  * @create 2020/8/5 16:22
  *
  *        IN  (categoryId,标识符)  标识符：click order pay
  *
  *        OUT Map[(categoryId,(categoryId,cc,oc,pc)]  封装成一个Bean
  */
class CategoryAcc extends AccumulatorV2[(String,String),mutable.Map[String,AccBean]]{
  private var resultMap = mutable.Map[String,AccBean]()

  override def isZero: Boolean = resultMap.isEmpty

  override def copy(): AccumulatorV2[(String, String), mutable.Map[String, AccBean]] = new CategoryAcc

  override def reset(): Unit =resultMap.clear()

  /**
    * 累加
    * @param v (id,标识符)
    */
  override def add(v: (String, String)): Unit = {
    val toMergeBean = resultMap.getOrElse(v._1,AccBean(v._1,0,0,0))
    //判断标识符是啥然后就加那个
    if (v._2.equals("order")) {
      toMergeBean.oc+=1
    }else if(v._2.equals("click")){
      toMergeBean.cc+=1
    }else{
      toMergeBean.pc+=1
    }
    //把已经加好的bean放进去 更新
    resultMap.put(v._1,toMergeBean)
  }

  override def merge(other: AccumulatorV2[(String, String), mutable.Map[String, AccBean]]): Unit = {
    val beanList = other.value.values.toList
    for (elem <- beanList) {
      val bean = resultMap.getOrElse(elem.categoryId,AccBean(elem.categoryId,0,0,0))
      bean.cc += elem.cc
      bean.oc += elem.oc
      bean.pc += elem.pc
      resultMap.put(elem.categoryId,bean)
    }
  }

  override def value: mutable.Map[String, AccBean] = resultMap
}
