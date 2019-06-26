package com.utsoft.jan.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persistence
 */
public class Account {

    //保存用户登陆的状态
    private static final String KEY_USERID = "KEY_USER_ID";

    private static String sUserId;

    //存储数据
    public static void save(Context context) {
        SharedPreferences share = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        share.edit()
                .putString(KEY_USERID, sUserId)
                .apply();
    }

    //加载数据
    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        sUserId = sp.getString(KEY_USERID, "");
    }

    public static boolean isLogin(){
        return !TextUtils.isEmpty(sUserId);
    }

    /**
     * 得到token
     * @return token
     */
    public static CharSequence getToken() {

        return null;
    }

    //回调设值数据

}
