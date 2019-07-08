package com.utsoft.jan.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utsoft.jan.widget.convention.PlaceHolderView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.app
 */
public abstract class Fragment extends android.support.v4.app.Fragment {
    protected View mRoot;
    protected Unbinder unbinder;
    private boolean mIsFirstInitData = true;

    public PlaceHolderView getHolderView() {
        return holderView;
    }

    public void setHolderView(PlaceHolderView holderView) {
        this.holderView = holderView;
    }

    protected PlaceHolderView holderView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initArgs();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRoot == null) {
            int resId =  getContentLayoutId();
            mRoot = inflater.inflate(resId, container, false);
            initWidget();
        }
        else {
            if (mRoot.getParent()!=null)
            {
                ((ViewGroup)mRoot.getParent()).removeView(mRoot);
            }
        }

        return mRoot;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mIsFirstInitData){
            mIsFirstInitData = false;
            initFirit();
        }
        initData();
    }

    protected void initData() {

    }

    protected void initFirit() {

    }

    protected void initWidget() {
        unbinder = ButterKnife.bind(this, mRoot);
    }

    protected abstract int getContentLayoutId();

    protected void initArgs() {

    }

    public boolean onBackPressed() {

        return false;
    }
}
