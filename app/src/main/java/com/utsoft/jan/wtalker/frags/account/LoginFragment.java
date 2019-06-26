package com.utsoft.jan.wtalker.frags.account;

import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.factory.persenter.account.LoginContract;
import com.utsoft.jan.factory.persenter.account.LoginPresenter;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.account
 */
public class LoginFragment extends PresenterFragment<LoginContract.LoginPresenter> implements LoginContract.LoginView  {

    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void loginSucess() {

    }
}
