package com.utsoft.jan.common.tools.gif;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by Administrator on 2019/8/14.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools.gif
 */
public class DrawableTarget extends SimpleTarget<GifDrawable> {

    private static final String TAG = "GifTarget";
    private final int Size;
    private GlidePreDrawable preDrawable;
    //加载结束时调用
    @Override
    public void onResourceReady(@NonNull GifDrawable resource,
                                @Nullable GlideAnimation<? super GifDrawable> transition) {
        Log.i(TAG, "onResourceReady: " + resource);
        preDrawable.setDrawable(resource);
        if (resource instanceof GifDrawable) {
            ((GifDrawable) resource).setLoopCount(GifDrawable.LOOP_FOREVER);
            ((GifDrawable) resource).start();
        }
        preDrawable.setBounds(new Rect(0, 0, preDrawable.getWidth() > 0 ? preDrawable.getWidth() : Size, preDrawable.getHeight() > 0 ? preDrawable.getHeight() : Size));
        preDrawable.invalidateSelf();
    }

    public DrawableTarget(GlidePreDrawable preDrawable, int size) {
        this.preDrawable = preDrawable;
        this.Size = size;
    }

    //加载任务取消并且资源被释放时调用
    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {
        Log.i(TAG, "onLoadCleared: " + placeholder);
        if (placeholder==null)
            return;
        if (preDrawable.getDrawable() != null) {
            return;
        }
        preDrawable.setDrawable(placeholder);
        if (placeholder instanceof GifDrawable) {
            ((GifDrawable) placeholder).setLoopCount(GifDrawable.LOOP_FOREVER);
            ((GifDrawable) placeholder).start();
        }
        preDrawable.invalidateSelf();

    }
    //加载开始时调用
    @Override
    public void onLoadStarted(@Nullable Drawable placeholder) {
        Log.i(TAG, "onLoadCleared: " + placeholder);
        if (placeholder==null)
            return;
        preDrawable.setDrawable(placeholder);

        if (placeholder instanceof GifDrawable) {
            ((GifDrawable) placeholder).setLoopCount(GifDrawable.LOOP_FOREVER);
            ((GifDrawable) placeholder).start();
        }
        preDrawable.invalidateSelf();
    }
    //加载失败是调用
    @Override
    public void onLoadFailed(Exception e,@Nullable Drawable errorDrawable) {
        Log.i(TAG, "onLoadFailed: " + errorDrawable);
        if (errorDrawable==null)
            return;
        preDrawable.setDrawable(errorDrawable);
        if (errorDrawable instanceof GifDrawable) {
            ((GifDrawable) errorDrawable).setLoopCount(GifDrawable.LOOP_FOREVER);
            ((GifDrawable) errorDrawable).start();
        }
        preDrawable.invalidateSelf();
    }

    public Drawable getDrawable(){
        return preDrawable;
    }
}
