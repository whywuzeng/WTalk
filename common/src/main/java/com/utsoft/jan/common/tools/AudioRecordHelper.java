package com.utsoft.jan.common.tools;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;

import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.utils.PCM2WAVUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2019/8/6.
 * <p>
 * by author wz
 * AudioRecord 的用法
 *
 * Lame java用法
 * <p>
 * com.utsoft.jan.common.tools
 */
public class AudioRecordHelper {

    private static final int[] SAMPLE_RATES = new int[]{44100, 22050, 11025, 8000};
    private final File tmpFile;
    private final RecordCallback mCallBack;
    private boolean isDone;
    private boolean isCancel;
    private int minShortBufferSize;


    /**
     * @param tmpFile  临时存储文件
     * @param callback 录制回调
     */
    public AudioRecordHelper(File tmpFile, RecordCallback callback) {
        this.tmpFile = tmpFile;
        this.mCallBack = callback;
    }

    /**
     * 初始化record
     *
     * @return
     */
    private AudioRecord initRecord() {
        //setAudioSource 从麦克风 采集源

        for (int rate : SAMPLE_RATES) {

            for (short format : new short[]{AudioFormat.ENCODING_PCM_16BIT, AudioFormat.ENCODING_PCM_8BIT}) {
                for (short channel : new short[]{AudioFormat.CHANNEL_IN_STEREO, AudioFormat.CHANNEL_IN_MONO}) {
                    int minBufferSize = AudioRecord.getMinBufferSize(rate, channel, format);
                    if (minBufferSize != AudioRecord.ERROR_BAD_VALUE) {
                        AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, rate, channel, format, minBufferSize);
                        if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                            minShortBufferSize = minBufferSize;
                            return audioRecord;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 初始化缓存文件，每次都只用这一个文件
     */
    File initTmpFile() {
        if (tmpFile.exists()) {
            tmpFile.delete();
        }

        try {
            if (tmpFile.createNewFile())
                return tmpFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 进行异步录制
     */
    public void recordAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                record();
            }
        }).start();
    }

    /**
     * 开始录制
     * 录制完后 返回tmpFile
     */
    private File record() {
        isDone = false;
        isCancel = false;
        int minShortSize = minShortBufferSize;

        RecordCallback callback = mCallBack;

        File file = initTmpFile();

        final File tmpOutFile = createFile(System.currentTimeMillis() + ".wav");

        AudioRecord audioRecord = initRecord();
        if (file == null || audioRecord == null) {
            Application.showToast("record or tmpFile is null");
            return null;
        }
        audioRecord.startRecording();
        callback.onStart();

        long startTime = SystemClock.uptimeMillis();
        long endTime;

        //获得file  outputStream流。
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] audioData = new byte[minShortSize];

        //while 循环 读取recode。read（）
        while (true) {
            int read = audioRecord.read(audioData, 0, minShortSize);
            if (read != AudioRecord.ERROR_INVALID_OPERATION ) {

                if (outputStream != null) {
                    try {
                        outputStream.write(audioData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            endTime = SystemClock.uptimeMillis();
            callback.onProgress(endTime - startTime);
            if (isDone) {
                break;
            }
        }

        if (tmpOutFile!=null)
            PCM2WAVUtils.pcmToWave(tmpFile.getAbsolutePath(),tmpOutFile.getAbsolutePath(),audioRecord.getSampleRate(),audioRecord.getChannelCount(),minShortSize);

        if (!isCancel) {
            callback.onRecordEnd(tmpOutFile, endTime - startTime);
        }

        audioRecord.stop();
        audioRecord.release();

        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //把读到 short[] buffer 放到 解码器里处理

        //返回大小 和 time

        return tmpOutFile;
    }

    private File createFile(String name) {
        //tmp临时缓存
        String dirPath = Environment.getExternalStorageDirectory().getPath() + "/AudioRecordTmp/";
        File file = new File(dirPath);

        if (!file.exists()) {
            file.mkdirs();
        }

        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (File file1 : files) {
                file1.delete();
            }
        }

        String filePath = dirPath + name;
        File objFile = new File(filePath);
        if (!objFile.exists()) {
            try {
                objFile.createNewFile();
                return objFile;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return objFile;
    }

    public void onStop(boolean cancel) {
        isDone = true;
        isCancel = cancel;

    }

   public interface RecordCallback {
        void onStart();

        void onProgress(long time);

        //录制取消不会回调此方法
        void onRecordEnd(File file, long time);
    }
}
