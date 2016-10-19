package com.example.lenovo.everydaynews.net;

import android.content.Context;
import android.util.Log;

import java.util.Map;

/**
 * 网络请求类
 * Created by lenovo on 2016/9/22.
 */
public class MyHttp {
    /**
     * 请求网络方式(get)
     * @param context
     * @param url
     * @param prams
     * @param mListener
     */
    public static void get(Context context, String url, Map<String,String> prams,
                           OnResultFinishListener mListener){
        //Log.e("aaa", "get: +++++++" );

        Request request=new Request();

        request.params=prams;
        request.type=Constants.GET;
        request.url=url+Utils.getUrl(prams,Constants.GET);
        /**
         * 异步对象类的对象
         */
        NetAsync async=new NetAsync(context,mListener);

        async.execute(request);
    }

    /**
     * 请求网络方式(post)
     * @param context
     * @param url
     * @param prams
     * @param mListener
     */
    public static void post(Context context, String url, Map<String,String> prams,
                        OnResultFinishListener mListener){
    Request request=new Request();
    request.params=prams;
    //网络请求方式
    request.type=Constants.POST;
    request.url=url;

    NetAsync async=new NetAsync(context,mListener);

    async.execute(request);

}
}
