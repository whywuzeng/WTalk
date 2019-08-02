package com.utsoft.jan.common.recycle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by Administrator on 2019/8/2.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.recycle
 */
public class onScrollListenerImpl extends RecyclerView.OnScrollListener {

    private Context Context ;

    public onScrollListenerImpl(Context context) {
        this.Context = context;
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState){
            case SCROLL_STATE_IDLE:
                //滑动停止
                try {
                    if(Context != null)
                    {
                        if (Glide.with(Context).isPaused()){
                            Glide.with(Context).resumeRequests();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SCROLL_STATE_DRAGGING:
                //正在滚动
                try {
                    if(Context != null){
                        if (!Glide.with(Context).isPaused()){
                            Glide.with(Context).pauseRequests();
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }
}
