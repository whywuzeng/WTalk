package com.utsoft.jan.factory.persenter.contact;

import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2019/7/4.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.contact
 */
public interface ContactContract {

    interface View extends BaseContract.RecyclerView<Presenter,User> {

    }

    interface Presenter extends BaseContract.Presenter {

    }
}
