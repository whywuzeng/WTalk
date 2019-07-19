package com.utsoft.jan.wtalker.frags.message;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.persenter.message.ChatContract;
import com.utsoft.jan.factory.persenter.message.ChatUserPresenter;
import com.utsoft.jan.widget.PortraitView;
import com.utsoft.jan.wtalker.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.message
 */
public class ChatUserFragment extends ChatFragment<User> implements ChatContract.UserView {

    private static final String TAG = "ChatUserFragment";
    @BindView(R.id.im_portrait)
    PortraitView imPortrait;

    Toolbar toolbar;
    @BindView(R.id.lay_collapsingToolbarLayout)
    CollapsingToolbarLayout layCollapsingToolbarLayout;

    private MenuItem mUserInfoMenuItem;

    public static ChatUserFragment newInstance(String receiverId) {
        Bundle args = new Bundle();
        args.putString(ChatFragment.KEY_RECEIVER_ID, receiverId);
        ChatUserFragment fragment = new ChatUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViewStubHeader(ViewStub viewStubHeader) {
        viewStubHeader.setLayoutResource(R.layout.lay_chat_head_user);
        viewStubHeader.inflate();
        initToolbar();
    }

    protected void initToolbar() {
        toolbar = mRoot.findViewById(R.id.toolbar);
        if (toolbar != null) {
//            Objects.requireNonNull((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_back);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        Toolbar toolbar = this.toolbar;
        toolbar.inflateMenu(R.menu.chat_user);

        mUserInfoMenuItem = toolbar.getMenu().findItem(R.id.chat_person_action);
    }


    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = new ChatUserPresenter(this, mReceiverId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();

    }

    @Override
    public void setPresenter(ChatContract.Presenter presenter) {

    }

    @Override
    public void onInit(User user) {
        layCollapsingToolbarLayout.setTitle(user.getName());
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int onOffset) {
        Log.e(TAG, "onOffsetChanged: " + onOffset);
        int totalScrollRange = appBarLayout.getTotalScrollRange();
        Log.e(TAG, "getTotalScrollRange: " + totalScrollRange);
//        -0 是全部打开  -160 是折叠起来
        View view = imPortrait;
        MenuItem menuItem = this.mUserInfoMenuItem;
        int offsetabs = Math.abs(onOffset);
        Log.e(TAG, "offsetabs: " + offsetabs);
        if (offsetabs == 0) {
            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);
            view.setVisibility(View.VISIBLE);
            menuItem.setVisible(false);
            menuItem.getIcon().setAlpha(0);
        }
        else if (offsetabs >= totalScrollRange) {
            view.setScaleX(0);
            view.setScaleY(0);
            view.setAlpha(0);
            view.setVisibility(View.INVISIBLE);
            menuItem.setVisible(true);
            menuItem.getIcon().setAlpha(255);
        }
        else {
            float rangeI = 1 - (float) offsetabs / totalScrollRange;
            Log.e(TAG, "rangeI: " + rangeI);
            view.setScaleX(rangeI);
            view.setScaleY(rangeI);
            view.setAlpha(rangeI);
            view.setVisibility(View.VISIBLE);

            menuItem.setVisible(true);
            menuItem.getIcon().setAlpha((int) (255 - 255 * rangeI));
        }

    }


    @OnClick(R.id.im_portrait)
    public void onViewClicked() {

    }

}
