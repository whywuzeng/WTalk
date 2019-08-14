package com.utsoft.jan.common.tools;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.utsoft.jan.common.R;

/**
 * Created by Administrator on 2019/8/14.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools
 */
public class TextDrawableCallback {

    public static void setText(final TextView textView, final CharSequence nText,
                               final TextView.BufferType type) {
        //type 默认SPANNABLE，保证textView中取出来的是Spannable类型
        textView.setText(nText, type);
        CharSequence text = textView.getText();
        if (text instanceof Spannable) {
            ImageSpan[] gifSpans = ((Spannable) text).getSpans(0, text.length(), ImageSpan.class);
            //将之前的Callback disable
            Object oldCallback = textView
                    .getTag(R.id.drawable_callback_tag);
            if (oldCallback != null && oldCallback instanceof CallbackForTextView) {
                ((CallbackForTextView) oldCallback).disable();
            }
            Drawable.Callback callback = new CallbackForTextView(textView);
            //callback在drawable中是弱引用
            //让textview持有callback,防止callback被回收
            //也使旧的callback可以被系统回收
            textView.setTag(R.id.drawable_callback_tag, callback);
            for (ImageSpan gifSpan : gifSpans) {
                Drawable drawable = gifSpan.getDrawable();
                if (drawable != null) {
                    drawable.setCallback(callback);
                }
            }
            GifSpanWatcher gifSpanWatcher = new GifSpanWatcher();
            //gifSpanWatcher是SpanWatcher,继承自NoCopySpan
            //只有setText之后设置SpanWatcher才能成功
            ((Spannable) text).setSpan(gifSpanWatcher, 0, text.length(),
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE | Spanned.SPAN_PRIORITY);

        }
        textView.invalidate();
    }
}
