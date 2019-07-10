package com.utsoft.jan.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;

import net.qiujuer.widget.airpanel.AirPanelLinearLayout;

/**
 * Created by Administrator on 2019/7/10.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.widget
 */
public class MessageLayout extends AirPanelLinearLayout {

    public MessageLayout(Context context) {
        super(context);
    }

    public MessageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            insets.top = 0;
            insets.left = 0;
            insets.right = 0;
        }
        return super.fitSystemWindows(insets);
    }
}
