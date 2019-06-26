package com.utsoft.jan.factory.net;

import android.text.TextUtils;

import com.utsoft.jan.common.Common;
import com.utsoft.jan.factory.persistence.Account;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.net
 */
public class Network {

    // 1 静态的单例
    public static Network instance;
    private OkHttpClient client;
    private Retrofit retrofit;
    static {
        instance = new Network();
    }

    private Network(){}

    //构建 httpclient 实例
    public static OkHttpClient getClient(){
        if (instance.client!=null)
        {
            return instance.client;
        }
         instance.client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder request = original.newBuilder();
                        request.addHeader("Content-Type", "application/json");
                        if (!TextUtils.isEmpty(Account.getToken())) {
                            request.addHeader("token", Account.getToken());
                        }
                        Request newRequest = request.build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();
        return instance.client;
    }
    // 构建 retrofit 实例
    public static Retrofit getRetrofit(){
        if (instance.retrofit!=null)
        {
            return instance.retrofit;
        }
        OkHttpClient client = getClient();
        Retrofit.Builder builder = new Retrofit.Builder();

       return instance.retrofit = builder.baseUrl(Common.Constance.base_Url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    //返回 请求代理 remote
    public static RemoteService remote(){
        return getRetrofit().create(RemoteService.class);
    }
}
