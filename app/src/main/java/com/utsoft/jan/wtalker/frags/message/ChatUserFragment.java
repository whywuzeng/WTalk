package com.utsoft.jan.wtalker.frags.message;

import android.os.Bundle;
import android.view.ViewStub;

import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.persenter.message.ChatContract;
import com.utsoft.jan.factory.persenter.message.ChatUserPresenter;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.message
 */
public class ChatUserFragment extends ChatFragment<User> implements ChatContract.UserView {

    public static ChatUserFragment newInstance(String receiverId) {
        Bundle args = new Bundle();
        args.putString(ChatFragment.KEY_RECEIVER_ID,receiverId);
        ChatUserFragment fragment = new ChatUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViewStubHeader(ViewStub viewStubHeader) {

    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = new ChatUserPresenter(this,mReceiverId);
    }


    @Override
    public void setPresenter(ChatContract.Presenter presenter) {

    }

    @Override
    public void onInit(User user) {

    }
}
