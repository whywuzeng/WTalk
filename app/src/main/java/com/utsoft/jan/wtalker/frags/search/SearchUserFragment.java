package com.utsoft.jan.wtalker.frags.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.persenter.contact.FollowContract;
import com.utsoft.jan.factory.persenter.contact.FollowPresenter;
import com.utsoft.jan.factory.persenter.search.SearchContract;
import com.utsoft.jan.factory.persenter.search.SearchUserPresenter;
import com.utsoft.jan.widget.EmptyView;
import com.utsoft.jan.widget.PortraitView;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;
import com.utsoft.jan.wtalker.R;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;
import net.qiujuer.genius.ui.drawable.LoadingDrawable;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/7/8.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.search
 */
public class SearchUserFragment extends PresenterFragment<SearchContract.Presenter> implements SearchContract.UserView {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.empty)
    EmptyView empty;

    private RecyclerAdapter<UserCard> mAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter = new RecyclerAdapter<UserCard>() {
            @Override
            protected int getItemViewType(int position, UserCard card) {
                return R.layout.cell_search_list;
            }

            @Override
            protected ViewHolder<UserCard> onCreateViewHolder(@NonNull View root, int viewtype) {
                return new SearchUserFragment.ViewHolder(root);
            }
        });

        empty.bindView(recycler);
        setHolderView(empty);
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = new SearchUserPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        search("");
    }

    /**
     * 搜素
     *
     * @param text 搜素的文本
     */
    public void search(String text) {
        mPresenter.search(text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void searchDone(List<UserCard> userCards) {
        mAdapter.replace(userCards);
        holderView.triggerOkOrEmpty(mAdapter.getItemCount()>0);
    }

    public class ViewHolder extends RecyclerAdapter.ViewHolder<UserCard>
        implements FollowContract.View {

        @BindView(R.id.im_portrait)
        PortraitView imPortrait;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_desc)
        TextView txtDesc;
        @BindView(R.id.im_add)
        ImageView imAdd;

        private FollowContract.Presenter mPresenter;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           new FollowPresenter(this);
       }

       @OnClick(R.id.im_add)
       void onFollowClick(){
           mPresenter.follow(mData.getId());
       }

       @OnClick(R.id.im_portrait)
       void onPortraitClick(){
           //显示信息
       }

       @Override
       protected void onBind(UserCard mData) {
           imPortrait.setup(Glide.with(SearchUserFragment.this),mData.getPortrait());
           txtName.setText(mData.getName());
           txtDesc.setText(mData.getDesc());
           imAdd.setEnabled(!mData.isFollow());
       }

        @Override
        public void followDone(UserCard card) {
            if (imAdd.getDrawable() instanceof LoadingDrawable) {
                LoadingDrawable drawable = (LoadingDrawable) imAdd.getDrawable();
                drawable.setProgress(1);
                drawable.stop();
            }
        }

        @Override
        public void showLoading() {
            int minSize = (int)Ui.dipToPx(getResources(), 22);
            int maxSize = (int)Ui.dipToPx(getResources(), 30);

            LoadingCircleDrawable loadingDrawable = new LoadingCircleDrawable(minSize,maxSize);
            loadingDrawable.setBackgroundColor(0);

            int[] color = {UiCompat.getColor(getResources(), R.color.white_alpha_208)};
            loadingDrawable.setForegroundColor(color);

            imAdd.setImageDrawable(loadingDrawable);

            loadingDrawable.start();
        }

        @Override
        public void showErrorMsg(int strId) {
            Application.showToast(strId);
            if (imAdd.getDrawable() instanceof LoadingDrawable) {
                LoadingDrawable drawable = (LoadingDrawable) imAdd.getDrawable();
                drawable.setProgress(1);
                drawable.stop();
            }
        }

        @Override
        public void setPresenter(FollowContract.Presenter presenter) {
            mPresenter = presenter;
        }
    }
}
