package com.utsoft.jan.common.tools;

import android.text.SpanWatcher;
import android.text.Spannable;
import android.view.View;

import com.utsoft.jan.common.tools.gif.InvalidateDrawable2;
import com.utsoft.jan.common.tools.gif.RefreshListener;
import com.utsoft.jan.common.tools.gif.RefreshSpan;

/**
 * Created by Administrator on 2019/8/14.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools
 */
public class GifSpanWatcher implements SpanWatcher,RefreshListener {
    private static final String TAG = "GifSpanWatcher";

    private long mLastTime;
    private static final int REFRESH_INTERVAL = 100;
    private View mView;

    public GifSpanWatcher(View view) {
        this.mView = view;
    }

    @Override
    public void onSpanAdded(Spannable text, Object what, int start, int end) {


        if (what instanceof RefreshSpan) {
            InvalidateDrawable2 drawable = ((RefreshSpan) what).getInvalidateDrawable();
            if (drawable != null) {
                drawable.addRefreshListener(this);
            }
        }
    }

    @Override
    public void onSpanRemoved(Spannable text, Object what, int start, int end) {
        //只处理ImageSpan被移除的情况
        if (what instanceof RefreshSpan) {
            InvalidateDrawable2 drawable = ((RefreshSpan) what).getInvalidateDrawable();
            if (drawable != null) {
                drawable.removeRefreshListener(this);
            }
        }
    }

    @Override
    public void onSpanChanged(Spannable text, Object what, int ostart, int oend, int nstart,
                              int nend) {
    }

    @Override
    public void onRefresh() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastTime > REFRESH_INTERVAL) {
            mLastTime = currentTime;

            mView.invalidate();

            //final HashMap<Object, GlidePreDrawable> drawableCacheMap = Face.getDrawableCacheMap();
            //if (drawableCacheMap!=null)
            //{
            //    if (!TextUtils.isEmpty(mSource))
            //    {
            //        if (drawableCacheMap.containsKey(mSource)){
            //            final GlidePreDrawable glidePreDrawable = drawableCacheMap.get(mSource);
            //            if (glidePreDrawable == mGlidePreDrawable)
            //            {
            //                mView.invalidate();
            //            }
            //        }
            //    }
            //}

        }
    }
}
