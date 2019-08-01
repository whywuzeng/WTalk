package com.utsoft.jan.wtalker.frags.panel;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.utsoft.jan.face.Face;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;
import com.utsoft.jan.wtalker.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/7/31.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.panel
 */
public class EmojiHolder extends RecyclerAdapter.ViewHolder<Face.Emoji> {

    @BindView(R.id.im_face)
    ImageView imFace;

    public EmojiHolder(@NonNull View itemView) {
        super(itemView);

    }

    @Override
    protected void onBind(Face.Emoji mData) {
        if (mData != null &&(mData.getPreview() instanceof Integer
                || mData.getPreview() instanceof String))
        {
            Glide.with(itemView.getContext())
                    .load(mData.getSource())
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .placeholder(R.drawable.default_face)
                    .into(imFace);
        }
    }
}
