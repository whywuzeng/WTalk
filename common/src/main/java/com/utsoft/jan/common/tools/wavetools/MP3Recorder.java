package com.utsoft.jan.common.tools.wavetools;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.io.File;

/**
 * Created by Administrator on 2019/8/12.
 * <p>
 * by author wz
 * 录制lame 转码为MP3
 * <p>
 * com.utsoft.jan.common.tools.wavetools
 */
public class MP3Recorder {
    private final String mFilePath;
    private boolean mIsRecording;
    public static final int DEFAULT_AUDIO_SOURCE = MediaRecorder.AudioSource.MIC;
    public static final int DEFAULT_SAMPLE_RATE = 44100;
    public static final int DEFAULT_CHANNEL_CONFIG=AudioFormat.CHANNEL_IN_MONO;
    public static final int DEFAULT_AUDIO_FORMAT=AudioFormat.ENCODING_PCM_16BIT;

    //AudioRecorder Default Setting

    public MP3Recorder(File file) {
        this.mFilePath = file.getAbsolutePath();
    }

    public void start(){
        if (mIsRecording)
        {
            return;
        }
        mIsRecording = true;
        initRecord();
    }

    private void initRecord() {
        int minBufferSize = AudioRecord.getMinBufferSize(DEFAULT_SAMPLE_RATE, DEFAULT_CHANNEL_CONFIG, DEFAULT_AUDIO_FORMAT);

    }
}
