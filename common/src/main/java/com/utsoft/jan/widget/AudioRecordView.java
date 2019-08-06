package com.utsoft.jan.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.utsoft.jan.common.R;

import net.qiujuer.genius.ui.widget.FloatActionButton;

/**
 * Created by Administrator on 2019/8/6.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.widget
 */
public class AudioRecordView extends FrameLayout implements View.OnTouchListener {

    public static final int END_SUCESS = 1;
    public static final int END_CANCEL = 0;
    private static final float MIN_ALPHA = 0.4f;
    private ImageView ImDelete;
    private FloatActionButton mBtnRecord;
    private boolean mRecording = false;

    private CallBack mCallBack;

    public AudioRecordView( Context context) {
        super(context);
        init();
    }

    public AudioRecordView( Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AudioRecordView( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        //整体布局 View inflate
        inflate(getContext(),R.layout.lay_record_view,this);
        ImDelete = (ImageView) findViewById(R.id.im_delete);
        mBtnRecord = (FloatActionButton) findViewById(R.id.btn_record);
        //监听 recordButton
        mBtnRecord.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked())
        {
            case MotionEvent.ACTION_DOWN:
                onStart();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                onStop(END_SUCESS);
                break;
            case MotionEvent.ACTION_CANCEL:
                onStop(END_CANCEL);
                break;

        }
        return false;
    }

    private void onStop(int type) {
        mRecording = false;
        ImDelete.setVisibility(mRecording ? VISIBLE : GONE);
        turnRecord();

        CallBack callBack = mCallBack;
        if (callBack!=null)
        {
            callBack.requestRecordEnd(type);
        }
    }

    private void onStart() {
        mRecording = true;
        turnRecord();
        ImDelete.setVisibility(mRecording ? VISIBLE : GONE);

        CallBack callBack = mCallBack;
        if (callBack!=null)
        {
            callBack.requestRecordStart();
        }
    }

    private void turnRecord() {
        if (mRecording) {
            ImDelete.animate()
                    .alpha(MIN_ALPHA)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(160)
                    .setInterpolator(new AnticipateOvershootInterpolator())
                    .start();
        }
        else {
            ImDelete.animate()
                    .alpha(0)
                    .scaleX(1)
                    .scaleY(1)
                    .setDuration(320)
                    .setInterpolator(new AnticipateOvershootInterpolator())
                    .start();
        }
    }

    public void setup(CallBack callBack) {
        mCallBack = callBack;
    }

    public interface CallBack{
        void requestRecordStart();
        void requestRecordEnd(int type);
    }
}
