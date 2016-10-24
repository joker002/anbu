package com.example.lenovo.everydaynews.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lenovo.everydaynews.R;
import com.example.lenovo.everydaynews.activity.fragment.CollectFragment;
import com.example.lenovo.everydaynews.activity.fragment.FollowFragment;
import com.example.lenovo.everydaynews.activity.fragment.ImageFragment;
import com.example.lenovo.everydaynews.activity.fragment.LocationFragment;
import com.example.lenovo.everydaynews.activity.fragment.NewsFragment;
import com.example.lenovo.everydaynews.adapter.NewsFragmentAdapter;
import com.example.lenovo.everydaynews.entity.News;
import com.example.lenovo.everydaynews.net.MyHttp;
import com.example.lenovo.everydaynews.net.OnResultFinishListener;
import com.example.lenovo.everydaynews.net.Response;
import com.google.gson.Gson;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import edu.zx.slidingmenu.SlidingMenu;


/**
 * 主界面
 * Created by cc on 2016/9/27.
 */

//ArrayAdapter
//android.support.v4.widget.DrawerLayout
//记得提交
public class HomeActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    ImageView mImageView;

    //左侧布局
    ListView mListLift;
    //每一个事务都是同时要执行的一套变化.可以在一个给定的事务中设置你想执行的所有变化,使用诸如 add(), remove(), 和 replace().然后, 要给activity应用事务, 必须调用 commit().
    //在调用commit()之前, 你可能想调用 addToBackStack(),将事务添加到一个fragment事务的back stack. 这个back stack由activity管理, 并允许用户通过按下 BACK 按键返回到前一个fragment状态.
    FragmentTransaction mTransaction;

    //加载的碎片
    NewsFragment mNewsFragment = new NewsFragment();
    CollectFragment mCollectFragment = new CollectFragment();
    LocationFragment mLocationFragment = new LocationFragment();
    FollowFragment mFollowFragment = new FollowFragment();
    ImageFragment mImageFragment = new ImageFragment();

    //DrawerLayout mDrawerLayout;

    //碎片管理类
    FragmentManager mFm;

    SlidingMenu mSlidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViews(R.layout.activity_home);
    }

    @Override
    public void initView() {
        //改变Title
        mIvLift.setImageResource(R.mipmap.ic_title_home_default);
        mTvTitle.setText("资讯");
        mIvRightTwo.setImageResource(R.mipmap.ic_title_share_default);

        //mDrawerLayout = (DrawerLayout) findViewById(R.id.hhhh);

        //拿到碎片管理类对象
        mFm = getSupportFragmentManager();
        mTransaction = mFm.beginTransaction();

        //添加碎片
        mTransaction.add(R.id.ll_home, mNewsFragment);
        mTransaction.add(R.id.ll_home, mCollectFragment);
        mTransaction.add(R.id.ll_home, mLocationFragment);
        mTransaction.add(R.id.ll_home, mFollowFragment);
        mTransaction.add(R.id.ll_home, mImageFragment);

        mTransaction.show(mNewsFragment);
        mTransaction.hide(mCollectFragment);
        mTransaction.hide(mLocationFragment);
        mTransaction.hide(mFollowFragment);
        mTransaction.hide(mImageFragment);
        //注册
        mTransaction.commit();

        //初始化slidingMenu
        mSlidingMenu = new SlidingMenu(this);
        //将SlidingMenu与当前Activity进行绑定 并设置模式
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        LayoutInflater inflater = getLayoutInflater();

        View listLift = inflater.inflate(R.layout.item_home_left, null);
        mListLift = (ListView) listLift.findViewById(R.id.lv_home_left);

        //设置左侧菜单setMenu(View view)   setMenu(int layoutId)
        mSlidingMenu.setMenu(listLift);


        //设置右侧菜单 setSecondaryMenu(View view)   setSecondaryMenu(int  layoutId)

        //View listRight = inflater.inflate(R.layout.item_home_right, null);
        mSlidingMenu.setSecondaryMenu(R.layout.item_home_right);


        //设置展示模式
        //是否支持左右两侧 或者只是一侧  setMode(int model)
        //SlidingMenu.LEFT_RIGHT------->左右两侧
        //SlidingMenu.LEFT------->左侧
        //SlidingMenu.RIGHT------->右侧
        mSlidingMenu.setMode(SlidingMenu.LEFT_RIGHT);


        //设置边界距离 防止菜单栏完全遮盖主内容  需要设置一定的边界距离
        mSlidingMenu.setBehindOffset(220);
        //设置触摸模式
        //SlidingMenu.TOUCHMODE_FULLSCREEN-->全屏滑动
        //SlidingMenu.TOUCHMODE_MARGIN-->边界滑动
        //SlidingMenu.TOUCHMODE_NONE-->无法滑动
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        TextView mTextView = (TextView) findViewById(R.id.tv_home_disembark);
        ImageView mIvHead = (ImageView) findViewById(R.id.iv_home_head);
        TextView mTvUpdate = (TextView) findViewById(R.id.tv_home_right_update);

        mImageView = (ImageView) findViewById(R.id.iv_home_weixin);
        mImageView.setOnClickListener(this);


        String[] strings = {"新闻", "收藏", "本地", "跟帖", "图片",};
        mListLift.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, strings));

        mListLift.setOnItemClickListener(this);

        mIvLift.setOnClickListener(this);

        mIvRightTwo.setOnClickListener(this);
        mTextView.setOnClickListener(this);
        mIvHead.setOnClickListener(this);
        mTvUpdate.setOnClickListener(this);
        //初始化ShareSDK
        ShareSDK.initSDK(this, "181ee874ccb3a");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_home_disembark:
                Intent intent = new Intent(this, DisembarkActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_home_head:
                Intent intent1 = new Intent(this, UserDetailActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_base_lift:
                mSlidingMenu.showMenu();
                break;
            case R.id.iv_base_right_two:
                mSlidingMenu.showSecondaryMenu();
                break;
            case R.id.iv_home_weixin:
                showShare();
                break;
            case R.id.tv_home_right_update:
                getUpdate();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //mDrawerLayout.closeDrawer(Gravity.LEFT);

        mTransaction = mFm.beginTransaction();
        switch (position) {
            case 0:
                mTvTitle.setText("资讯");
                mTransaction.show(mNewsFragment);
                mTransaction.hide(mCollectFragment);
                mTransaction.hide(mLocationFragment);
                mTransaction.hide(mFollowFragment);
                mTransaction.hide(mImageFragment);
                mTransaction.commit();
                break;
            case 1:
//                CollectFragment collectFragment=new CollectFragment();
//
//                ListView mListView = (ListView) view.findViewById(R.id.lv_fragment_collect);
//                News mNews = (News)getIntent().getSerializableExtra("News");
//                Log.e("aaa", "onViewCreated: "+mNews );
//
//                ArrayList<News> arrayList=new ArrayList<>();
//                arrayList.add(mNews);
//                Log.e("aaa", "onViewCreated: "+arrayList );

                //适配器
//                NewsFragmentAdapter mNewsFragmentAdapter = new NewsFragmentAdapter(this, null, R.layout.item_fragment);
//                mListView.setAdapter(mNewsFragmentAdapter);

//                mNewsFragmentAdapter.mList.addAll(arrayList);
//                mNewsFragmentAdapter.notifyDataSetChanged();

                mTvTitle.setText("新闻收藏");
                mTransaction.show(mCollectFragment);
                mTransaction.hide(mNewsFragment);
                mTransaction.hide(mLocationFragment);
                mTransaction.hide(mFollowFragment);
                mTransaction.hide(mImageFragment);
                mTransaction.commit();
                break;
            case 2:
                mTvTitle.setText("本地");
                mTransaction.show(mLocationFragment);
                mTransaction.hide(mCollectFragment);
                mTransaction.hide(mNewsFragment);
                mTransaction.hide(mFollowFragment);
                mTransaction.hide(mImageFragment);
                mTransaction.commit();
                break;
            case 3:
                mTvTitle.setText("跟帖");
                mTransaction.show(mFollowFragment);
                mTransaction.hide(mCollectFragment);
                mTransaction.hide(mLocationFragment);
                mTransaction.hide(mNewsFragment);
                mTransaction.hide(mImageFragment);
                mTransaction.commit();
                break;
            case 4:
                mTvTitle.setText("图片");
                mTransaction.show(mImageFragment);
                mTransaction.hide(mCollectFragment);
                mTransaction.hide(mLocationFragment);
                mTransaction.hide(mFollowFragment);
                mTransaction.hide(mNewsFragment);
                mTransaction.commit();
                break;
        }
    }

    //系统的返回键
    @Override
    public void onBackPressed() {
        if (mSlidingMenu.isMenuShowing() || mSlidingMenu.isSecondaryMenuShowing()) {
            mSlidingMenu.showContent();
            return;
        }
        super.onBackPressed();
    }

    /**
     * 分享
     */
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
    }

    public void getUpdate() {


        //PackageInfo 拿到版本号
        //update?imei=唯一识别号&pkg=包名&ver=版本
        Map<String, String> params = new HashMap<>();
        params.put("imei", "866818023239831");
        params.put("pkg", "com.example.lenovo.everydaynews");
        params.put("ver", "0000000");
        MyHttp.get(this, "http://118.244.212.82:9092/newsClient/update", params, new OnResultFinishListener() {
            @Override
            public void success(Response response) {
                Log.e("aaa", "success: " + response.result);
                Gson gson=new Gson();
                Update update=gson.fromJson(response.result.toString(),Update.class);
                Log.e("aaa", "success: "+update.link );
                if(!update.link.equals("1")){

                }
            }

            @Override
            public void failed(Response response) {
                Log.e("aaa", "failed: " + response.result);
            }
        });
    }
}

class Update {
    public String pkgName;//包名,
    public String version;//版本号,
    public URL link;//下载地址,
    public String md5;//MD5检验值
}