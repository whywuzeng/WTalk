package com.utsoft.jan.common.app;

import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.utsoft.jan.common.R;
import com.utsoft.jan.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2019/7/9.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.app
 */
public abstract class PresenterToolbarActivity<Presenter extends BaseContract.Presenter>
        extends ToolbarActivity implements BaseContract.View<Presenter>{

    protected Presenter mPresenter;

    private ProgressDialog mProgressDialog;

    @Override
    protected void initBefore() {
        super.initBefore();
        initPresenter();
    }

    protected abstract void initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null)
        mPresenter.destory();
    }

    @Override
    public void showLoading() {
        if (holderView!=null)
        {
            holderView.triggerLoading();
        }else {
            ProgressDialog dialog =  mProgressDialog;
            if (dialog == null)
            {
                dialog = new ProgressDialog(this, R.style.AppTheme_Dialog_Alert_Light);
                //不可触摸取消
                dialog.setCanceledOnTouchOutside(false);
                //强制取消关闭界面
                dialog.setCancelable(true);
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        finish();
                    }
                });
                mProgressDialog = dialog;
            }

            dialog.setMessage(getText(R.string.prompt_loading));
            dialog.show();
        }
    }

    @Override
    public void showErrorMsg(int strId) {
        hideDialogLoading();
        if (holderView!=null){
            holderView.triggerError(strId);
        }else {
            Application.showToast(strId);
        }
    }

    private void hideDialogLoading(){
        ProgressDialog dialog = this.mProgressDialog;
        if (dialog != null)
        {
            this.mProgressDialog =null;
            dialog.dismiss();
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }
}
