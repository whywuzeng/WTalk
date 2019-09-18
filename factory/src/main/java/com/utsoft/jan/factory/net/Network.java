package com.utsoft.jan.factory.net;

import android.text.TextUtils;

import com.utsoft.jan.common.Common;
import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.factory.Factory;
import com.utsoft.jan.factory.persistence.Account;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Objects;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

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

        SSLSocketFactory sslSocketFactory =null;

        try {
            InputStream inputStream = Application.getInstance().getApplicationContext().getAssets().open("cerbook.cer");
             sslSocketFactory = setCertificates(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
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
                 .sslSocketFactory(Objects.requireNonNull(sslSocketFactory))
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
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
    }
    //返回 请求代理 remote
    public static RemoteService remote(){
        return getRetrofit().create(RemoteService.class);
    }

    public static SSLSocketFactory setCertificates(InputStream... certificates){
        CertificateFactory factory = null;
        try {
            factory = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int index = 0;
        for (InputStream certificate : certificates) {
            String certificatesAlias = Integer.toString(index++);
            try {
                keyStore.setCertificateEntry(certificatesAlias,factory.generateCertificate(certificate));
            } catch (KeyStoreException e) {
                e.printStackTrace();
            } catch (CertificateException e) {
                e.printStackTrace();
            }

            try {
                if (certificate!=null)
                    certificate.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);

            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom()
            );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext.getSocketFactory();
    }
}
