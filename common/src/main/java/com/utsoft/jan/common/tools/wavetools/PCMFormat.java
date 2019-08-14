package com.utsoft.jan.common.tools.wavetools;

import android.media.AudioFormat;

/**
 * Created by Administrator on 2019/8/14.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools.wavetools
 */
public enum PCMFormat {
    PCM_Bit8(1, AudioFormat.ENCODING_PCM_8BIT),
    PCM_Bit16(2, AudioFormat.ENCODING_PCM_16BIT);

    private int bytesPerFrame;
    private int audioFormat;

    PCMFormat(int bytesPerFrame, int audioFormat) {
        this.bytesPerFrame = bytesPerFrame;
        this.audioFormat = audioFormat;
    }

    public int getBytesPerFrame() {
        return bytesPerFrame;
    }

    public int getAudioFormat() {
        return audioFormat;
    }

}
