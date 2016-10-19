package com.example.lenovo.everydaynews.net;

import java.util.Map;
import java.util.Set;

/**
 * 拼接工具类
 * Created by lenovo on 2016/9/22.
 */
public class Utils {
    /**
     *拼接方法
     * @param params
     * @param type
     * @return
     */
    public  static String getUrl(Map<String,String> params,int type){
        StringBuffer buffer=new StringBuffer();
        if(params!=null&&params.size()!=0){
            if(type==Constants.GET){
                buffer.append("?");
            }

            Set<String> keySet=params.keySet();
            for (String key:keySet) {
                buffer.append(key)
                        .append("=")
                        //通过键拿值
                        .append(params.get(key))
                        .append("&");
            }
            //删除指定位置的字符(删除最后一个&)
            buffer.deleteCharAt(buffer.length()-1);
        }
        return buffer.toString();
    }
}
