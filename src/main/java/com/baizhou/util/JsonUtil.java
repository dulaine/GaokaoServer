package com.baizhou.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;

import java.io.IOException;

public class JsonUtil {

    //解析Json中的Key栏位的为Object
    public  static <T> T ParseKeyData(ByteBuf body, String key, Class<T> clazz){
        String jsonStr = body.toString(CharsetUtil.UTF_8);
        return JsonUtil.ParseKeyData(jsonStr, key, clazz);
    }
    public  static <T> T ParseKeyData(String jsonString, String key, Class<T> clazz){
        JSONObject jsonObj = JSONObject.parseObject(jsonString);//.toJavaObject(FinanceSubjectVO.class);
        T data =  jsonObj.getObject(key, clazz);
        return  data;
    }

//    public static FileConfig FileConfigParse(String jsonFile) {
//        try {
//            String json = FileUtils.readFile(jsonFile);
//            JSONObject object = JSON.parseObject(json);
//            return object.toJavaObject(FileConfig.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public static <T> T Parse(String jsonFile, Class<T> tClass) {
        try {
//            if (!FileUtils.Exist(jsonFile)) return null;
            String json = FileUtils.readFile(jsonFile);
            JSONObject object = JSON.parseObject(json);
            return object.toJavaObject(tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
