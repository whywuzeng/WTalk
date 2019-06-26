package com.utsoft.jan.factory.data;

import android.support.annotation.StringRes;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data
 */
public interface DataSource {
    /**
     *  成功 失败接口
     */

    interface CallBack<T> extends SucceedCallback<T>,FailedCallback{

    }

    interface SucceedCallback<T> {
        void onDataLoad(T t);
    }

    interface FailedCallback{
        void onDataNotAvailable(@StringRes int resId);
    }

    /**
     * 销毁操作
     */
    void dispose();
}
