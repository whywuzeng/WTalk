package com.utsoft.jan.wtalker.frags.panel;

import android.support.annotation.NonNull;
import android.view.View;

import com.utsoft.jan.face.Face;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;
import com.utsoft.jan.wtalker.R;

/**
 * Created by Administrator on 2019/7/31.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.panel
 */
public class EmojiAdapter extends RecyclerAdapter<Face.Emoji> {

    public EmojiAdapter(AdapterListener<Face.Emoji> mAdapterListener) {
        super(mAdapterListener);
    }

    @Override
    protected int getItemViewType(int position, Face.Emoji emoji) {

        return R.layout.face_cell;
    }

    @Override
    protected ViewHolder<Face.Emoji> onCreateViewHolder(@NonNull View root, int viewtype) {
        return new EmojiHolder(root);
    }

}
