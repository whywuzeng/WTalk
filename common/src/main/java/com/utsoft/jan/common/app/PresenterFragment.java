package com.utsoft.jan.common.app;

import android.content.Context;

import com.utsoft.jan.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.app
 */
public abstract class PresenterFragment<Presenter extends BaseContract.Presenter> extends Fragment implements BaseContract.View<Presenter> {

    //拿到Presenter 引用
    protected Presenter mPresenter;
    //初始化setpresetnter()

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initPresenter();
    }

    protected void initPresenter() {

    }

    @Override
    public void showLoading() {
        holderView.triggerLoading();
    }

    @Override
    public void showErrorMsg(int strId) {
        if (holderView!=null){
            holderView.triggerError(strId);
        }else {
            Application.showToast(strId);
        }
    }

    @Override
    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter !=null){
            mPresenter.destory();
        }
    }
}
