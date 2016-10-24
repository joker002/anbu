package com.example.lenovo.everydaynews.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.net.MyHttp;
import com.example.lenovo.everydaynews.net.OnResultFinishListener;
import com.example.lenovo.everydaynews.net.Response;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 注册界面
 * Created by lenovo on 2016/10/8.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    CheckBox cb;
    EditText emile;
    EditText name;
    EditText password;
    String uid;
    String email;
    String pwd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViews(R.layout.activity_register);
    }

    @Override
    public void initView() {


        mIvLift.setImageResource(R.mipmap.ic_title_home_default);
        mTvTitle.setText("用户注册");
        mIvRightTwo.setImageResource(R.mipmap.ic_title_share_default);

        emile = (EditText) findViewById(R.id.et_reg_emile);
        name = (EditText) findViewById(R.id.et_reg_name);
        password = (EditText) findViewById(R.id.et_reg_password);
        cb = (CheckBox) findViewById(R.id.cb_reg);
        Button btn = (Button) findViewById(R.id.btn_reg);

        uid = name.getText().toString();
        email = emile.getText().toString();
        pwd = password.getText().toString();
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        //Log.e("aaa", "onClick: " + uid + "---" + email + "----" + pwd);

        if (isRight() && cb.isChecked()) {
            Map<String, String> params = new HashMap<>();
            params.put("ver", "0000000");
            params.put("uid", uid);
            params.put("email", email);
            params.put("pwd", pwd);

            MyHttp.get(this, "http://118.244.212.82:90/newsClien92t/user_register", params, new OnResultFinishListener() {

                @Override
                public void success(Response response) {
                    //Log.e("aaa", "success: "+response.result);

                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();

                    //到底是数组还是对象
                    Gson gson=new Gson();
                    RegisterReturn registerReturn=gson.fromJson(response.result.toString(),RegisterReturn.class);

                    //Log.e("aaa", "success: "+a );
                    finish();
                }

                @Override
                public void failed(Response response) {
                    Log.e("aaa", "failed: ");
                }
            });
        }
    }

    /**
     * 判断格式是否正确(正则表达式)
     */
    public boolean isRight() {

        // 要验证的字符串
        //String str = "service@xsoftlab.net";
        // 邮箱验证规则
        String regEx = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(uid);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        //System.out.println(rs);

        if(rs){
            return true;
        }

        return true;
    }
}

class RegisterReturn{
    String message;
    String status;
    //ArrayList<B> data;
    User mUser;

    @Override
    public String toString() {
        return "A{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + mUser +
                '}';
    }
}

class User{
    int result;
    String token;
    String explain;

    @Override
    public String toString() {
        return "B{" +
                "result=" + result +
                ", token='" + token + '\'' +
                ", explain='" + explain + '\'' +
                '}';
    }
}