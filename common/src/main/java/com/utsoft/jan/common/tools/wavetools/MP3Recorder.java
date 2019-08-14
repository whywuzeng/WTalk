package com.utsoft.jan.common.tools.wavetools;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.utsoft.jan.common.tools.wavetools.utils.LameUtil;

import java.io.File;
import java.util.Objects;

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
    private static final int DEFAULT_SAMPLE_RATE = 44100;
    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final PCMFormat DEFAULT_AUDIO_FORMAT = PCMFormat.PCM_Bit16;

    // lame init
    private static final int OUTBITSRATE = 32;
    private static final int QUALITY = 7;
    private static final int LAME_IN_CHANNEL = 1;

    private static final int PER_COUNT = 160;
    private int minBufferSize;
    private AudioRecord mAudioRecord;
    private short[] PCMBuffer;
    private EncodeHandleThread encodeHandleThread;

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
        mAudioRecord = initRecord();
        initLame();

        Objects.requireNonNull(mAudioRecord).startRecording();

    }

    private void initLame() {
        PCMBuffer = new short[minBufferSize];

        LameUtil.init(DEFAULT_SAMPLE_RATE, LAME_IN_CHANNEL, DEFAULT_SAMPLE_RATE, OUTBITSRATE, QUALITY);

        //encodeHandleThread = new EncodeHandleThread();
        encodeHandleThread.start();

        mAudioRecord.setRecordPositionUpdateListener(encodeHandleThread,encodeHandleThread.getHandle());

    }

    private AudioRecord initRecord() {
        int minBufferSize = AudioRecord.getMinBufferSize(DEFAULT_SAMPLE_RATE, DEFAULT_CHANNEL_CONFIG, DEFAULT_AUDIO_FORMAT.getAudioFormat());

        //要 160 整除。做成160一段一段发送。
        int bytesPerFrame = DEFAULT_AUDIO_FORMAT.getBytesPerFrame();
        int byteSize = minBufferSize / bytesPerFrame;
        if (byteSize % PER_COUNT != 0) {
            minBufferSize = minBufferSize + (PER_COUNT - byteSize % PER_COUNT);
        }

        if (minBufferSize != AudioRecord.ERROR_BAD_VALUE) {
            //如何搞
            AudioRecord audioRecord = new AudioRecord(DEFAULT_AUDIO_SOURCE, DEFAULT_SAMPLE_RATE, DEFAULT_CHANNEL_CONFIG, DEFAULT_AUDIO_FORMAT.getAudioFormat(), minBufferSize);

            if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                this.minBufferSize = minBufferSize / 2;
                return audioRecord;
            }
        }
        return null;
    }
}
