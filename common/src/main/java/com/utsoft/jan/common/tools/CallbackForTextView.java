package com.utsoft.jan.common.tools;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by Administrator on 2019/8/14.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools
 */
public class CallbackForTextView implements Drawable.Callback {

    private final TextView mTextView;
    private boolean enable = true;
    private long lastInvalidateTime;

    public CallbackForTextView(TextView textView) {
        this.mTextView = textView;
    }

    //需要重绘时调用。
    @Override
    public void invalidateDrawable( Drawable who) {
        if (!enable)
        {
            return;
        }
        if (System.currentTimeMillis() - lastInvalidateTime > 400) {
            lastInvalidateTime = System.currentTimeMillis();
            mTextView.invalidate();
        }
    }

    //调用此方法准备下一帧动画。
    @Override
    public void scheduleDrawable( Drawable who,  Runnable what, long when) {

    }

    //取消scheduleDrawable(Drawable who, Runnable what, long when)方法的上一次操作。
    @Override
    public void unscheduleDrawable( Drawable who, Runnable what) {

    }

    public void disable() {
        enable = false;
    }

    public void setNeedInterval(boolean b) {
        enable = b;
    }
}
