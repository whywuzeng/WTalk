package com.utsoft.jan.wtalker.frags.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.factory.data.session.SessionRepository;
import com.utsoft.jan.factory.model.db.Session;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.persenter.session.SessionContract;
import com.utsoft.jan.factory.persenter.session.SessionPresenter;
import com.utsoft.jan.widget.EmptyView;
import com.utsoft.jan.widget.PortraitView;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;
import com.utsoft.jan.wtalker.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/7/2.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.main
 */
public class ActiveFragment extends PresenterFragment<SessionContract.Presenter> implements SessionContract.SessionView<Session> {

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
        mAdapter = new Adapter();
        recycler.setAdapter(mAdapter);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }


    class Adapter extends RecyclerAdapter<Session> {

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
                User other = mData.getMessage().getOther();
                imPortrait.setup(Glide.with(ActiveFragment.this), other.getPortrait());
                txtName.setText(other.getName());
                txtDesc.setText(mData.getLastMsgContent());
                txtModify.setText(mData.getLastModify().toString());
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
