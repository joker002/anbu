package com.example.lenovo.everydaynews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lenovo.everydaynews.adapter.ChooseAdapter;

import java.util.ArrayList;

/**
 * 选择人员
 * Created by lenovo on 2016/10/10.
 */
public class ChooseActivity extends Activity implements ChooseAdapter.OnItemClickListener {
    RecyclerView mRecyclerView;
    public static boolean[] chooses;

    ChooseAdapter adapter;
    ArrayList<String> mList;
    ArrayList<String> name=new ArrayList<>();
    Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mLlChoose = new LinearLayout(this);
        mLlChoose.setOrientation(LinearLayout.VERTICAL);

        mRecyclerView = new RecyclerView(this);
        //设置RecyclerView的宽和高
        mRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));

       mBtn = new Button(this);
        mBtn.setText("确认");
        mBtn.setTextSize(20);

        mLlChoose.addView(mRecyclerView);
        mLlChoose.addView(mBtn);

        setContentView(mLlChoose);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        //数据源
        mList = new ArrayList<>();
        mList.add("张一");
        mList.add("张二");
        mList.add("张三");
        mList.add("张四");
        mList.add("张五");
        mList.add("张六");
        mList.add("张七");
        mList.add("张八");
        mList.add("张九");

        chooses = new boolean[mList.size()];

        //布局管理器 设置LayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //加载适配器
        adapter = new ChooseAdapter(mList, this);
        mRecyclerView.setAdapter(adapter);

        adapter.setmOnItemClickListener(this);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i <chooses.length ; i++) {
                    //Log.e("aaa", "onClick: "+chooses[i] );
                    if(chooses[i]){
                        name.add(mList.get(i));
                    }
                   // Log.e("aaa", "onClick: "+name );
                }

                Intent intent=new Intent();
                intent.putExtra("data",name);
                //返回结果
                if(name!=null&&name.size()!=0){
                    setResult(RESULT_OK,intent);
                }
                finish();
            }
        });

    }

    @Override
    public void onItemClick(ChooseAdapter.MyHolder holder, int position) {

        //Log.e("aaa", "onItemClick: "+6 );
        chooses[position] = !chooses[position];
        adapter.notifyItemChanged(position);


    }



}
