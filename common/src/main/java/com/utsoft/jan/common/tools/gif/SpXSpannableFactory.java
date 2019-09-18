package com.utsoft.jan.common.tools.gif;

import android.support.annotation.NonNull;
import android.text.NoCopySpan;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

import java.util.List;

/**
 * Created by Administrator on 2019/8/16.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools.gif
 */
public class SpXSpannableFactory extends Spannable.Factory{
    private List<NoCopySpan> mNoCopySpans;
    public SpXSpannableFactory(List<NoCopySpan> watchers) {
        mNoCopySpans = watchers;
    }

    public Spannable newSpannable(@NonNull CharSequence source) {
        SpannableStringBuilder spannableStringBuilder = new  SpannableStringBuilder();
        for (NoCopySpan span : mNoCopySpans) {
            spannableStringBuilder.setSpan(span, 0,0,
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE | Spanned.SPAN_PRIORITY);
        }
        spannableStringBuilder.append(source);
        return spannableStringBuilder;
    }
}
