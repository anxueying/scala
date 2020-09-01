package com.atguigu.homework2

import scala.io.Source

/**
  * @author Shelly An
  * @create 2020/7/27 16:11
  *        获取每个省份农产品最多的三个农贸市场
  *         香菜	  2.80	2018/1/1	山西汾阳市晋阳农副产品批发市场	山西	汾阳
  */
object $03_MostMarketProvince {
  def main(args: Array[String]): Unit = {
    val product = Source.fromFile("D:\\IdeaProjects\\scala\\input\\product.txt","utf-8").getLines().toList

    val productProvince = product.map(x=>x.split("\t")).filter(x=>x.length>=6)
                                        .map(x=>(x(4),x(3),x(0))).distinct
    //山西 山西汾阳市晋阳农副产品批发市场
    val result = productProvince.groupBy(x=>(x._1,x._2)).map(x=>(x._1._1,x._1._2,x._2.length))
      .groupBy(x=>x._1).map(x=>x._2.toList.sortBy(x=>x._3).reverse.take(3))

    //取每个省最多的三个
    val top3 = result
    top3.foreach(println)

    /**
      * List((黑龙江,黑龙江鹤岗市万圃源蔬菜有限责任公司,45))
      * List((甘肃,甘肃酒泉春光农产品市场有限责任公司,36), (甘肃,甘肃省武山县洛门蔬菜批发市场,29), (甘肃,甘肃陇西县清吉洋芋批发市场,28))
      * List((四川,四川广安市邻水县农产品交易中心,51), (四川,四川绵阳市高水蔬菜批发市场,30), (四川,四川南充市桑园坝农产品批发市场,29))
      * List((上海,上海农产品中心批发市场有限公,39))
      * List((青海,西宁仁杰粮油批发市场有限公司,3))
      * List((浙江,浙江嘉兴蔬菜批发交易市场,51), (浙江,浙江义乌农贸城,40), (浙江,浙江嘉善浙北果蔬菜批发交易,35))
      * List((广西,广西柳州柳邕农副产品批发市场,53), (广西,广西南宁市五里亭蔬菜批发市场,47), (广西,广西田阳县果蔬菜批发市场,5))
      * List((重庆,重庆江北区盘溪农贸市场,33))
      * List((广东,广东汕头农副产品批发中心,65), (广东,广东广州江南农副产品市场,27))
      * List((天津,天津南开区红旗农贸批发市场,55), (天津,天津西青区当城无公害农副产品批发市场,51), (天津,天津金钟蔬菜批发市场,33))
      * List((吉林,长春蔬菜中心批发市场,47))
      * List((北京,北京八里桥农产品中心批发市场,112), (北京,北京昌平区水屯农副产品批发市场,105), (北京,北京朝阳区大洋路综合市场,103))
      * List((山西,太原市桥西农副产品批发市场,54), (山西,山西晋城绿欣农产品批发市场,54), (山西,山西大同振华蔬菜批发市场,49))
      * List((辽宁,辽宁阜新市瑞轩蔬菜农副产品综合批发市场,53), (辽宁,辽宁鞍山宁远农产品批发市场,46), (辽宁,大连水产品交易市场有限公司,42))
      * List((内蒙古,呼和浩特市东瓦窑批发市场,85), (内蒙古,内蒙包头市场友谊蔬菜批发市场,55), (内蒙古,内蒙呼市食全食美股份有限公司石羊桥交易中,27))
      * List((安徽,安徽合肥周谷堆农产品批发市场,76), (安徽,安徽马鞍山安民农副产品批发交易市场,62), (安徽,安徽砀山农产品中心惠丰批发市场,52))
      * List((江苏,江苏凌家塘农副产品批发市场,146), (江苏,江苏苏州南环桥农副产品批发市,82), (江苏,江苏省南京市惠民桥农副产品市场,37))
      * List((新疆,新疆乌鲁木齐北园春批发市场,99), (新疆,新疆克拉玛依乌尔禾兵团农七师一三七团绿衡蔬菜瓜果批发市场,43))
      * List((河南,河南郑州农产品物流配送中心,114), (河南,河南安阳豫北蔬菜批发市场,37))
      * List((福建,福建福鼎闽浙边界农贸中心市场,91), (福建,福州市闽江蔬菜贸易中心,22))
      * List((湖南,长沙市马王堆批发市场,56), (湖南,湖南常德甘露寺蔬菜批发市场,27))
      * List((河北,石家庄桥西批发市场,75), (河北,河北邯郸（魏县）天仙果品农贸批发交易市场,73), (河北,河北秦皇岛海阳农副产品批发,45))
      * List((湖北,湖北武汉白沙洲农副产品大市场,68), (湖北,武汉市皇经堂批发市场,46), (湖北,湖北省黄石市农副产品批发交易公司,39))
      * List((江西,江西省永丰县农产品批发中心市场,23))
      * List((陕西,陕西咸阳市新阳光农副产品批发市场,51), (陕西,陕西泾阳县云阳蔬菜批发市场,25), (陕西,陕西西部欣桥农产品物流中心,25))
      * List((山东,山东章丘批发市场,44), (山东,山东济南市堤口路果品批发市场,43), (山东,山东青岛城阳蔬菜批发市场,39))
      * List((宁夏,宁夏吴忠市利通区东郊果蔬批发市场,53))
      */
  }

}