package com.utsoft.jan.factory.persenter.friend;


import com.utsoft.jan.factory.model.card.FriendCircleCard;
import com.utsoft.jan.factory.presenter.BaseContract;

import java.util.List;

/**
 * @author fengyun
 * 获取朋友圈信息
 * 朋友圈契约
 */

public interface FriendCircleContract {
    interface Presenter extends BaseContract.Presenter{
        void friendCircle();
    }

    //用户界面
    interface View extends BaseContract.View<Presenter>{
        void onFriendCircleDone(List<FriendCircleCard> friendCircles);
    }
}
