package com.utsoft.jan.wtalker.frags.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.widget.EmptyView;
import com.utsoft.jan.wtalker.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/7/3.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.main
 */
public class ContactFragment extends PresenterFragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.empty)
    EmptyView empty;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter();
    }
}
