package com.utsoft.jan.factory.data.message;

import android.text.TextUtils;

import com.utsoft.jan.factory.data.helper.DbHelper;
import com.utsoft.jan.factory.data.helper.MessageHelper;
import com.utsoft.jan.factory.data.helper.UserHelper;
import com.utsoft.jan.factory.model.card.MessageCard;
import com.utsoft.jan.factory.model.db.Message;
import com.utsoft.jan.factory.model.db.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.message
 */
public class MessageDispatcher implements MessageCenter{

    private static Executor executor  = Executors.newSingleThreadExecutor();

    private static class Holder{
        private static MessageDispatcher instance = new MessageDispatcher();
    }

    private MessageDispatcher(){
    }

    public static MessageDispatcher getIntances(){
        return Holder.instance;
    }

    @Override
    public void dispathcher(MessageCard... cards) {
        executor.execute(new HandlerRunable(cards));
    }


    private class HandlerRunable implements Runnable {
        private final MessageCard[] mCards;

        public HandlerRunable(MessageCard[] cards) {
            this.mCards = cards;
        }

        @Override
        public void run() {
            List<Message> cardList =new ArrayList<>();
            for (MessageCard mCard : mCards) {
                if (TextUtils.isEmpty(mCard.getId())||TextUtils.isEmpty(mCard.getSenderId())||TextUtils.isEmpty(mCard.getReceiverId()))
                    continue;
                //判断本地有没有这条消息
                Message message = MessageHelper.findLocal(mCard.getId());
                if (message != null) {

                    if (message.getStatus() == Message.STATUS_DONE) {
                        continue;
                    }

                    if (mCard.getStatus() == Message.STATUS_DONE) {
                        message.setCreateAt(mCard.getCreateAt());
                    }
                    message.setAttach(mCard.getAttach());
                    message.setContent(mCard.getContent());
                    message.setStatus(mCard.getStatus());
                }
                else {
                    //新建message数据
                    String receiverId = mCard.getReceiverId();
                    User receiver = UserHelper.findFromLocal(receiverId);
                    User send = UserHelper.findFromLocal(mCard.getSenderId());
                    if (receiver == null)
                        continue;
                    message = mCard.build(send, receiver);
                }

                cardList.add(message);
            }
            if (cardList.size()>0)
            DbHelper.save(Message.class,cardList.toArray(new Message[0]));
        }
    }
}
