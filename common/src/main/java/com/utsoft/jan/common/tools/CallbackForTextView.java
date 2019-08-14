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

    @Override
    public void invalidateDrawable( Drawable who) {
        if (!enable)
        {
            return;
        }
        if (System.currentTimeMillis() - lastInvalidateTime > 40) {
            lastInvalidateTime = System.currentTimeMillis();
            mTextView.invalidate();
        }
    }

    @Override
    public void scheduleDrawable( Drawable who,  Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable( Drawable who, Runnable what) {

    }

    public void disable() {
        enable = false;
    }
}
