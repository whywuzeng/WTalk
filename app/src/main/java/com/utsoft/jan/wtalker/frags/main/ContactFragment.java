package com.utsoft.jan.wtalker.frags.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.persenter.contact.ContactContract;
import com.utsoft.jan.factory.persenter.contact.ContactPresenter;
import com.utsoft.jan.widget.EmptyView;
import com.utsoft.jan.widget.PortraitView;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;
import com.utsoft.jan.wtalker.R;
import com.utsoft.jan.wtalker.activities.PersonalToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/7/3.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.main
 */
public class ContactFragment extends PresenterFragment<ContactContract.Presenter> implements ContactContract.View {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.empty)
    EmptyView empty;

    private RecyclerAdapter<User> mAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(mAdapter = new RecyclerAdapter<User>() {
            @Override
            protected int getItemViewType(int position, User user) {
                return R.layout.cell_contact_list;
            }

            @Override
            protected ViewHolder<User> onCreateViewHolder(@NonNull View root, int viewtype) {
                return new ContactFragment.ViewHolder(root);
            }
        });

        empty.bindView(recycler);
        setHolderView(empty);
    }

    @Override
    protected void initFirit() {
        super.initFirit();
        //开始加载
        mPresenter.start();
    }

    @Override
    public RecyclerAdapter<User> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChange() {
        holderView.triggerOkOrEmpty(mAdapter.getItemCount() > 0);
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = new ContactPresenter(this);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<User> {
        @BindView(R.id.im_portrait)
        PortraitView imPortrait;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_desc)
        TextView txtDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(User mData) {
            imPortrait.setup(Glide.with(ContactFragment.this), mData.getPortrait());
            txtName.setText(mData.getName());
            txtDesc.setText(mData.getDesc());
        }

        @OnClick(R.id.im_portrait)
        void onPortraitClick() {
            //显示信息
            PersonalToolbarActivity.show(getActivity(),mData.getId());
        }
    }
}
