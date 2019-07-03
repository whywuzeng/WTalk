package com.utsoft.jan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.utsoft.jan.common.R;
import com.utsoft.jan.widget.convention.PlaceHolderView;

import net.qiujuer.genius.ui.widget.Loading;

/**
 * Created by Administrator on 2019/7/3.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.widget
 */
public class EmptyView extends LinearLayout implements PlaceHolderView {

    private ImageView mEmptyIm;
    private TextView mStatusTxt;
    private Loading mLoading;

    private int[] mTextId = new int[]{0,0,0};
    private int[] mDrawable = new int[]{0,0};
    private View[] mBindView;


    public EmptyView(Context context) {
        super(context);
        init(null,0);
    }

    public EmptyView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public EmptyView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        inflate(getContext(),R.layout.lay_empty,this);
        mEmptyIm = (ImageView) findViewById(R.id.im_empty);
        mStatusTxt = (TextView)findViewById(R.id.txt_empty);
        mLoading = (Loading) findViewById(R.id.loading);

//        load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.EmptyView, defStyleAttr, 0);
       mTextId[0] = a.getInteger(R.styleable.EmptyView_comEmptyText,R.string.prompt_empty);
       mTextId[1] = a.getInteger(R.styleable.EmptyView_comErrorText,R.string.prompt_error);
       mTextId[2] = a.getInteger(R.styleable.EmptyView_comLoadingText,R.string.prompt_loading);

        mDrawable[0] = a.getInteger(R.styleable.EmptyView_comEmptyDrawable,R.drawable.ic_status_empty);
        mDrawable[1] = a.getInteger(R.styleable.EmptyView_comErrorDrawable,R.drawable.ic_error_black_24dp);

        a.recycle();

    }

    /**
     * 控制 外面有数据，layout的显示逻辑
     */
    public void bindView(View... bindView){
        this.mBindView = bindView;
    }

    /**
     * 改变mbinview 显示状态
     */
    private void changeBindViews(int visible){

        if (mBindView == null && mBindView.length ==0)
            return;
            for (View item :
                    mBindView) {
                item.setVisibility(visible);
            }
    }

    @Override
    public void triggerEmpty() {
        mLoading.stop();
        mLoading.setVisibility(GONE);
        mStatusTxt.setText(mTextId[0]);
        mStatusTxt.setVisibility(VISIBLE);
        mEmptyIm.setImageResource(mDrawable[0]);
        mEmptyIm.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        changeBindViews(INVISIBLE);
    }

    @Override
    public void triggerNetError() {
        mLoading.stop();
        mLoading.setVisibility(GONE);
        mStatusTxt.setText(mTextId[1]);
        mStatusTxt.setVisibility(VISIBLE);
        mEmptyIm.setImageResource(mDrawable[1]);
        mEmptyIm.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        changeBindViews(INVISIBLE);
    }

    @Override
    public void triggerError(int strRes) {
        mLoading.stop();
        mLoading.setVisibility(GONE);
        mStatusTxt.setText(strRes);
        mStatusTxt.setVisibility(VISIBLE);
        mEmptyIm.setImageResource(mDrawable[1]);
        mEmptyIm.setVisibility(VISIBLE);
        setVisibility(VISIBLE);
        changeBindViews(INVISIBLE);
    }

    @Override
    public void triggerLoading() {
        mLoading.start();
        mLoading.setVisibility(VISIBLE);
        mStatusTxt.setText(mTextId[2]);
        mStatusTxt.setVisibility(VISIBLE);
        mEmptyIm.setVisibility(INVISIBLE);
        setVisibility(VISIBLE);
        changeBindViews(INVISIBLE);
    }

    @Override
    public void triggerOk() {
        mLoading.stop();
        setVisibility(GONE);
        changeBindViews(VISIBLE);
    }

    @Override
    public void triggerOkOrEmpty(boolean isOk) {
        if (isOk){
            triggerOk();
        }else {
            triggerEmpty();
        }
    }
}
