package com.utsoft.jan.factory.data.helper;

import android.text.TextUtils;

import com.utsoft.jan.factory.Factory;
import com.utsoft.jan.factory.R;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.model.RspModel;
import com.utsoft.jan.factory.model.api.account.AccountRspModel;
import com.utsoft.jan.factory.model.api.account.LoginModel;
import com.utsoft.jan.factory.model.api.account.RegisterModel;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.net.Network;
import com.utsoft.jan.factory.net.RemoteService;
import com.utsoft.jan.factory.persistence.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.helper
 */
public class AccountHelper {
    /**
     * 登陆的model
     * @param model api提供数据
     * @param callBack 成功与失败回调的接口
     */
    public static void login(LoginModel model, DataSource.CallBack<User> callBack){
        RemoteService remote = Network.remote();
        Call<RspModel<AccountRspModel>> rspModelCall = remote.accountLogin(model);
        rspModelCall.enqueue(new AccountRspCallback(callBack));
    }

    /**
     * 对有pushId 进行绑定
     * @param callBack
     */
    public static void bindPush(DataSource.CallBack<User> callBack) {
        String pushId = Account.getPushId();
        //判断是否有PushId
        if (TextUtils.isEmpty(pushId)){
            return;
        }
        RemoteService remote = Network.remote();
        Call<RspModel<AccountRspModel>> call = remote.accountBind(pushId);
        call.enqueue(new AccountRspCallback(callBack));
    }

    /**
     * 注册接口
     * @param registerModel model
     * @param callBack 回调
     */
    public static void register(RegisterModel registerModel, DataSource.CallBack<User> callBack) {
        RemoteService remote = Network.remote();
        Call<RspModel<AccountRspModel>> call = remote.accountRegister(registerModel);
        call.enqueue(new AccountRspCallback(callBack));
    }

    private static class AccountRspCallback implements Callback<RspModel<AccountRspModel>> {

        private final DataSource.CallBack<User> mCallBack;

        public AccountRspCallback(DataSource.CallBack<User> callBack) {
            this.mCallBack = callBack;
        }

        @Override
        public void onResponse(Call<RspModel<AccountRspModel>> call,
                               Response<RspModel<AccountRspModel>> response) {
            RspModel<AccountRspModel> body = response.body();
                if (body.success()){
                    AccountRspModel result = body.getResult();
                    User user = result.getUser();

                    //保持数据库

                    //持久化xml
                    Account.login(result);
                    if (result.isBind()){
                        Account.setBind(true);
                        if (mCallBack!=null)
                        mCallBack.onDataLoad(user);
                    }else {
                        //推送绑定服务
                        bindPush(mCallBack);
                    }
                }else {
                    //错误解析  根据code 去解析
                    Factory.decodeRspCode(body,mCallBack);
                }
        }

        @Override
        public void onFailure(Call<RspModel<AccountRspModel>> call, Throwable t) {
            if (mCallBack!=null){
                mCallBack.onDataNotAvailable(R.string.data_network_error);
            }
        }
    }
}
