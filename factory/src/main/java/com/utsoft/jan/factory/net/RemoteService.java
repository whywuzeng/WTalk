package com.utsoft.jan.factory.net;

import com.utsoft.jan.factory.model.RspModel;
import com.utsoft.jan.factory.model.api.account.AccountRspModel;
import com.utsoft.jan.factory.model.api.account.LoginModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.net
 */
public interface RemoteService {
    @POST("account/login")
    Call<RspModel<AccountRspModel>> accountLogin(@Body LoginModel model);

    @POST("account/bind/{pushId}")
    Call<RspModel<AccountRspModel>> accountBind(@Path(encoded = true,value = "pushId") String pushId);
}
