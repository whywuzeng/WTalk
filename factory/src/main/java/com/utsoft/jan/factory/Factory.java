package com.utsoft.jan.factory;

import android.support.annotation.StringRes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.message.MessageDispatcher;
import com.utsoft.jan.factory.data.user.UserDispatcher;
import com.utsoft.jan.factory.model.PushModel;
import com.utsoft.jan.factory.model.RspModel;
import com.utsoft.jan.factory.model.card.MessageCard;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.persistence.Account;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory
 */
public class Factory {

    public static void setup(){
        //所有数据的初始化 都在这里开始
        FlowManager.init(new FlowConfig.Builder(app())
                .openDatabasesOnInit(true)
                .build());

        //加载数据
        Account.load(app());
    }

    public static Application app(){
        return Application.getInstance();
    }

    //全局的Gson
    private final Gson gson;

    private final static Factory instance;

    private final Executor mExecutor;

    static {
        instance = new Factory();
    }

    private Factory(){
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
                .create();
        //初始化线程池
        mExecutor = Executors.newFixedThreadPool(4);
    }

    public static Gson getGson(){
        return instance.gson;
    }

    public static void decodeRspCode(RspModel body, DataSource.FailedCallback mCallBack) {
        if (body == null)
            return;
        switch (body.getCode()){
            case RspModel.ERROR_SERVER:
                decodeRspCode(R.string.data_rsp_error_service, mCallBack);
                break;
            case RspModel.ERROR_NOT_FOUND_USER:
                decodeRspCode(R.string.data_rsp_error_not_found_user, mCallBack);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP:
                decodeRspCode(R.string.data_rsp_error_not_found_group,mCallBack);
                break;
            case RspModel.ERROR_NOT_FOUND_GROUP_MEMBER:
                decodeRspCode(R.string.data_rsp_error_not_found_group_member,mCallBack);
                break;
            case RspModel.ERROR_CREATE_USER:
                decodeRspCode(R.string.data_rsp_error_create_user,mCallBack);
                break;
            case RspModel.ERROR_CREATE_GROUP:
                decodeRspCode(R.string.data_rsp_error_create_group,mCallBack);
                break;
            case RspModel.ERROR_CREATE_MESSAGE:
                decodeRspCode(R.string.data_rsp_error_create_message,mCallBack);
                break;
            case RspModel.ERROR_PARAMETERS:
                decodeRspCode(R.string.data_rsp_error_parameters,mCallBack);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_ACCOUNT:
                decodeRspCode(R.string.data_rsp_error_parameters_exist_account,mCallBack);
                break;
            case RspModel.ERROR_PARAMETERS_EXIST_NAME:
                decodeRspCode(R.string.data_rsp_error_parameters_exist_name,mCallBack);
                break;
             case RspModel.ERROR_ACCOUNT_TOKEN:
                 Application.showToast(R.string.data_rsp_error_account_token);
                 instance.logout();
                 break;
            case RspModel.ERROR_ACCOUNT_LOGIN:
                decodeRspCode(R.string.data_rsp_error_account_login, mCallBack);
                break;
            case RspModel.ERROR_ACCOUNT_REGISTER:
                decodeRspCode(R.string.data_rsp_error_account_register, mCallBack);
                break;
            case RspModel.ERROR_ACCOUNT_NO_PERMISSION:
                decodeRspCode(R.string.data_rsp_error_account_no_permission, mCallBack);
                break;
            case RspModel.ERROR_UNKNOWN:
            default:
                decodeRspCode(R.string.data_rsp_error_unknown, mCallBack);
                break;
        }
    }

    public static void runOnAsync(Runnable runnable){
        instance.mExecutor.execute(runnable);
    }

    public static void dispatchPush(String message) {
        if (!Account.isLogin())
            return;

        PushModel model = PushModel.decode(message);

        for (PushModel.Entity entity : model.getEntities()) {
            switch (entity.type){
                case PushModel.ENTITY_TYPE_LOGOUT:
                    instance.logout();
                    // 退出情况下，直接返回，并且不可继续
                    return;

                case PushModel.ENTITY_TYPE_MESSAGE: {
                    // 普通消息
                    MessageCard card = getGson().fromJson(entity.content, MessageCard.class);
                    getMessageCenter().dispathcher(card);
                    break;
                }

                case PushModel.ENTITY_TYPE_ADD_FRIEND: {
                    // 好友添加
                    UserCard card = getGson().fromJson(entity.content, UserCard.class);
                    getUserCenter().dispatch(card);
                    break;
                }

                case PushModel.ENTITY_TYPE_ADD_GROUP: {
                    // 添加群
//                    GroupCard card = getGson().fromJson(entity.content, GroupCard.class);
//                    getGroupCenter().dispatch(card);
                    break;
                }

                case PushModel.ENTITY_TYPE_ADD_GROUP_MEMBERS:
                case PushModel.ENTITY_TYPE_MODIFY_GROUP_MEMBERS: {
                    // 群成员变更, 回来的是一个群成员的列表
//                    Type type = new TypeToken<List<GroupMemberCard>>() {
//                    }.getType();
//                    List<GroupMemberCard> card = getGson().fromJson(entity.content, type);
//                    // 把数据集合丢到数据中心处理
//                    getGroupCenter().dispatch(card.toArray(new GroupMemberCard[0]));
                    break;
                }
                case PushModel.ENTITY_TYPE_EXIT_GROUP_MEMBERS: {
                    // TODO 成员退出的推送
                }

            }
        }
    }

    /**
     * 重新退出
     */
    private void logout() {

    }

    public static void decodeRspCode(@StringRes int idRes, DataSource.FailedCallback mCallBack) {
        if (mCallBack!=null){
            mCallBack.onDataNotAvailable(idRes);
        }
    }

    public static UserDispatcher getUserCenter(){
        return UserDispatcher.getInstance();
    }

    public static MessageDispatcher getMessageCenter(){
        return MessageDispatcher.getIntances();
    }
}
