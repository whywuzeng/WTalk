package com.utsoft.jan.common.tools;

import android.graphics.drawable.Drawable;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.style.ImageSpan;

/**
 * Created by Administrator on 2019/8/14.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools
 */
public class GifSpanWatcher implements SpanWatcher {
    private static final String TAG = "GifSpanWatcher";
    @Override
    public void onSpanAdded(Spannable text, Object what, int start, int end) {

    }

    @Override
    public void onSpanRemoved(Spannable text, Object what, int start, int end) {
        //只处理ImageSpan被移除的情况
        if (what instanceof ImageSpan) {
            ImageSpan imageSpan = (ImageSpan) what;
            Drawable drawable = imageSpan.getDrawable();
            if (drawable != null) {
                drawable.setCallback(null);
            }
        }
    }

    @Override
    public void onSpanChanged(Spannable text, Object what, int ostart, int oend, int nstart,
                              int nend) {
    }
}
