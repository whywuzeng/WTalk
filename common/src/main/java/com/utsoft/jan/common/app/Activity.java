package com.utsoft.jan.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/25.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.app
 */
public abstract class Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化windows
        initWindow();

        if (initArgs(getIntent().getExtras())){
            int resId =  getContentLayoutId();
            setContentView(resId);
            initBefore();
            initWidget();
            initData();
        }else {
            finish();
        }
    }

    protected  void initData(){

    }

    protected void initWidget(){
        ButterKnife.bind(this);
    }

    protected  void initBefore(){

    }

    protected abstract int getContentLayoutId();

    //判断参数是否正确
    protected  boolean initArgs(Bundle extras){
        return true;
    }

    protected  void initWindow(){

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments){
                if (fragment instanceof com.utsoft.jan.common.app.Fragment){
                    if (((com.utsoft.jan.common.app.Fragment) fragment).onBackPressed()){
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
        finish();
    }
}
