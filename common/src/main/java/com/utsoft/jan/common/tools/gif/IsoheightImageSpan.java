package com.utsoft.jan.common.tools.gif;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;

/**
 * 和文字等高的ImageSpan
 * 在选择模式下，光标的index可能在Span对应的文字内部，标记为IntegratedSpan可以防止光标
 * 在内部，在replace其他文字时可以正常删除该span
 */
public class IsoheightImageSpan extends ImageSpan  {
    protected boolean resized = false;
    private static final char[] ELLIPSIS_NORMAL = {'\u2026'}; // this is "..."
    private static final char[] ELLIPSIS_TWO_DOTS = {'\u2025'}; // this is ".."
    protected int drawableHeight = 0;
    private FontMetricsInt fm;

    public IsoheightImageSpan(Drawable d) {
        super(d);
    }

    public IsoheightImageSpan(@NonNull Drawable d, int verticalAlignment) {
        super(d, verticalAlignment);
    }

    public IsoheightImageSpan(@NonNull Drawable d,
                              @NonNull String source) {
        super(d, source);
    }

    public IsoheightImageSpan(@NonNull Drawable d,
                              @NonNull String source, int verticalAlignment) {
        super(d, source, verticalAlignment);
    }

    public IsoheightImageSpan(Context context, Uri uri) {
        super(context, uri);
    }

    public IsoheightImageSpan(@NonNull Context context,
                              @NonNull Uri uri, int verticalAlignment) {
        super(context, uri, verticalAlignment);
    }

    public IsoheightImageSpan(@NonNull Context context, int resourceId) {
        super(context, resourceId);
    }

    public IsoheightImageSpan(@NonNull Context context, int resourceId, int verticalAlignment) {
        super(context, resourceId, verticalAlignment);
    }

    public IsoheightImageSpan(@NonNull Bitmap b) {
        super(b);
    }

    public IsoheightImageSpan(@NonNull Bitmap b, int verticalAlignment) {
        super(b, verticalAlignment);
    }

    public IsoheightImageSpan(@NonNull Context context,
                              @NonNull Bitmap b) {
        super(context, b);
    }

    public IsoheightImageSpan(@NonNull Context context,
                              @NonNull Bitmap b, int verticalAlignment) {
        super(context, b, verticalAlignment);
    }

    @Override
    public int getSize(Paint paint, CharSequence text,
                       int start, int end, FontMetricsInt fm) {
        if (fm != null) {
            this.fm = fm;
            drawableHeight = fm.descent - fm.ascent;
        }
        Drawable drawable = getResizedDrawable();
        Rect bounds = drawable.getBounds();
        return bounds.right;
    }

    protected Drawable getResizedDrawable() {
        Drawable d = getDrawable();
        if (drawableHeight == 0) {
            d.setBounds(new Rect(0, 0,
                    (int) (60),
                    60));
            return d;
        }
        if (!resized) {
            resized = true;
            d.setBounds(new Rect(0, 0,
                    (int) (1f * drawableHeight * d.getIntrinsicWidth() / d.getIntrinsicWidth()),
                    drawableHeight));
        }
        return d;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                     int bottom, Paint paint) {
        final String s = text.toString();
        String subS = s.substring(start, end);
        if (ELLIPSIS_NORMAL[0] == subS.charAt(0)
                || ELLIPSIS_TWO_DOTS[0] == subS.charAt(0)) {
            canvas.save();
            canvas.drawText(subS, x, y, paint);
            canvas.restore();
        } else {
            Drawable d = getResizedDrawable();
            canvas.save();
            //向上移动
            int transY;
            fm = paint.getFontMetricsInt();
            transY = y + fm.ascent;
            canvas.translate(x, transY);
            d.draw(canvas);
            canvas.restore();
        }
    }

}
