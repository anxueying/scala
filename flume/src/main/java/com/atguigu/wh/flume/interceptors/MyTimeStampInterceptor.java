package com.atguigu.wh.flume.interceptors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by VULCAN on 2020/8/12
 */
public class MyTimeStampInterceptor implements Interceptor {
    private List<Event> results=new ArrayList<>();
    @Override
    public void initialize() {

    }

    // event 是 f2的KafkaSource封装
    @Override
    public Event intercept(Event event) {

        byte[] body = event.getBody();
        String log = new String(body, StandardCharsets.UTF_8);

        Map<String, String> headers = event.getHeaders();

        //将字符串转为JSONObject
        JSONObject jsonObject = JSON.parseObject(log);

        if (jsonObject.containsKey("ts")) {

            String ts = jsonObject.getString("ts");
            headers.put("timestamp",ts);
            return event;
        } else {
            // 没有时间戳的数据没有分析价值
            return null;
        }

    }

    @Override
    public List<Event> intercept(List<Event> list) {
        //先清空results
        results.clear();

        for (Event event : list) {
            Event e = intercept(event);
            //判断拦截的数据是否合法
            if (e !=null){
                //将合法的数据放入到集合中
                results.add(e);
            }
        }
        return results;
    }

    @Override
    public void close() {

    }
    public static class Builder implements Interceptor.Builder {

        // 返回一个拦截器对象
        @Override
        public Interceptor build() {
            return new MyTimeStampInterceptor();
        }

        //读取agent配置文件中的参数
        @Override
        public void configure(Context context) {

        }
    }
}
