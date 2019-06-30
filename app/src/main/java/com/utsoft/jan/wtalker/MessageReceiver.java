package com.utsoft.jan.wtalker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.utsoft.jan.factory.data.helper.AccountHelper;
import com.utsoft.jan.factory.persistence.Account;

/**
 * Created by Administrator on 2019/6/30.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker
 */
public class MessageReceiver extends BroadcastReceiver {
    private static final String TAG = "MessageReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null)
            return;
        Bundle extras = intent.getExtras();
        switch (extras.getInt(PushConsts.CMD_ACTION)){
            case PushConsts.GET_CLIENTID:{
                Log.i(TAG, "GET_CLIENTID:" + extras.toString());
                // 当Id初始化的时候
                // 获取设备Id
                onClientInit(extras.getString("clientid"));
                break;
            }
            case PushConsts.GET_MSG_DATA: {

                break;
            }
            default:
                Log.i(TAG, "OTHER:" + extras.toString());
                break;
        }
    }

    private void onClientInit(String clientid) {
        Account.setPushId(clientid);
        if (Account.isLogin()) {
            AccountHelper.bindPush(null);
        }
    }
}
