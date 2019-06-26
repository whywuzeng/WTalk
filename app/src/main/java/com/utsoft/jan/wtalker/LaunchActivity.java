package com.utsoft.jan.wtalker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.util.Property;
import android.view.View;

import com.utsoft.jan.common.app.Activity;
import com.utsoft.jan.factory.persistence.Account;
import com.utsoft.jan.wtalker.activities.MainActivity;

import net.qiujuer.genius.res.Resource;
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
        int color = UiCompat.getColor(getResources(), R.color.colorAccent);

        ColorDrawable colorDrawable = new ColorDrawable(color);
        //设置背景
        root.setBackground(colorDrawable);

        mBgDrawable = colorDrawable;
    }

    @Override
    protected void initData() {
        super.initData();

        //先运行 50%的动画
        startAnim(0.8f,new Runnable(){
            @Override
            public void run() {
                waitRecevierPushId();
            }
        });

    }

    private void waitRecevierPushId() {
        if (Account.isLogin()){
            skip();
        }
    }

    private void skip() {
        startAnim(1f, new Runnable() {
            @Override
            public void run() {
                reallySkip();
            }
        });
    }

    private void reallySkip() {
        if (Account.isLogin()) {
            MainActivity.show(this);
        }
        else {

        }
        finish();
    }

    private void startAnim(float progress, final Runnable runnable) {
        int finalColor = Resource.Color.WHITE;

        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        int endcolor = (int) argbEvaluator.evaluate(progress, mBgDrawable.getColor(), finalColor);

        ValueAnimator objectAnimator =  ObjectAnimator.ofObject(this,property,argbEvaluator,endcolor);
        objectAnimator.setDuration(1500);
        objectAnimator.setIntValues(mBgDrawable.getColor(),endcolor);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                runnable.run();
            }
        });
        objectAnimator.start();
    }

    private final Property<LaunchActivity,Object> property  =new Property<LaunchActivity,Object>(Object.class,"color"){

        @Override
        public Object get(LaunchActivity object) {

            return object.mBgDrawable.getColor();
        }

        @Override
        public void set(LaunchActivity object, Object value) {
//            super.set(object, value);
            object.mBgDrawable.setColor((int)value);
        }
    };
}
