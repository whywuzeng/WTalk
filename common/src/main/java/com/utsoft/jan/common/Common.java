package com.utsoft.jan.common;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common
 */
public class Common {
    /**
     * 一些不变的变量
     */
    public interface Constance{
        // 手机号的正则,11位手机号
        String REGEX_MOBILE = "[1][3,4,5,7,8][0-9]{9}$";

//        String base_Url = "http://192.168.1.228:8080/api/";
//        192.168.182.1
        String base_Url = "http://192.168.1.102:8080/api/";
    }
}
