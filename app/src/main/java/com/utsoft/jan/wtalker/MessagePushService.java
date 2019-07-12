package com.utsoft.jan.wtalker;

import android.content.Context;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.utsoft.jan.factory.Factory;
import com.utsoft.jan.factory.data.helper.AccountHelper;
import com.utsoft.jan.factory.persistence.Account;

/**
 * Created by Administrator on 2019/6/30.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker
 */
public class MessagePushService extends GTIntentService {
    private static final String TAG = "MessagePushService";

    public MessagePushService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {

    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        // 透传消息的处理，详看SDK demo
        byte[] payload = msg.getPayload();
        if (payload!=null){
            String message = new String(payload);
            onMessageArrived(message);
        }
    }

    private void onMessageArrived(String message) {
        Factory.dispatchPush(message);
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + clientid);
        onClientInit(clientid);
    }

    private void onClientInit(String clientid) {
        Account.setPushId(clientid);
        if (Account.isLogin()) {
            AccountHelper.bindPush(null);
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
    }
}
