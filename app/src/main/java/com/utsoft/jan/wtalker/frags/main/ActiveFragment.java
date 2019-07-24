package com.utsoft.jan.wtalker.frags.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.factory.data.helper.UserHelper;
import com.utsoft.jan.factory.data.session.SessionRepository;
import com.utsoft.jan.factory.model.db.Session;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.net.UploadHelper;
import com.utsoft.jan.factory.persenter.session.SessionContract;
import com.utsoft.jan.factory.persenter.session.SessionPresenter;
import com.utsoft.jan.utils.DateTimeUtil;
import com.utsoft.jan.widget.EmptyView;
import com.utsoft.jan.widget.PortraitView;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;
import com.utsoft.jan.wtalker.R;
import com.utsoft.jan.wtalker.activities.MessageActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/7/2.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.main
 */
public class ActiveFragment extends PresenterFragment<SessionContract.Presenter> implements SessionContract.SessionView<Session>,RecyclerAdapter.AdapterListener<Session> {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.lay_empty)
    EmptyView layEmpty;
    private Adapter mAdapter;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = new SessionPresenter(new SessionRepository(), this);
        mPresenter.start();
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        layEmpty.bindView(recycler);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter(this);
        recycler.setAdapter(mAdapter);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    public void onItemClick(RecyclerAdapter.ViewHolder holder, Session session) {
        String userId = session.getId();
        User user = UserHelper.searchFirstofLocal(userId);
        MessageActivity.show(getActivity(),user);
        UploadHelper.getIntances().dispathcher();
    }

    @Override
    public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Session session) {

    }


    class Adapter extends RecyclerAdapter<Session> {

        public Adapter(AdapterListener<Session> mAdapterListener) {
            super(mAdapterListener);
        }

        public Adapter() {
        }

        @Override
        protected int getItemViewType(int position, Session session) {
            return R.layout.cell_active_session;
        }

        @Override
        protected ViewHolder<Session> onCreateViewHolder(@NonNull View root, int viewtype) {
            return new SessionHolder(root);
        }

        class SessionHolder extends ViewHolder<Session> {
            @BindView(R.id.im_portrait)
            PortraitView imPortrait;
            @BindView(R.id.txt_name)
            TextView txtName;
            @BindView(R.id.txt_desc)
            TextView txtDesc;
            @BindView(R.id.im_bubble)
            TextView txtModify;

            public SessionHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            protected void onBind(Session mData) {
                imPortrait.setup(Glide.with(ActiveFragment.this), mData.getPictureUrl());
                txtName.setText(mData.getName());
                txtDesc.setText(mData.getLastMsgContent());
                String sampleDate = DateTimeUtil.getSampleDate(mData.getLastModify());
                txtModify.setText(sampleDate);
            }
        }
    }


    @Override
    public RecyclerAdapter<Session> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChange() {
        layEmpty.triggerOkOrEmpty(!mAdapter.getListData().isEmpty());
    }
}
