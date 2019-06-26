package com.utsoft.jan.factory.persenter.account;

import com.utsoft.jan.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.account
 */
public interface LoginContract {
    interface LoginView extends BaseContract.View<LoginPresenter> {
        void loginSucess();
    }

    interface LoginPresenter extends BaseContract.Presenter {
        void login(String phone,String password);
    }
}
