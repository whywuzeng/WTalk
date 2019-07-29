package com.utsoft.jan.lang.bean;

/**
 * Created by Administrator on 2019/7/29.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.lang.bean
 */
public class DtVersionBean {
//    {
//        "AndroidAppUrl" : "",
//            "AndroidAppVersion" : "1.0.0",
//            "bundelUrl" : "https://exeutest.blob.core.chinacloudapi.cn/app/dtdebug/dtsbundlejs.zip",
//            "bundleVersion" : "6.11.26",
//            "iosAppUrl" : "",
//            "iosAppVersion" : "1.0.0"
//    }

    private String AndroidAppUrl;
    private String AndroidAppVersion;
    private String bundelUrl;
    //热更新版本
    private String bundleVersion;
    private String iosAppUrl;

    public String getAndroidAppUrl() {
        return AndroidAppUrl;
    }

    public void setAndroidAppUrl(String androidAppUrl) {
        AndroidAppUrl = androidAppUrl;
    }

    public String getAndroidAppVersion() {
        return AndroidAppVersion;
    }

    public void setAndroidAppVersion(String androidAppVersion) {
        AndroidAppVersion = androidAppVersion;
    }

    public String getBundelUrl() {
        return bundelUrl;
    }

    public void setBundelUrl(String bundelUrl) {
        this.bundelUrl = bundelUrl;
    }

    public String getBundleVersion() {
        return bundleVersion;
    }

    public void setBundleVersion(String bundleVersion) {
        this.bundleVersion = bundleVersion;
    }

    public String getIosAppUrl() {
        return iosAppUrl;
    }

    public void setIosAppUrl(String iosAppUrl) {
        this.iosAppUrl = iosAppUrl;
    }

    public String getIosAppVersion() {
        return iosAppVersion;
    }

    public void setIosAppVersion(String iosAppVersion) {
        this.iosAppVersion = iosAppVersion;
    }

    private String iosAppVersion;

}
