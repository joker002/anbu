package com.example.lenovo.everydaynews.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 异步对象类
 */
public class NetAsync extends AsyncTask<Request, Object, Response> {
    /**
     * 加载框
     */
    ProgressDialog mDialog;
    /**
     * 自定义的接口
     */
    OnResultFinishListener mListener;

        public NetAsync(Context context, OnResultFinishListener mListener){
            mDialog=ProgressDialog.show(context,"","加载中。。。");

            this.mListener=mListener;
        }

    /**
     * 用来加载数据
     * @param params
     * @return
     */
        @Override
        protected Response doInBackground(Request... params) {
            //Log.e("aaa", "doInBackground: " );

            Request request=params[0];

            Response response=new Response();

            HttpURLConnection httpURLConnection=null;
            try {
                URL url=new URL(request.url);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT);
                httpURLConnection.setReadTimeout(Constants.READ_TIMEOUT);
                //Log.e("aaa", "doInBackground:111 "+request.type );
                if(request.type==Constants.GET){
                    httpURLConnection.setRequestMethod("GET");
                    //Log.e("aaa", "doInBackground: "+request.type );
                }else{
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream ou = httpURLConnection.getOutputStream();
                    ou.write(Utils.getUrl(request.params,Constants.POST).getBytes());
                }

                //Log.e("aaa", "doInBackground: 3333" );
                //Log.e("aaa", "doInBackground: +++++"+httpURLConnection.getResponseCode() );
                //结果码
                int code=httpURLConnection.getResponseCode();
                //int code=1;
                //Log.e("aaa", "doInBackground: 111111111" );
                response.code=code;
                //Log.e("aaa", "doInBackground: 22222222222" );


                if(code==HttpURLConnection.HTTP_OK){
                    InputStream in=httpURLConnection.getInputStream();
                    byte[] bytes=new byte[1024];
                    int len;
                    StringBuffer buffer=new StringBuffer();
                    while((len=in.read(bytes))!=-1){
                        buffer.append(new String(bytes,0,len));
                    }
                    //拿到结果
                    response.result=buffer.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(httpURLConnection!=null){
                    httpURLConnection.disconnect();
                }
            }

            return response;
        }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        //拿到结果
        mDialog.dismiss();
        Response response1=response;

        //Log.e("aaa", "onPostExecute: "+response1 );

        if(response.code!=HttpURLConnection.HTTP_OK){
           mListener.failed(response1);
           //Log.e("aaa", "onPostExecute: "+response1.code );
        }else{
            mListener.success(response1);
        }
    }
}
