package com.example.lenovo.everydaynews.util;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.everydaynews.net.Constants;
import com.example.lenovo.everydaynews.net.Utils;

import java.util.Map;

/**
 * 网络请求工具类
 * Created by lenovo on 2016/9/27.
 */
public class MyVolley {
    /**
     * GET请求
     * @param context
     * @param url
     * @param params
     * @param onVolleyResult
     */
    public static void get(Context context, String url, Map<String,String>  params, final OnVolleyResult onVolleyResult){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, url+ Utils.getUrl(params, Constants.GET), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                onVolleyResult.get(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onVolleyResult.post(volleyError);
            }
        });
        queue.add(request);
    }

    /**
     * POST请求
     * @param context
     * @param url
     * @param params
     * @param onVolleyResult
     */
    public static void post(Context context, String url, final Map<String,String> params, final OnVolleyResult onVolleyResult){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                 onVolleyResult.get(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onVolleyResult.post(volleyError);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        queue.add(request);
    }
    public  interface  OnVolleyResult{
        void get(String s);
        void post(VolleyError volleyError);
    }
}
