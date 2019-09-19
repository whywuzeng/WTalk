package com.utsoft.jan.common.tools.gif;

import android.content.Context;
import android.text.NoCopySpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.utsoft.jan.common.tools.GifSpanWatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/16.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools.gif
 */
public class OneSpanTextview extends android.support.v7.widget.AppCompatTextView {

    public OneSpanTextview(Context context) {
        super(context);
    }

    public OneSpanTextview(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public OneSpanTextview(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        List<NoCopySpan> watchers = new ArrayList<>();
        watchers.add(new GifSpanWatcher(this));
        setSpannableFactory(new SpXSpannableFactory(watchers));
    }

    public void setText(CharSequence text, TextView.BufferType type) {
        super.setText(text, TextView.BufferType.SPANNABLE);
    }
}
