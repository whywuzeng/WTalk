package com.utsoft.jan.common.tools;

import android.media.MediaPlayer;

import com.utsoft.jan.widget.recycler.RecyclerAdapter;

import java.io.IOException;

/**
 * Created by Administrator on 2019/8/9.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools
 */
public class RecordPlayHelper<Holder extends RecyclerAdapter.ViewHolder> {

    private Holder mHolder;
    private MediaPlayer mPlayer;
    private RecordPlayCallBack mRecordPlayCallBack;

    public RecordPlayHelper(RecordPlayCallBack callBack) {
        createNewMedia();
        this.mRecordPlayCallBack = callBack;
    }

    public void createNewMedia() {
        mPlayer = new MediaPlayer();
        mPlayer.setLooping(false);
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                RecordPlayCallBack callBack = RecordPlayHelper.this.mRecordPlayCallBack;
                if (callBack != null) {
                    callBack.onPlayError(mHolder);
                }
                return false;
            }
        });
        mPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                RecordPlayCallBack callBack = RecordPlayHelper.this.mRecordPlayCallBack;
                if (callBack != null) {
                    callBack.onPlayComplete(mHolder);
                }
            }
        });
    }

    public void trigger(Holder holder, String path) {


        //先停止先前的播放
        onStop();
        this.mHolder = holder;
        onStart(path);
    }

    private void onStart(String path) {

        if (mPlayer==null)
            return;

        try {
            mPlayer.setDataSource(path);
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            onStop();
        }
        mPlayer.start();
        if (mRecordPlayCallBack!=null)
        {
            mRecordPlayCallBack.onPlayStart(this.mHolder);
        }
    }

    private void onStop() {

        if (mPlayer == null)
            return;

        mPlayer.stop();
        mPlayer.reset();
        Holder holder = this.mHolder;
        this.mHolder = null;
        if (mRecordPlayCallBack!=null)
        {
            mRecordPlayCallBack.onPlayStop(holder);
        }

    }

    public void onDestory(){
        mPlayer.release();
        mPlayer = null;
    }

    public interface RecordPlayCallBack<Holder> {
        void onPlayError(Holder holder);

        void onPlayComplete(Holder holder);

        void onPlayStop(Holder holder);

        void onPlayStart(Holder holder);
    }

}
