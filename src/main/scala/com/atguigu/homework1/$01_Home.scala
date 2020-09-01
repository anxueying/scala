package com.atguigu.homework1

import scala.io.Source
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.GetMethod

/**
  * @author Shelly An
  * @create 2020/7/27 9:30
  *        一个需求一个main方法
  */
object $01_Home {
  def main(args: Array[String]): Unit = {
    //1. 读取文件
    val source = Source.fromFile("D:\\IdeaProjects\\scala\\input\\test.json","utf-8").getLines().toList
    //2. 解析json，获取ip和需要的字段
    val data = source.map(jsonStr => {
      //解析自己的json数据
      val obj = JSON.parseObject(jsonStr)
      //ip
      val ip = obj.getString("ip")
      //adplatformproviderid
      val adplatformproviderid = obj.getLong("adplatformproviderid")
      //requestmode
      val requestmode = obj.getInteger("requestmode")
      //      "processnode",
      val processnode = obj.getInteger("processnode")
      //      "iseffective",
      val iseffective = obj.getInteger("iseffective")
      //      "isbilling",
      val isbilling = obj.getInteger("isbilling")
      //      "isbid",
      val isbid = obj.getInteger("isbid")
      //      "iswin",
      val iswin = obj.getInteger("iswin")
      //      "adorderid",
      val adorderid = obj.getLong("adorderid")
      //      "adcreativeid"
      val adcreativeid = obj.getLong("adcreativeid")
      //winprice
      val winprice = obj.getDouble("winprice")
      //adpayment
      val adpayment = obj.getDouble("adpayment")

      (ip, adplatformproviderid, requestmode, processnode, iseffective, isbilling, isbid, iswin, adorderid, adcreativeid,winprice,adpayment)
    })
    data
    //3. 过滤ip为空的数据
    val filterData = data.filter(json=>json._1!=null&&json._1=="")

    //4. 请求接口，获取省份，城市
    val locationData = filterData.map(json => {
      val ip = json._1
      val url = s"https://restapi.amap.com/v3/ip?ip=${ip}&key=ecbd06980be90fe4fb74b113e9b4d6a7"
      //创建客户端
      val client = new HttpClient()
      //创建method
      val method = new GetMethod(url)
      //发起请求
      val code = client.executeMethod(method)
      var province = ""
      var city = ""
      if (code == 200) {
        //解析结果
        val result = method.getResponseBodyAsString
        val resultObj = JSON.parseObject(result)
        //解析出高德api返回的json数据
        province = resultObj.getString("province")
        city = resultObj.getString("city")
      }
      //返回结果
      (province, city, json)
    })
    locationData

    //过滤省份、城市 为空的数据
    val filterLocationData = locationData.filter(x=>x._1!=""&&x._2!=""&&x._1!=null&&x._2!=null)

    //5. 分组 以DSP广告消费为例
    val groupbyLocaiton = filterLocationData.groupBy(x=>(x._1,x._2))
    //6. 统计
    val result = groupbyLocaiton.map( {
      case ((province:String,city:String),list) =>
      val v_province = province
      val v_city = city
      val org_num = list.filter({
        case ((_,_,(ip, adplatformproviderid, requestmode, processnode, iseffective, isbilling, isbid, iswin, adorderid, adcreativeid,winprice,adpayment))) =>
          adplatformproviderid >= 1000 && iseffective == 1 && isbilling == 1 && isbid == 1 && adorderid > 2000 && adcreativeid > 2000
      }
      ).map({
        case ((_,_,(ip, adplatformproviderid, requestmode, processnode, iseffective, isbilling, isbid, iswin, adorderid, adcreativeid,winprice,adpayment))) =>
          winprice/1000
      })
      //filter+size = count
      //winprice/1000 的和即为DSP广告消费
      (v_province, v_city, org_num.sum)
    })
    //7. 结果展示
    result.foreach(println)
  }
}
