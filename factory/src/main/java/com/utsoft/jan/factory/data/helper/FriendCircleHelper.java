package com.utsoft.jan.factory.data.helper;

import com.utsoft.jan.factory.R;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.model.RspModel;
import com.utsoft.jan.factory.model.card.FriendCircleCard;
import com.utsoft.jan.factory.net.Network;
import com.utsoft.jan.factory.net.RemoteService;
import com.utsoft.jan.factory.persenter.friend.CommentModel;
import com.utsoft.jan.factory.persenter.friend.ReleaseFriendCircleModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/8/13.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.helper
 */
public class FriendCircleHelper {

    /**
     * 发布
     * @param model
     * @param callBack
     */
    public static void release(ReleaseFriendCircleModel model, final DataSource.CallBack<FriendCircleCard> callBack) {
        RemoteService remote = Network.remote();
        remote.release(model)
                .enqueue(new Callback<RspModel<FriendCircleCard>>() {
                    @Override
                    public void onResponse(Call<RspModel<FriendCircleCard>> call, Response<RspModel<FriendCircleCard>> response) {
                        if (response.isSuccessful()) {
                            RspModel<FriendCircleCard> body = response.body();
                            if (body.success()) {
                                FriendCircleCard result = body.getResult();
                                if (callBack != null) {
                                    callBack.onDataLoad(result);
                                }
                            }
                        }else {
                            if (callBack != null) {
                                callBack.onDataNotAvailable(R.string.data_network_error);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RspModel<FriendCircleCard>> call, Throwable t) {
                        if (callBack != null) {
                            callBack.onDataNotAvailable(R.string.data_network_error);
                        }
                    }
                });
    }

    /**
     * 评论
     * @param commentModel
     * @param callBack
     */
    public static void comment(CommentModel commentModel, DataSource.CallBack callBack) {

    }

    /**
     * 点赞
     * @param friendCircleId
     * @param callBack
     */
    public static void fabulous(String friendCircleId, DataSource.CallBack callBack) {

    }

    /**
     * 获取朋友圈全部list
     * @param callBack
     * @return
     */
    public static Call friendCircle(final DataSource.CallBack<List<FriendCircleCard>> callBack) {
        RemoteService remote = Network.remote();
        Call<RspModel<List<FriendCircleCard>>> rspModelCall = remote.friendCircle();
        rspModelCall.enqueue(new Callback<RspModel<List<FriendCircleCard>>>() {
            @Override
            public void onResponse(Call<RspModel<List<FriendCircleCard>>> call, Response<RspModel<List<FriendCircleCard>>> response) {
                if (response.isSuccessful()) {
                    RspModel<List<FriendCircleCard>> body = response.body();
                    if (body.success()) {
                        List<FriendCircleCard> result = body.getResult();
                        if (callBack != null) {
                            callBack.onDataLoad(result);
                        }
                    }
                }else {
                    if (callBack != null) {
                        callBack.onDataNotAvailable(R.string.data_network_error);
                    }
                }
            }

            @Override
            public void onFailure(Call<RspModel<List<FriendCircleCard>>> call, Throwable t) {
                if (callBack != null) {
                    callBack.onDataNotAvailable(R.string.data_network_error);
                }
            }
        });

        return rspModelCall;
    }
}
