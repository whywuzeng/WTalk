package com.utsoft.jan.factory.persenter.account;

import android.text.TextUtils;

import com.utsoft.jan.factory.R;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.model.api.account.LoginModel;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.presenter.BasePresenter;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.account
 */
public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter,DataSource.CallBack<User> {

    public LoginPresenter(LoginContract.LoginView mView) {
        super(mView);
    }

    @Override
    public void login(String phone,String password) {
        start();
        LoginContract.LoginView view = getView();
        if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(password))
        {
            //todo 做一个lang的module为存放地方
            view.showErrorMsg(R.string.data_account_login_invalid_parameter);
        }else {
            LoginModel loginModel = new LoginModel(phone, password);

        }

    }

    @Override
    public void onDataLoad(User user) {

    }

    @Override
    public void onDataNotAvailable(int resId) {

    }

}
