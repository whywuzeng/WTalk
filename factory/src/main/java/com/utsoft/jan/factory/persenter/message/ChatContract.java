package com.utsoft.jan.factory.persenter.message;

import com.utsoft.jan.factory.model.db.Message;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.presenter.BaseContract;

import java.io.File;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.message
 */
public interface ChatContract {
    interface Presenter extends BaseContract.Presenter {
        //发送文字
        void pushText(String content);

        //重新发送一个消息，返回是否调度成功
        void rePush(Message message);

        void pushImage(String... paths);

        void pushAudio(File file, long time);
    }

    interface View<InitModel> extends BaseContract.RecyclerView<Presenter, Message> {
        void onInit(InitModel model);
    }

    interface UserView extends View<User>{

    }
}
