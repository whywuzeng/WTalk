package com.utsoft.jan.factory.persenter.message;

import android.support.v7.util.DiffUtil;

import com.utsoft.jan.factory.data.helper.MessageHelper;
import com.utsoft.jan.factory.data.message.MessageDataSource;
import com.utsoft.jan.factory.model.api.message.MsgCreateModel;
import com.utsoft.jan.factory.model.db.Message;
import com.utsoft.jan.factory.persenter.BaseSourcePersenter;
import com.utsoft.jan.factory.utils.DiffUIDataCallback;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.message
 */
public class ChatPresenter<View extends ChatContract.View> extends BaseSourcePersenter<Message,Message,View,MessageDataSource> implements ChatContract.Presenter {


    protected final String mReceiverId;
    protected final int mReceiverType;

    public ChatPresenter(MessageDataSource source,
                         View mView,
                         String receiverId, int receiverType) {
        super(source, mView);
        this.mReceiverId = receiverId;
        this.mReceiverType = receiverType;
    }

    @Override
    public void pushText(String content) {
        //请求数据
        MsgCreateModel model = new MsgCreateModel.ModelBuild()
                .content(content, Message.TYPE_STR)
                .receiver(mReceiverId, mReceiverType)
                .Build();
        MessageHelper.push(model);
    }

    @Override
    public void pushImage(String... paths) {
        for (final String path : paths) {

            MsgCreateModel model = new MsgCreateModel.ModelBuild()
                    .content(path, Message.TYPE_PIC)
                    .receiver(mReceiverId, mReceiverType)
                    .Build();
            MessageHelper.push(model);
        }
    }

    @Override
    public void pushAudio(File file, long time) {
        MsgCreateModel model = new MsgCreateModel.ModelBuild()
                .content(file.getAbsolutePath(), Message.TYPE_AUDIO)
                .attach(String.valueOf(time))
                .receiver(mReceiverId, mReceiverType)
                .Build();
        MessageHelper.push(model);
    }

    @Override
    public void rePush(Message message) {

    }


    /**
     * 数据库数据发生变化
     * @param messages
     */
    @Override
    public void onDataLoad(List<Message> messages) {
        //更新recycleView
        ChatContract.View view = getView();
        if (view==null)
            return;

        //拿到老数据
        List<Message> listData = view.getRecyclerAdapter().getListData();

        // 差异计算
        DiffUIDataCallback<Message> callback = new DiffUIDataCallback<>(listData, messages);
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        //然后更新
        refreshData(result,messages);
    }
}
