package com.utsoft.jan.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.bumptech.glide.RequestManager;
import com.utsoft.jan.common.R;
import com.utsoft.jan.factory.model.Author;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/7/2.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.widget
 */
public class PortraitView extends CircleImageView {

    public PortraitView(Context context) {
        super(context);
    }

    public PortraitView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PortraitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setup(RequestManager manager, Author author){
        if (author ==null)
            return;
        setup(manager,R.drawable.default_portrait,author.getPortrait());
    }

    public void setup(RequestManager manager,String url){
        setup(manager,R.drawable.default_portrait,url);
    }

    public void setup(RequestManager manager,int resourceId,String url){
        manager.load(url)
                .placeholder(resourceId)
                .centerCrop()
                .dontAnimate()
                .into(this);
    }
}
