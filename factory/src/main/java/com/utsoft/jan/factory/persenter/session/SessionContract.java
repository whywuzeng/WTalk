package com.utsoft.jan.factory.persenter.session;

import com.utsoft.jan.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2019/7/19.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.session
 */
public interface SessionContract {

    interface Presenter extends BaseContract.Presenter {

    }

    interface SessionView<ViewModel> extends BaseContract.RecyclerView<Presenter, ViewModel> {
    }
}
