package com.utsoft.jan.factory.persenter.message;

import com.utsoft.jan.factory.data.helper.UserHelper;
import com.utsoft.jan.factory.data.message.MessageRepository;
import com.utsoft.jan.factory.model.db.Message;
import com.utsoft.jan.factory.model.db.User;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.message
 */
public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView> {

    public ChatUserPresenter( ChatContract.UserView mView, String receiverId) {
        super(new MessageRepository(receiverId), mView, receiverId, Message.RECEIVER_TYPE_NONE);
    }

    @Override
    public void start() {
        super.start();
        ChatContract.View<User> view = getView();
        User user = UserHelper.findFromLocal(mReceiverId);
        if (view != null) {
            view.onInit(user);
        }
    }
}
