package com.utsoft.jan.factory.persenter.contact;

import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2019/7/9.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.contact
 */
public interface PersonalContract {
    interface Presenter extends BaseContract.Presenter {
        User getUserPersonal();
    }

    interface View extends BaseContract.View<Presenter> {

        String getUserID();

        void onLoadDone(User user);

        //是否可以发起聊天
        void allowSayHello(boolean isAllow);

        // 设置关注状态
        void setFollowStatus(boolean isFollow);
    }
}
