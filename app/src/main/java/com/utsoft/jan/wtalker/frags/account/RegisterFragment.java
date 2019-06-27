package com.utsoft.jan.wtalker.frags.account;

import android.content.Context;
import android.widget.TextView;

import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.wtalker.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.account
 */
public class RegisterFragment extends PresenterFragment {
    @BindView(R.id.txt_font)
    TextView txtFont;

    private AccountTrigger trigger;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        trigger = (AccountTrigger) context;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_register;
    }

    @OnClick(R.id.txt_font)
    public void onClick() {
        if (trigger!=null){
            trigger.triggerView();
        }
    }
}
