package com.utsoft.jan.wtalker.frags.panel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.utsoft.jan.common.app.Fragment;
import com.utsoft.jan.common.tools.UiTool;
import com.utsoft.jan.face.Face;
import com.utsoft.jan.wtalker.R;

import net.qiujuer.genius.ui.Ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/7/11.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.panel
 */
public class PanelFragment extends Fragment {
    //    @BindView(R.id.emoji_recycler)
//    RecyclerView emojiRecycler;
    @BindView(R.id.gallery_recycler)
    RecyclerView galleryRecycler;
    @BindView(R.id.lay_panel_gallery)
    ConstraintLayout layPanelGallery;
    @BindView(R.id.lay_panel_record)
    ConstraintLayout layPanelRecord;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.lay_panel_face)
    LinearLayout layPanelFace;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    //3种布局，


    public PanelFragment() {
        // Required empty public constructor
    }

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

        final int minFaceSize = (int) Ui.dipToPx(getResources(), 48);
        final int screenWidth = UiTool.getScreenWidth(getActivity());
        int count = screenWidth / minFaceSize;

        List<Face.EmojiTab> all = Face.all();
        EmojiPager emojiPager = new EmojiPager(all,count);
        viewpager.setAdapter(emojiPager);
        tablayout.setupWithViewPager(viewpager);

    }

    class EmojiPager extends PagerAdapter {

        List<Face.EmojiTab> lists = new ArrayList<>();
        int count;

        public EmojiPager(List<Face.EmojiTab> all, int count) {
            this.lists = all;
            this.count = count;
        }

        @Override
        public int getCount() {
            return lists.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return Objects.equals(view, o);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.lay_emoji_panel,container,false);
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),this.count));

            recyclerView.setAdapter(new EmojiAdapter());

            container.addView(recyclerView);

            return recyclerView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            return lists.get(position).getName();
        }
    }

    public void showEmoji() {
        layPanelFace.setVisibility(View.VISIBLE);
        layPanelGallery.setVisibility(View.GONE);
        layPanelRecord.setVisibility(View.GONE);
    }

    public void showGallery() {
        layPanelFace.setVisibility(View.GONE);
        layPanelGallery.setVisibility(View.VISIBLE);
        layPanelRecord.setVisibility(View.GONE);
    }

    public void showRecord() {
        layPanelFace.setVisibility(View.GONE);
        layPanelGallery.setVisibility(View.GONE);
        layPanelRecord.setVisibility(View.VISIBLE);
    }


}
