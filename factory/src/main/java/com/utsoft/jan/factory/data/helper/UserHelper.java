package com.utsoft.jan.factory.data.helper;

import com.utsoft.jan.factory.Factory;
import com.utsoft.jan.factory.R;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.user.UserDispatcher;
import com.utsoft.jan.factory.model.RspModel;
import com.utsoft.jan.factory.model.api.user.UserUpdateModel;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.net.Network;
import com.utsoft.jan.factory.net.RemoteService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/7/1.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.helper
 */
public class UserHelper {
    // 更新用户信息的操作，异步的
    public static void update(UserUpdateModel model, final DataSource.CallBack<UserCard> callback) {
        // 调用Retrofit对我们的网络请求接口做代理
        RemoteService service = Network.remote();
        // 得到一个Call
        Call<RspModel<UserCard>> call = service.userUpdate(model);
        // 网络请求
        call.enqueue(new Callback<RspModel<UserCard>>() {
            @Override
            public void onResponse(Call<RspModel<UserCard>> call, Response<RspModel<UserCard>> response) {
                RspModel<UserCard> rspModel = response.body();
                if (rspModel.success()) {
                    UserCard userCard = rspModel.getResult();
//                    // 唤起进行保存的操作
//                    Factory.getUserCenter().dispatch(userCard);
                    // 返回成功
                    callback.onDataLoad(userCard);
                } else {
                    // 错误情况下进行错误分配
                    Factory.decodeRspCode(rspModel, callback);
                }
            }

            @Override
            public void onFailure(Call<RspModel<UserCard>> call, Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }

    /**
     *刷新联系人，从网络请求。
     * 存储到数据库，然后监听数据库的改变
     */
    public static void refreshContacts() {
        RemoteService service = Network.remote();
        service.userContacts()
                .enqueue(new Callback<RspModel<List<UserCard>>>() {
                    @Override
                    public void onResponse(Call<RspModel<List<UserCard>>> call, Response<RspModel<List<UserCard>>> response) {
                        RspModel<List<UserCard>> rspModel = response.body();
                        if (rspModel.success()) {
                            List<UserCard> cards = rspModel.getResult();
                            if (cards == null && cards.size() == 0)
                                return;
                            UserCard[] cards1 = cards.toArray(new UserCard[0]);
                            UserDispatcher.getInstance().dispatch(cards1);
                        }
                        else {
                            Factory.decodeRspCode(rspModel, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<List<UserCard>>> call, Throwable t) {

                    }
                });
    }
}
