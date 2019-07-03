package com.utsoft.jan.widget.convention;

import android.support.annotation.StringRes;

/**
 * Created by Administrator on 2019/7/3.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.widget.convention
 */
public interface PlaceHolderView {

    /**
     * 没有数据
     * 显示空布局，隐藏当前数据布局
     */
    void triggerEmpty();

    /**
     * 网络错误
     * 显示网络错误的图标
     */
    void triggerNetError();

    /**
     * 加载错误，并显示错误信息
     */
    void triggerError(@StringRes int strRes);

    /**
     * 显示正在加载的状态
     */
    void triggerLoading();

    /**
     * 数据加载成功
     * 调用该方法的时候要隐藏当前占位布局
     */
    void triggerOk();

    /**
     * 是否显示成功布局
     */
    void triggerOkOrEmpty(boolean isOk);

}
