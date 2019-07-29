package com.utsoft.jan.lang.net;

import com.utsoft.jan.lang.bean.DtVersionBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 网络请求的所有的接口
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
public interface RemoteService {
//开发库
//    https://exeutest.blob.core.chinacloudapi.cn/app/dtdebug/dtsversion.json
//测试库
//    https://exeutest.blob.core.chinacloudapi.cn/app/dtsversion.json

    // 开发库获取json 测试库
    @GET("app/{name}")
    Call<DtVersionBean> getDtsVersion(@Path("name") String name);

}
