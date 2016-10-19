package com.example.lenovo.everydaynews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.net.MyHttp;
import com.example.lenovo.everydaynews.net.OnResultFinishListener;
import com.example.lenovo.everydaynews.net.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆界面
 * Created by lenovo on 2016/10/8.
 */
public class DisembarkActivity extends BaseActivity implements View.OnClickListener {
    String name;
    String password;
    TextInputLayout mTextInputName;
    TextInputLayout mTextInoutPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViews(R.layout.activity_disembark);
    }

    @Override
    public void initView() {
        //Log.e("aaa", "initView: 1111111111111");
        mTextInputName = (TextInputLayout) findViewById(R.id.text_layout_name);
        mTextInoutPassword = (TextInputLayout) findViewById(R.id.text_layout_password);

        Button btnRegister = (Button) findViewById(R.id.btn_dis_reg);
        Button btnDisembark = (Button) findViewById(R.id.btn_dis_disembark);

        btnRegister.setOnClickListener(this);
        btnDisembark.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dis_disembark://登陆
                if (isRight()) {
                    name = mTextInputName.getEditText().getText().toString();
                    password = mTextInoutPassword.getEditText().getText().toString();

                    Map<String, String> params = new HashMap<>();
                    params.put("ver", "0000000");
                    params.put("uid", name);
                    params.put("pwd", password);
                    params.put("device", "0");
                    //http://118.244.212.82:9092/newsClient/user_login?ver=0000000&uid="ggggggg"&pwd="gggggg"&device=1
                    //Log.e("aaa", "onClick: " + params);
                    MyHttp.get(this, "http://118.244.212.82:9092/newsClient/user_login", params, new OnResultFinishListener() {

                        @Override
                        public void success(Response response) {
                            Toast.makeText(DisembarkActivity.this, "登陆成功", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void failed(Response response) {
                            Toast.makeText(DisembarkActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
            case R.id.btn_dis_reg://跳转注册
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }

    /**
     * 正则判断
     *
     * @return
     */
    public boolean isRight() {

        return true;
    }

}
