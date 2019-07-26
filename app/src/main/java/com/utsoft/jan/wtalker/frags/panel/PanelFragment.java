package com.utsoft.jan.wtalker.frags.panel;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.utsoft.jan.common.app.Fragment;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;
import com.utsoft.jan.wtalker.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/7/11.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.panel
 */
public class PanelFragment extends Fragment {
    @BindView(R.id.emoji_recycler)
    RecyclerView emojiRecycler;
    @BindView(R.id.lay_panel_face)
    ConstraintLayout layPanelFace;
    @BindView(R.id.gallery_recycler)
    RecyclerView galleryRecycler;
    @BindView(R.id.lay_panel_gallery)
    ConstraintLayout layPanelGallery;
    @BindView(R.id.lay_panel_record)
    ConstraintLayout layPanelRecord;
    //3种布局，

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_panel;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        initEmoji();
    }

    private void initEmoji() {
        emojiRecycler.setLayoutManager(new GridLayoutManager(getActivity(),7));
        emojiRecycler.setAdapter();
    }

//    class EmojiAdapter extends RecyclerAdapter<>{
//
//    }

    public void showEmoji(){
        layPanelFace.setVisibility(View.VISIBLE);
        layPanelGallery.setVisibility(View.GONE);
        layPanelRecord.setVisibility(View.GONE);
    }

    public void showGallery(){
        layPanelFace.setVisibility(View.GONE);
        layPanelGallery.setVisibility(View.VISIBLE);
        layPanelRecord.setVisibility(View.GONE);
    }

    public void showRecord(){
        layPanelFace.setVisibility(View.GONE);
        layPanelGallery.setVisibility(View.GONE);
        layPanelRecord.setVisibility(View.VISIBLE);
    }
}
