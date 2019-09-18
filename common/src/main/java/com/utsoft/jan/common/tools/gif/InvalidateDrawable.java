package com.utsoft.jan.common.tools.gif;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2019/8/15.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools.gif
 */
public interface InvalidateDrawable {
    void addRefreshListener(Drawable.Callback callback);
    void removeRefreshListener(Drawable.Callback callback);
    void refresh();

    int getInterval();
}
