package com.utsoft.jan.wtalker.frags.account;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.factory.persenter.account.RegisterContract;
import com.utsoft.jan.factory.persenter.account.RegisterPresenter;
import com.utsoft.jan.wtalker.R;
import com.utsoft.jan.wtalker.activities.MainActivity;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.account
 */
public class RegisterFragment
        extends PresenterFragment<RegisterContract.Presenter> implements RegisterContract.View {
    @BindView(R.id.txt_go_login)
    TextView txtFont;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.loading)
    Loading loading;

    private AccountTrigger trigger;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        trigger = (AccountTrigger) context;
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = new RegisterPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @OnClick(R.id.txt_go_login)
    public void onClick() {
        if (trigger != null) {
            trigger.triggerView();
        }
    }

    @OnClick(R.id.btn_register)
    public void onClick1() {
        mPresenter.register(editPhone.getText().toString()
                ,editPassword.getText().toString(),editName.getText().toString());
    }

    @Override
    public void showLoading() {
        super.showLoading();
        editName.setEnabled(false);
        editPassword.setEnabled(false);
        editPhone.setEnabled(false);

        loading.start();
    }

    @Override
    public void showErrorMsg(int strId) {
        super.showErrorMsg(strId);
        editName.setEnabled(true);
        editPassword.setEnabled(true);
        editPhone.setEnabled(true);

        loading.stop();
    }

    @Override
    public void registerSuccess() {

        editName.setEnabled(true);
        editPassword.setEnabled(true);
        editPhone.setEnabled(true);

        loading.stop();
        MainActivity.show(Objects.requireNonNull(getContext()));
        getActivity().finish();
    }

}
