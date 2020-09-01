package com.atguigu.charpter5;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.*;


/**
 * @author Mrs.An Xueying
 * 2020/7/13 2:15
 */
public class FindIPLocation {

    public static PMTJsonRootBean getIP(String json) throws IOException {

        String aMap = "https://restapi.amap.com/v3/ip?ip=";
        String myKey = "&key=ecbd06980be90fe4fb74b113e9b4d6a7";

        String string = json;
        PMTJsonRootBean rootBean = null;
            try {
                rootBean = JSON.parseObject(string, PMTJsonRootBean.class);
            } catch (Exception e) {
                //解析报错，说明不是json数据
                e.printStackTrace();
                return null;
            }
            if(rootBean==null){
                return null;
            }
            String ip = rootBean.getIp();
            if (ip == null && ip.equals("")) {
                return null;
            }

            String result = FindIPLocation.get(aMap + ip + myKey);
            JSONObject obj = JSON.parseObject(result);

            String province = obj.getString("province");
            //String city = obj.getString("city");

            //System.out.println("ip="+ip+",province="+province);
            if(province.equals("")){
                return null;
            }

            rootBean.setProvince(province);

            return rootBean;
    }

    public static String get(String url) throws IOException {
        //1. 创建http客户端
        HttpClient client = new HttpClient();
        //2. 创建Method
        GetMethod method = new GetMethod(url);

        //3. 发起请求
        int code = client.executeMethod(method);
        //4. 判断请求是否成功
        if (code == 200) {
            //String result = method.getResponseBodyAsString();

            InputStream inputStream = method.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String str= "";
            while((str = br.readLine()) != null){
                stringBuffer .append(str);
            }
            String result = stringBuffer.toString();
            //5. 打印结果

            return result;
        }
        return null;
    }
//
//    public static void main (String[]args) throws IOException {
//        FindIPLocation.getIP("D:\\IdeaProjects\\atguiguHadoop\\myhadoop\\pmt.json");
//        //FindIPLocation.get("https://restapi.amap.com/v3/ip?ip=114.247.50.2&output=xml&key=ecbd06980be90fe4fb74b113e9b4d6a7");
//    }
}