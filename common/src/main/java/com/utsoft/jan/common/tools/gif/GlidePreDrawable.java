package com.utsoft.jan.common.tools.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifOptions;
import pl.droidsonroids.gif.InputSource;

/**
 * Created by Administrator on 2019/8/14.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools.gif
 */
public class GlidePreDrawable extends GifDrawable implements InvalidateDrawable2 {

    private static final String TAG = "PreDrawable";
    private CallBack mCallBack = new CallBack();

    private List<RefreshListener> mRefreshListeners = new ArrayList<>();


    {
        setCallback(mCallBack);
    }

    public GlidePreDrawable(Resources res, int id) throws Resources.NotFoundException, IOException {
        super(res, id);
    }

    public GlidePreDrawable(AssetManager assets, String assetName) throws IOException {
        super(assets, assetName);
    }

    public GlidePreDrawable(String filePath) throws IOException {
        super(filePath);
    }

    public GlidePreDrawable(File file) throws IOException {
        super(file);
    }

    public GlidePreDrawable(InputStream stream) throws IOException {
        super(stream);
    }

    public GlidePreDrawable(AssetFileDescriptor afd) throws IOException {
        super(afd);
    }

    public GlidePreDrawable(FileDescriptor fd) throws IOException {
        super(fd);
    }

    public GlidePreDrawable(byte[] bytes) throws IOException {
        super(bytes);
    }

    public GlidePreDrawable(ByteBuffer buffer) throws IOException {
        super(buffer);
    }

    public GlidePreDrawable(ContentResolver resolver, Uri uri) throws IOException {
        super(resolver, uri);
    }

    protected GlidePreDrawable(InputSource inputSource, GifDrawable oldDrawable, ScheduledThreadPoolExecutor executor, boolean isRenderingTriggeredOnDraw, GifOptions options) throws IOException {
        super(inputSource, oldDrawable, executor, isRenderingTriggeredOnDraw, options);
    }


    @Override
    public void addRefreshListener(RefreshListener listener) {
        mRefreshListeners.add(listener);
    }

    @Override
    public void removeRefreshListener(RefreshListener listener) {
        mRefreshListeners.remove(listener);
    }

    class CallBack implements Drawable.Callback{


        @Override
        public void invalidateDrawable( Drawable who) {
            for (RefreshListener listener : mRefreshListeners) {
                listener.onRefresh();
            }
        }

        @Override
        public void scheduleDrawable( Drawable who,  Runnable what, long when) {

        }

        @Override
        public void unscheduleDrawable( Drawable who,  Runnable what) {

        }
    }
}
