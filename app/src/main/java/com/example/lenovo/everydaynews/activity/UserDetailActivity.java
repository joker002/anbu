package com.example.lenovo.everydaynews.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.everydaynews.Manifest;
import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.net.MyHttp;
import com.example.lenovo.everydaynews.net.OnResultFinishListener;
import com.example.lenovo.everydaynews.net.Response;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * 个人中心
 * Created by lenovo on 2016/10/10.
 */
public class UserDetailActivity extends BaseActivity implements View.OnClickListener, Runnable {
    private ProgressDialog mLoadingDialog = null;
    View mView;
    ImageView mIVHead;
    PopupWindow mPopupWindow;
    TextView mTvName;
    SharedPreferences preference;
    File file = new File(DIR_PATH);
    private static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";
    /**
     * 选择人员跳转请求码
     */
    public static final int CHOOSE_PEO = 200;

    /**
     * 图库请求码
     */
    public static final int GOTO_PICK = 201;
    /**
     * 相机跳转请求码
     */
    public static final int GOTO_CAMARE = 202;

    /**
     * 相机权限请求码
     */
    public static final int PERMISSION_CAMERA = 203;
    /**
     * 图片存储文件夹
     */
    public static final String DIR_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "EverydayNews";
    /**
     * 图片存储位置
     */
    public static final String PHOTO_FILE_PATH = DIR_PATH + File.separator + "photo.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViews(R.layout.activity_detail);
    }

    @Override
    public void initView() {
        mIVHead = (ImageView) findViewById(R.id.iv_detail);

        mView = getLayoutInflater().inflate(R.layout.view_pop, null);

        mTvName = (TextView) mView.findViewById(R.id.tv_view_pop_name);

        LinearLayout mLlCamera = (LinearLayout) mView.findViewById(R.id.ll_view_pop_camera);
        LinearLayout mLlPicture = (LinearLayout) mView.findViewById(R.id.ll_view_pop_picture);
        LinearLayout mLlSet = (LinearLayout) mView.findViewById(R.id.ll_view_pop_set);
        TextView mTvPay = (TextView) mView.findViewById(R.id.tv_view_pop_pay);

        mLlSet.setOnClickListener(this);
        mLlCamera.setOnClickListener(this);
        mLlPicture.setOnClickListener(this);
        mTvPay.setOnClickListener(this);
        /**
         * view
         * PopupWindow的宽
         * PopupWindow的高
         */
        mPopupWindow = new PopupWindow(mView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //设置
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        mIVHead.setOnClickListener(this);

        //Log.e("aaa", "initView: " + a());
        if (a()) {
            Bitmap bitmap = BitmapFactory.decodeFile(PHOTO_FILE_PATH);
            mIVHead.setImageBitmap(bitmap);
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_detail:
                //mPopupWindow.showAsDropDown(mIVHead,100,100);
                mPopupWindow.showAtLocation(mView, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ll_view_pop_set:
                Intent intent1 = new Intent(this, ChooseActivity.class);
                //startActivity(intent1);
                //第二个参数是请求码
                startActivityForResult(intent1, CHOOSE_PEO);

                break;
            case R.id.ll_view_pop_camera:
                //手动给权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //检查手机是否给权限
                    if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        GotoCamera();
                    } else {
                        requestPermissions(new String[]{android.Manifest.permission.CAMERA}, PERMISSION_CAMERA);
                    }
                } else {
                    GotoCamera();
                }
                break;

            case R.id.ll_view_pop_picture:
                //,MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                //需要同时指定URI
                intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                if (!file.exists()) {
                    file.mkdirs();
                }

                startActivityForResult(intent2, GOTO_PICK);
                break;
            case R.id.tv_view_pop_pay:
                // “00” – 银联正式环境
                // “01” – 银联测试环境，该环境中不发生真实交易
//                MyHttp.get(this, "http://101.231.204.84:8091/sim/getacptn", null, new OnResultFinishListener() {
//                    @Override
//                    public void success(Response response) {
//                        Log.e("aaa", "success:11111 " + response);
//                    }
//
//                    @Override
//                    public void failed(Response response) {
//                        Log.e("aaa", "failed: ");
//                        Toast.makeText(UserDetailActivity.this, "获取流水号失败", Toast.LENGTH_LONG).show();
//                    }
//                });
                mLoadingDialog = ProgressDialog.show(this, // context
                        "", // title
                        "正在努力的获取tn中,请稍候...", // message
                        true);
                Log.e("aaa", "onClick: " );
                new Thread(UserDetailActivity.this).start();
                break;
        }
    }

    Intent intent2;

    /**
     * Activity之间的传值
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("aaa", "onActivityResult: " );
        //Log.e("aaa", "onActivityResult: "+requestCode );
        switch (requestCode) {
            case CHOOSE_PEO:
                //Log.e("aaa", "onActivityResult:aaaaaa " );
                //Log.e("aaa", "onActivityResult: "+resultCode );

                if (resultCode == RESULT_OK) {
                    ArrayList<String> name = data.getStringArrayListExtra("data");
                    //Log.e("aaa", "onActivityResult: "+name );
                    StringBuffer nameBuffer = new StringBuffer();
                    for (String list : name) {
                        nameBuffer.append(list).append(";");
                        //Log.e("aaa", "onActivityResult: "+nameBuffer );
                    }
                    nameBuffer.deleteCharAt(nameBuffer.length() - 1);
                    //Log.e("aaa", "onActivityResult: "+mTvName+"----"+nameBuffer );
                    mTvName.setText(nameBuffer);
                }
                break;
            case GOTO_CAMARE:
                //Log.e("aaa", "onActivityResult: " );
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = BitmapFactory.decodeFile(PHOTO_FILE_PATH);
                    //Log.e("aaa", "onActivityResult: "+bitmap );
                    mIVHead.setImageBitmap(bitmap);
                    //Log.e("aaa", "onActivityResult: "+a );
                }
                break;
            case GOTO_PICK:

                if (resultCode == RESULT_OK) {
                    //路径
                    Uri uri = data.getData();
                    //指定一个列
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    //filePathColumn 必须是数组
                    Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                    //游标游动到第一个
                    cursor.moveToFirst();
                    //下标
                    int columIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //手机图片的路径
                    String path = cursor.getString(columIndex);

                    //intent2.putExtra(MediaStore.Images.Media.DATA, Uri.fromFile(new File(PHOTO_FILE_PATH)));

                    try {
                        FileInputStream fi = new FileInputStream(path);
                        BufferedInputStream in = new BufferedInputStream(fi);

                        FileOutputStream fo = new FileOutputStream(PHOTO_FILE_PATH);
                        BufferedOutputStream out = new BufferedOutputStream(fo);
                        byte[] buf = new byte[4096];
                        int len = in.read(buf);
                        while (len != -1) {
                            out.write(buf, 0, len);
                            len = in.read(buf);
                        }
                        out.close();
                        fo.close();
                        in.close();
                        fi.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap1 = BitmapFactory.decodeFile(PHOTO_FILE_PATH);
                    //Log.e("aaa", "onActivityResult: " + bitmap1);
                    mIVHead.setImageBitmap(bitmap1);
                }
                break;

            case 10:
                if (data == null) {
                    return;
                }
                String msg = "";
                /*
                 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
                 */
                String str = data.getExtras().getString("pay_result");
                if (str.equalsIgnoreCase("success")) {
                    // 支付成功后，extra中如果存在result_data，取出校验
                    // result_data结构见c）result_data参数说明
                    if (data.hasExtra("result_data")) {
                        String result = data.getExtras().getString("result_data");
                        try {
                            JSONObject resultJson = new JSONObject(result);
                            String sign = resultJson.getString("sign");
                            String dataOrg = resultJson.getString("data");
                            // 验签证书同后台验签证书
                            // 此处的verify，商户需送去商户后台做验签
                            boolean ret = verify(dataOrg, sign, "01");
//                            if (ret) {
//                                // 验证通过后，显示支付结果
//                                msg = "支付成功！";
//                            } else {
//                                // 验证不通过后的处理
//                                // 建议通过商户后台查询支付结果
//                                msg = "支付失败！";
//                            }
                            msg = ret ? "支付成功" : "支付失败";
                        } catch (JSONException e) {
                        }
                    } else {
                        // 未收到签名信息
                        // 建议通过商户后台查询支付结果
                        msg = "支付成功！";
                    }
                } else if (str.equalsIgnoreCase("fail")) {
                    msg = "支付失败！";
                } else if (str.equalsIgnoreCase("cancel")) {
                    msg = "用户取消了支付";
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("支付结果通知");
                builder.setMessage(msg);
                builder.setInverseBackgroundForced(true);
                // builder.setCustomTitle();
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
        }


    }


    /**
     * 跳转相机
     */
    public void GotoCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //file = new File(DIR_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(PHOTO_FILE_PATH)));

        startActivityForResult(intent, GOTO_CAMARE);
    }

    /**
     * 请求权限结果
     *
     * @param requestCode  请求码
     * @param permissions
     * @param grantResults 所请求权限的结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //同意相机权限
                    GotoCamera();
                } else {
                    Toast.makeText(this, "打开相机需要权限 权限管理-->应用-->相应权限", Toast.LENGTH_LONG).show();
                }
        }
    }

    /**
     * @return
     */
    public boolean a() {
        preference = getSharedPreferences(POWER_SERVICE, MODE_APPEND);
        boolean a = preference.getBoolean("aaa", false);
        //Log.e("aaa", "a11111: " + a);
        return a;
    }

    @Override
    protected void onStop() {
        // Log.e("aaa", "onStop: ");
        super.onStop();
        SharedPreferences.Editor edit = preference.edit();
        edit.putBoolean("aaa", true);
        edit.commit();
    }

    /**
     * 后台验证
     *
     * @param msg
     * @param sign64
     * @param mode
     * @return
     */
    public boolean verify(String msg, String sign64, String mode) {
        return true;
    }

    String tn = null;
    @Override
    public void run() {
        //String tn = null;
        InputStream is;
        try {
            String url = TN_URL_01;
            URL myURL = new URL(url);
            //打开一个链接
            //url对象用openconnection()打开连接；获得URLConnection类对象，
            // 再用URLConnection类对象的connect（）方法进行连接
            URLConnection ucon = myURL.openConnection();
            ucon.setConnectTimeout(120000);
            is = ucon.getInputStream();
            int i = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((i = is.read()) != -1) {
                baos.write(i);
            }
            tn = baos.toString();
            is.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Message msg = mHandler.obtainMessage();
//        msg.obj = tn;
//        mHandler.sendMessage(msg);
        mHandler.sendEmptyMessage(1);
    }

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
            //String tn = "";
            if (tn== null || tn.length() == 0) {
            //if (msg.obj == null || ((String) msg.obj).length() == 0) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(UserDetailActivity.this);
                builder.setTitle("错误提示");
                builder.setMessage("网络连接失败,请重试!");
                builder.setNegativeButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            } else {
                //tn = (String) msg.obj;
                /*************************************************
                 * 步骤2：通过银联工具类启动支付插件
                 ************************************************/
                //doStartUnionPayPlugin(UserDetailActivity.this, tn, "01");
                Log.e("aaa", "handleMessage: "+tn );
                String serverMode = "01";
                UPPayAssistEx.startPay (UserDetailActivity.this, null, null, tn, serverMode);
            }
        };
};
}
