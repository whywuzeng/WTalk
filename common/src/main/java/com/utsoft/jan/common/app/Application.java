package com.utsoft.jan.common.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.app
 */
public class Application extends android.app.Application {

    private static Application instance;

    private static List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activityList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityList.remove(activity);
            }
        });
    }

    public static void exitApp() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    public static Application getInstance(){
        return instance;
    }

    public static void showToast(final String msg){
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Toast.makeText(instance,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showToast(@StringRes int msgId){
        showToast(instance.getString(msgId));
    }

}
