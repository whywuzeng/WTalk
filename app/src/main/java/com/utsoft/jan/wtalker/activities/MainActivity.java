package com.utsoft.jan.wtalker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.utsoft.jan.common.app.Activity;
import com.utsoft.jan.factory.persistence.Account;
import com.utsoft.jan.wtalker.R;

public class MainActivity extends Activity {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean initArgs(Bundle extras) {
        if (Account.isComplete()) {
            return super.initArgs(extras);
        }else {
            UserActivity.show(this);
            return false;
        }
    }

    public static void show(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }
}
