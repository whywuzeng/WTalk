package com.utsoft.jan.common.app;

import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.utsoft.jan.widget.convention.PlaceHolderView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

    private static final String TAG = "Activity";

    public PlaceHolderView getHolderView() {
        return holderView;
    }

    public void setHolderView(PlaceHolderView holderView) {
        this.holderView = holderView;
    }

    protected PlaceHolderView holderView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        hookOrientation(this);//hook，绕过检查
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


    /**
     * java.lang.IllegalStateException: Only fullscreen opaque activities can request orientation
     * <p>
     * 修复android 8.0存在的问题
     * <p>
     * 在Activity中onCreate()中super之前调用
     *
     * @param activity
     */
    public static void hookOrientation(Activity activity) {
        //目标版本8.0及其以上
        if (activity.getApplicationInfo().targetSdkVersion >= Build.VERSION_CODES.O) {
            if (isTranslucentOrFloating(activity)) {
                fixOrientation(activity);
            }
        }
    }

    /**
     * 设置屏幕不固定，绕过检查
     *
     * @param activity
     */
    private static void fixOrientation(Activity activity) {
        try {
            Class<android.app.Activity> activityClass = android.app.Activity.class;
            Field mActivityInfoField = activityClass.getDeclaredField("mActivityInfo");
            mActivityInfoField.setAccessible(true);
            ActivityInfo activityInfo = (ActivityInfo) mActivityInfoField.get(activity);
            //设置屏幕不固定
            activityInfo.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查屏幕 横竖屏或者锁定就是固定
     *
     * @param activity
     * @return
     */
    private static boolean isTranslucentOrFloating(Activity activity) {
        boolean isTranslucentOrFloating = false;
        try {
            Class<?> styleableClass = Class.forName("com.android.internal.R$styleable");
            Field WindowField = styleableClass.getDeclaredField("Window");
            WindowField.setAccessible(true);
            int[] styleableRes = (int[]) WindowField.get(null);
            //先获取到TypedArray
            final TypedArray typedArray = activity.obtainStyledAttributes(styleableRes);
            Class<?> ActivityInfoClass = ActivityInfo.class;
            //调用检查是否屏幕旋转
            Method isTranslucentOrFloatingMethod = ActivityInfoClass.getDeclaredMethod("isTranslucentOrFloating", TypedArray.class);
            isTranslucentOrFloatingMethod.setAccessible(true);
            isTranslucentOrFloating = (boolean) isTranslucentOrFloatingMethod.invoke(null, typedArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isTranslucentOrFloating;
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
