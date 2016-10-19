package com.example.lenovo.everydaynews.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.adapter.CommentAdapter;
import com.example.lenovo.everydaynews.entity.Comment;
import com.example.lenovo.everydaynews.net.MyHttp;
import com.example.lenovo.everydaynews.net.OnResultFinishListener;
import com.example.lenovo.everydaynews.net.Response;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 评论界面
 * Created by cc on 2016/10/13.
 */
public class CommentActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 评论界面适配器
     */
    CommentAdapter mAdapter;
    /**
     * 评论框
     */
    EditText mEditText;
    /**
     * 评论按钮
     */
    Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViews(R.layout.activity_comment);
    }

    @Override
    public void initView() {

        mIvLift.setImageResource(R.mipmap.back);
        mTvTitle.setText("跟帖");
        mIvRightTwo.setVisibility(View.GONE);

        ListView mList = (ListView) findViewById(R.id.lv_comment);
        mEditText = (EditText) findViewById(R.id.et_comment);
        mButton = (Button) findViewById(R.id.btn_comment);

        mAdapter = new CommentAdapter(this, null, R.layout.item_comment);
        //启动适配器
        mList.setAdapter(mAdapter);

        Map<String, String> params = new HashMap();
        params.put("ver", "0000000");
        //新闻ID
        params.put("nid", "1");
        params.put("type", "1");

        //评论时间
        params.put("stamp", "20140321");
        //评论ID
        params.put("cid", "20");
        params.put("dir", "0");
        //最大更新条目数
        params.put("cnt", "20");

        //http://118.244.212.82:9092/newsClient/news_list/cmt_list?ver=0000000&nid=1&type=1&stamp=20140321&cid=20&dir=0&cnt=20
        MyHttp.get(this, "http://118.244.212.82:9092/newsClient/cmt_list", params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {
                Gson gson = new Gson();
                Return aa = gson.fromJson(response.result.toString(), Return.class);

                mAdapter.mList.addAll(aa.data);
                mAdapter.notifyDataSetChanged();

                Toast.makeText(CommentActivity.this, "成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failed(Response response) {
                Toast.makeText(CommentActivity.this, "失败", Toast.LENGTH_LONG).show();
            }
        });
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String str=mEditText.getText().toString();


        Map<String, String> params = new HashMap<>();
        params.put("ver","0000000");
        //当前新闻ID
        params.put("nid","20");
        //用户令牌 dc1e619835848e06ca49e009f25d560e
        params.put("token","dc1e619835848e06ca49e009f25d560e");
        //手机标识符
        params.put("imei","866818023239831");
        //评论内容
        params.put("ctx",""+str);
        Log.e("aaa", "onClick: "+str );

        MyHttp.get(this, "http://118.244.212.82:9092/newsClient/cmt_commit", params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {
                Log.e("aaa", "success: "+response.result );
                Toast.makeText(CommentActivity.this,"成功",Toast.LENGTH_LONG).show();
            }

            @Override
            public void failed(Response response) {
                Toast.makeText(CommentActivity.this,"失败",Toast.LENGTH_LONG).show();
            }
        });
    }
}

class Return {
    public String message;
    public String status;
    public ArrayList<Comment> data;
}

