package com.utsoft.jan.factory.data.message;

import com.utsoft.jan.factory.model.card.MessageCard;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.message
 */
public interface MessageCenter {
    void dispathcher(MessageCard... cards);
}
