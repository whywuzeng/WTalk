package com.utsoft.jan.factory.data.user;

import com.utsoft.jan.factory.model.card.UserCard;

/**
 * Created by Administrator on 2019/7/8.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.user
 */
public interface UserCenter {
    //分发一堆用户卡片的信息，并更新到数据库
    void dispatch(UserCard... cards);
}
