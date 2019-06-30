package com.utsoft.jan.wtalker.activities;

import android.content.Context;
import android.content.Intent;

import com.utsoft.jan.common.app.Activity;

/**
 * Created by Administrator on 2019/6/29.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.activities
 */
public class UserActivity extends Activity {
    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    public static void show(Context context){
        context.startActivity(new Intent(context,UserActivity.class));
    }
}
