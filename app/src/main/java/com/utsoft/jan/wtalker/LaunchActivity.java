package com.utsoft.jan.wtalker;

import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.utsoft.jan.common.app.Activity;

import net.qiujuer.genius.ui.compat.UiCompat;

/**
 * Created by Administrator on 2019/6/25.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker
 */
public class LaunchActivity extends Activity {

    private ColorDrawable mBgDrawable;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //拿到根布局
        View root = findViewById(R.id.activity_launch);
        //获取颜色
        int color = UiCompat.getColor(getResources(), R.color.colorPrimary);

        ColorDrawable colorDrawable = new ColorDrawable(color);
        //设置背景
        root.setBackground(colorDrawable);

        mBgDrawable = colorDrawable;
    }

    @Override
    protected void initData() {
        super.initData();


    }
}
