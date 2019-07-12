package com.utsoft.jan.factory.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.utsoft.jan.factory.Factory;
import com.utsoft.jan.factory.model.RspModel;
import com.utsoft.jan.factory.model.api.message.MsgCreateModel;
import com.utsoft.jan.factory.model.card.MessageCard;
import com.utsoft.jan.factory.model.db.Message;
import com.utsoft.jan.factory.model.db.Message_Table;
import com.utsoft.jan.factory.net.Network;
import com.utsoft.jan.factory.net.RemoteService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.helper
 */
public class MessageHelper {
    //从本地找消息  id
    public static Message findLocal(String id){
        return SQLite.select()
                .from(Message.class)
                .where(Message_Table.id.eq(id))
                .querySingle();
    }

    public static void push(MsgCreateModel model){

        //如何 本地有，且是失败状态
        final Message message = findLocal(model.getId());
        if (message!=null && message.getStatus()!=Message.STATUS_FAILED)
            return;

        // 我们在发送的时候需要通知界面更新状态，Card;
//        自建一个card 给card一个状态通知界面
        final MessageCard card = model.buildCard();
        Factory.getMessageCenter().dispathcher(card);

        //文本直接访问接口  回调的时候 有成功的调度  和 失败的调度
        //factory getMessageCenter
        RemoteService service = Network.remote();
        final Call<RspModel<MessageCard>> call = service.msgPush(model);
        call.enqueue(new Callback<RspModel<MessageCard>>() {
            @Override
            public void onResponse(Call<RspModel<MessageCard>> call, Response<RspModel<MessageCard>> response) {
                RspModel<MessageCard> rspModel = response.body();
                if (rspModel.success()&&rspModel.getResult()!=null){
                    MessageCard result = rspModel.getResult();
                    Factory.getMessageCenter().dispathcher(result);
                }else {
                    Factory.decodeRspCode(rspModel,null);
                    onFailure(call,null);
                }
            }

            @Override
            public void onFailure(Call<RspModel<MessageCard>> call, Throwable t) {
                card.setStatus(Message.STATUS_FAILED);
                Factory.getMessageCenter().dispathcher(card);
            }
        });

    }
}
