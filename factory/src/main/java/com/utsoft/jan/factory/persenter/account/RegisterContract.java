package com.utsoft.jan.factory.persenter.account;

import com.utsoft.jan.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2019/6/28.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.account
 */
public interface RegisterContract {
    interface View extends BaseContract.View<RegisterContract.Presenter>{
        void registerSuccess();
    }

    interface Presenter extends BaseContract.Presenter{
        void register(String phone,String password,String name);
    }
}
