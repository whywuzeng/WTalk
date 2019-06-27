package com.utsoft.jan.wtalker.frags.account;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.factory.persenter.account.LoginContract;
import com.utsoft.jan.factory.persenter.account.LoginPresenter;
import com.utsoft.jan.wtalker.R;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.account
 */
public class LoginFragment extends PresenterFragment<LoginContract.LoginPresenter>
        implements LoginContract.LoginView {

    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.txt_go_register)
    TextView txtGoRegister;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private AccountTrigger trigger;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        trigger = (AccountTrigger) context;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void loginSucess() {
        Application.showToast(R.string.data_login_success);
    }

    @OnClick({R.id.txt_go_register, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_go_register:
                if (trigger!=null)
                trigger.triggerView();
                break;
            case R.id.btn_submit:
                mPresenter.login(editPhone.getText().toString(),editPassword.getText().toString());
                break;
        }
    }
}
