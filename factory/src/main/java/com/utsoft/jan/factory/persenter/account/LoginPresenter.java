package com.utsoft.jan.factory.persenter.account;

import android.text.TextUtils;

import com.utsoft.jan.factory.R;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.helper.AccountHelper;
import com.utsoft.jan.factory.model.api.account.LoginModel;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.Timer;
import java.util.TimerTask;

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
    public void login(final String phone, final String password) {
        start();
        TimerTask timerTask = new TimerTask(){

            @Override
            public void run() {
                LoginContract.LoginView view = getView();
                if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(password))
                {
                    //todo 做一个lang的module为存放地方
                    view.showErrorMsg(R.string.data_account_login_invalid_parameter);
                }else {
                    LoginModel loginModel = new LoginModel(phone, password);
                    AccountHelper.login(loginModel,LoginPresenter.this);
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,3000);

    }

    @Override
    public void onDataLoad(User user) {
        final LoginContract.LoginView view = getView();
        if (view==null)
            return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.loginSucess();
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int resId) {
        final LoginContract.LoginView view = getView();
        if (view == null)
            return;
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showErrorMsg(resId);
            }
        });
    }

}
