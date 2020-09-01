package com.atguigu.wh.flume.interceptors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

/**
 * Created by VULCAN on 2020/8/12
 */
public class JSONUtils {

    public static boolean isJSONValidate(String log){
        try {
            JSON.parse(log);
            return true;
        }catch (JSONException e){
            return false;
        }
    }

}
