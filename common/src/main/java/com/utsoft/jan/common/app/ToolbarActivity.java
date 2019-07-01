package com.utsoft.jan.common.app;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.utsoft.jan.common.R;

/**
 * Created by Administrator on 2019/7/1.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.app
 */
public abstract class ToolbarActivity extends Activity {

    private Toolbar mToolbar;

    @Override
    protected void initWidget() {
        super.initWidget();
        mToolbar =(Toolbar)findViewById(R.id.toolbar);
        initToolbar();
    }

    private void initToolbar(){
        if (mToolbar !=null){
            setSupportActionBar(mToolbar);
        }
        initNeedBack();
    }

    private void initNeedBack() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
