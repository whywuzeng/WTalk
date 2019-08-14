package com.utsoft.jan.common.tools.wavetools;

import android.media.AudioRecord;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.io.File;

/**
 * Created by Administrator on 2019/8/14.
 * <p>
 * by author wz
 * 要 转码MP3
 * <p>
 * com.utsoft.jan.common.tools.wavetools
 */
public class EncodeHandleThread extends HandlerThread implements AudioRecord.OnRecordPositionUpdateListener {

    private  EncodeHandle mHandle;
    private final File file;
    private final byte[] mp3Buffer;

    public EncodeHandleThread(String filePath, int bufferSize) {
        super("DataEncodeThread");
        file = new File(filePath);
        mp3Buffer = new byte[(int) (7200 + (bufferSize * 2 * 1.25))];
    }

    @Override
    public synchronized void start() {
        super.start();
        mHandle = new EncodeHandle(getLooper());
    }

    private static class EncodeHandle extends Handler {

        public EncodeHandle(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //处理

        }
    }


    public Handler getHandle() {
        return mHandle;
    }

    @Override
    public void onMarkerReached(AudioRecord recorder) {

    }

    @Override
    public void onPeriodicNotification(AudioRecord recorder) {

    }
}
