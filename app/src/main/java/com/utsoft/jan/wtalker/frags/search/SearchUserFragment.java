package com.utsoft.jan.wtalker.frags.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.widget.EmptyView;
import com.utsoft.jan.widget.PortraitView;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;
import com.utsoft.jan.wtalker.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/7/8.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.search
 */
public class SearchUserFragment extends PresenterFragment {

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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

   public static class ViewHolder extends RecyclerAdapter.ViewHolder<UserCard>{
        @BindView(R.id.im_portrait)
        PortraitView imPortrait;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_desc)
        TextView txtDesc;
        @BindView(R.id.im_add)
        ImageView imAdd;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
       }

       @Override
       protected void onBind(UserCard mData) {
           txtName.setText(mData.getName());
           txtDesc.setText(mData.getDesc());
       }
   }
}
