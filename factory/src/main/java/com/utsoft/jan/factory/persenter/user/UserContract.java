package com.utsoft.jan.factory.persenter.user;

import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.presenter.BaseContract;

/**
 * Created by Administrator on 2019/7/1.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.user
 */
public interface UserContract {
    interface View extends BaseContract.View<UserContract.Presenter>{
        void loadSuccess(UserCard card);
    }

    interface Presenter extends BaseContract.Presenter{
        void loadUserMessage();
    }
}
