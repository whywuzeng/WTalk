package com.utsoft.jan.factory.net;

import com.utsoft.jan.factory.model.RspModel;
import com.utsoft.jan.factory.model.api.account.AccountRspModel;
import com.utsoft.jan.factory.model.api.account.LoginModel;
import com.utsoft.jan.factory.model.api.account.RegisterModel;
import com.utsoft.jan.factory.model.api.user.UserUpdateModel;
import com.utsoft.jan.factory.model.card.UserCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @POST("account/register")
    Call<RspModel<AccountRspModel>> accountRegister(@Body RegisterModel model);

    @PUT("user")
    Call<RspModel<UserCard>> userUpdate(@Body UserUpdateModel model);

    // 获取联系人列表
    @GET("user/contact")
    Call<RspModel<List<UserCard>>> userContacts();
}
