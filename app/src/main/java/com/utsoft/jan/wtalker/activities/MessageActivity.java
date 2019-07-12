package com.utsoft.jan.wtalker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.utsoft.jan.common.app.ToolbarActivity;
import com.utsoft.jan.factory.model.Author;
import com.utsoft.jan.wtalker.R;
import com.utsoft.jan.wtalker.frags.message.ChatUserFragment;

/**
 * Created by Administrator on 2019/7/10.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.activities
 */
public class MessageActivity extends ToolbarActivity {

    public static final String EXTRA_ID = "extra_id";
    private String mReceiverId;

    /**
     * 显示人聊天的界面 false
     * @param context
     * @param author
     */
    public static void show(Context context, Author author) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra(EXTRA_ID,author.getId());
        context.startActivity(intent);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected boolean initArgs(Bundle extras) {
        mReceiverId = extras.getString(EXTRA_ID);
        return !TextUtils.isEmpty(mReceiverId);
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        //fragment加入
        ChatUserFragment chatUserFragment = ChatUserFragment.newInstance(mReceiverId);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.lay_container,chatUserFragment);
        ft.commit();
    }
}
