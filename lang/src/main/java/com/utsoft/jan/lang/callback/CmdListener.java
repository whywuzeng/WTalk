package com.utsoft.jan.lang.callback;

/**
 * Created by Administrator on 2019/7/29.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.lang.callback
 */
public interface CmdListener {
    void CmdSuccessCallBack(int exitValue);
    void CmdFailureCallBack(int exitValue);
}
