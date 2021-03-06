package com.utsoft.jan.factory.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.utsoft.jan.factory.Factory;
import com.utsoft.jan.factory.data.helper.UserHelper;
import com.utsoft.jan.factory.model.api.account.AccountRspModel;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.model.db.User_Table;

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
    private static final String KEY_TOKEN ="KEY_TOKEN_ID";
    private static final String KEY_ACCOUNT="KEY_ACCOUNT_ID";
    private static final String KEY_ISBIND="KEY_ISBIND_ID";
    private static final String KEY_PUSHID="KEY_PUSHID_ID";

    private static String sUserId;
    private static String sToken;
    private static String sAccount;
    private static boolean isBind;
    private static String sPushId;


    //存储数据
    public static void save(Context context) {
        SharedPreferences share = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        share.edit()
                .putString(KEY_USERID, sUserId)
                .putString(KEY_TOKEN,sToken)
                .putString(KEY_ACCOUNT,sAccount)
                .apply();
    }

    //加载数据
    public static void load(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Account.class.getName(), Context.MODE_PRIVATE);
        sUserId = sp.getString(KEY_USERID, "");
        sToken = sp.getString(KEY_TOKEN, "");
        sAccount = sp.getString(KEY_ACCOUNT,"");
    }

    public static boolean isLogin(){
        return !TextUtils.isEmpty(sUserId)
                &&!TextUtils.isEmpty(sToken);
    }

    /**
     * 得到token
     * @return token
     */
    public static String getToken() {
        return sToken;
    }

    public static void login(AccountRspModel result) {
        sToken = result.getToken();
        sAccount = result.getAccount();
        sUserId = result.getUser().getId();
        save(Factory.app());
    }

    public static void setBind(boolean bind){
        isBind = bind;
    }

    public static boolean isBind(){
        return isBind;
    }

    public static void setPushId(String pushId){
        sPushId = pushId;
        save(Factory.app());
    }

    public static String getPushId(){
        return sPushId;
    }

    public static boolean isComplete() {
        if (isLogin()&&isUserMessageComplete()){
            return true;
        }
        return false;
    }

    public static boolean isUserMessageComplete(){
        User localUser;
        if (TextUtils.isEmpty(sUserId)){
            localUser = new User();
        }else {
            localUser = UserHelper.findFromLocal(sUserId);
        }
        return localUser.isContentComplete();
    }

    public static User getUser() {
        return TextUtils.isEmpty(sUserId) ? new User() : SQLite.select()
                .from(User.class)
                .where(User_Table.id.eq(sUserId))
                .querySingle();
    }
}
