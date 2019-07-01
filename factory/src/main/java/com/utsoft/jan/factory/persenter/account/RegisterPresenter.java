package com.utsoft.jan.factory.persenter.account;

import android.text.TextUtils;

import com.utsoft.jan.common.Common;
import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.factory.R;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.helper.AccountHelper;
import com.utsoft.jan.factory.model.api.account.RegisterModel;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.persistence.Account;
import com.utsoft.jan.factory.presenter.BasePresenter;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2019/6/28.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.account
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.View> implements RegisterContract.Presenter, DataSource.CallBack<User> {

    public RegisterPresenter(RegisterContract.View mView) {
        super(mView);

    }

    @Override
    public void register(String phone, String password, String name) {
          start();
          if (!CheckMobile(phone)){
              Application.showToast(R.string.data_account_phone_invalid_parameter);
          }else if (password.length()<6){
              Application.showToast(R.string.data_account_register_password_length);
          }else if (name.length()<2){
              Application.showToast(R.string.data_account_register_name_lenght);
          }else {
              RegisterModel registerModel = new RegisterModel(phone, password, name,Account.getPushId());
              AccountHelper.register(registerModel,this);
          }
    }

    private boolean CheckMobile(String phone) {
        return !TextUtils.isEmpty(phone) && Pattern.matches(Common.Constance.REGEX_MOBILE,phone);
    }

    @Override
    public void onDataLoad(User user) {
        RegisterContract.View view = getView();
        if (view!=null){
            view.registerSuccess();
        }
    }

    @Override
    public void onDataNotAvailable(int resId) {
        RegisterContract.View view = getView();
        if (view!=null){
            view.showErrorMsg(resId);
        }
    }
}
