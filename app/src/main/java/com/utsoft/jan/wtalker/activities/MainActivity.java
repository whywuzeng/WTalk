package com.utsoft.jan.wtalker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.utsoft.jan.common.app.Activity;
import com.utsoft.jan.factory.persistence.Account;
import com.utsoft.jan.wtalker.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity  {

    @BindView(R.id.im_portrait)
    ImageView imPortrait;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean initArgs(Bundle extras) {
        if (Account.isComplete()) {
            return super.initArgs(extras);
        }
        else {
            UserActivity.show(this);
            return false;
        }
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @OnClick(R.id.im_portrait)
    public void onClick() {

    }
}
