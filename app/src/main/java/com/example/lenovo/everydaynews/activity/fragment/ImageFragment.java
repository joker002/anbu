package com.example.lenovo.everydaynews.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.everydaynews.R;

/**
 * 图片碎片
 * Created by lenovo on 2016/10/9.
 */
public class ImageFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.three_fragment, container, false);
        return view;

    }
}
