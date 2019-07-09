package com.utsoft.jan.factory.persenter.contact;

import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2019/7/9.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.contact
 */
public interface FollowContract {
    interface Presenter extends BaseContract.Presenter{
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter>{
        void followDone(UserCard card);
    }
}
