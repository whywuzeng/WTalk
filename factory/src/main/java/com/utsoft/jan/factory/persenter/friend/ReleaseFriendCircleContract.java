package com.utsoft.jan.factory.persenter.friend;


import com.utsoft.jan.factory.model.card.FriendCircleCard;
import com.utsoft.jan.factory.presenter.BaseContract;

import java.util.List;


/**
 * 发布朋友圈
 */

public interface ReleaseFriendCircleContract {
    interface Presenter extends BaseContract.Presenter{
        void release(String content, List<String> imgs);
    }

    //用户界面
    interface View extends BaseContract.View<Presenter>{
        void onReleaseDone(FriendCircleCard friendCircleCard);
    }
}
