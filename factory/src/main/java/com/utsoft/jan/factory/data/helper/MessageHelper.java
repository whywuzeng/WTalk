package com.utsoft.jan.factory.data.helper;

import android.os.SystemClock;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.utsoft.jan.common.Common;
import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.factory.Factory;
import com.utsoft.jan.factory.model.RspModel;
import com.utsoft.jan.factory.model.api.message.MsgCreateModel;
import com.utsoft.jan.factory.model.card.MessageCard;
import com.utsoft.jan.factory.model.db.Message;
import com.utsoft.jan.factory.model.db.Message_Table;
import com.utsoft.jan.factory.net.Network;
import com.utsoft.jan.factory.net.RemoteService;
import com.utsoft.jan.factory.net.UploadHelper;
import com.utsoft.jan.utils.PicturesCompressor;

import java.io.File;
import java.util.concurrent.ExecutionException;

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

    public static void push(final MsgCreateModel model){
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                //如何 本地有，且是失败状态
                final Message message = findLocal(model.getId());
                if (message != null && message.getStatus() != Message.STATUS_FAILED)
                    return;

                // 我们在发送的时候需要通知界面更新状态，Card;
                //        自建一个card 给card一个状态通知界面
                final MessageCard card = model.buildCard();
                Factory.getMessageCenter().dispathcher(card);

                //如果是图片 压缩图片并上传.
                if (message.getType()!=Message.TYPE_STR)
                {
                    //判断content的前缀 不是https auzer
                    if (!model.getContent().startsWith(UploadHelper.ENDPOINT))
                    {

                    String content = null;

                    switch (message.getType())
                    {
                        case Message.TYPE_PIC:
                            content = uploadPicture(model);
                            break;
                        case Message.TYPE_AUDIO:

                            break;
                        default:
                            break;
                    }

                    if (TextUtils.isEmpty(content))
                    {
                        //失败
                        card.setStatus(Message.STATUS_FAILED);
                        Factory.getMessageCenter().dispathcher(card);
                        return;
                    }

                    card.setContent(content);
                    Factory.getMessageCenter().dispathcher(card);
                    model.refreshCard();
                }
                }

                //文本直接访问接口  回调的时候 有成功的调度  和 失败的调度
                //factory getMessageCenter
                RemoteService service = Network.remote();
                final Call<RspModel<MessageCard>> call = service.msgPush(model);
                call.enqueue(new Callback<RspModel<MessageCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<MessageCard>> call, Response<RspModel<MessageCard>> response) {
                        RspModel<MessageCard> rspModel = response.body();
                        if (rspModel.success() && rspModel.getResult() != null) {
                            MessageCard result = rspModel.getResult();
                            Factory.getMessageCenter().dispathcher(result);
                        }
                        else {
                            Factory.decodeRspCode(rspModel, null);
                            onFailure(call, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<MessageCard>> call, Throwable t) {
                        card.setStatus(Message.STATUS_FAILED);
                        Factory.getMessageCenter().dispathcher(card);
                    }
                });
            }
        });
    }

    private static String uploadPicture(MsgCreateModel model) {

        if (TextUtils.isEmpty(model.getContent()))
            return null;

        File tmpFile = null;

        // 通过Glide的缓存区间解决了图片外部权限的问题
        try {
            tmpFile = Glide.with(Factory.app())
                   .load(model.getContent())
                   .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                   .get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (tmpFile!=null)
        {
            File cacheDir = Application.getInstance().getCacheDir();
            String tmpFolder = String.format("%s/file/image/Cache_%s.png", cacheDir.getAbsolutePath(), SystemClock.uptimeMillis());

            PicturesCompressor.compressImage(tmpFile.getAbsolutePath(),tmpFolder,Common.Constance.MAX_UPLOAD_IMAGE_LENGHT);

            return UploadHelper.uploadMsgPicture(tmpFolder);
        }

        return null;
    }

    public static Message findLastMessage(String id) {
       return SQLite.select()
                .from(Message.class)
                .where(OperatorGroup.clause().and(Message_Table.sender_id.eq(id)))
                .or(Message_Table.receiver_id.eq(id))
                .orderBy(Message_Table.createAt,false)
                .querySingle();

    }
}
