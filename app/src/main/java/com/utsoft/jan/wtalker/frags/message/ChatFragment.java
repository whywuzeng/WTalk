package com.utsoft.jan.wtalker.frags.message;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.utsoft.jan.common.app.PresenterFragment;
import com.utsoft.jan.face.Face;
import com.utsoft.jan.factory.model.db.Message;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.persenter.message.ChatContract;
import com.utsoft.jan.factory.persistence.Account;
import com.utsoft.jan.widget.MessageLayout;
import com.utsoft.jan.widget.PortraitView;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;
import com.utsoft.jan.wtalker.R;
import com.utsoft.jan.wtalker.frags.panel.PanelFragment;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.Util;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/7/10.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.frags.message
 */
public abstract class ChatFragment<InitModel> extends PresenterFragment<ChatContract.Presenter>
        implements ChatContract.View<InitModel>, AppBarLayout.OnOffsetChangedListener,PanelFragment.PanelCallback {

    ViewStub viewStubHeader;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.im_submit)
    ImageView imSubmit;
    @BindView(R.id.lay_content)
    MessageLayout layContent;

    public static final String KEY_RECEIVER_ID = "key_receiver_id";
    @BindView(R.id.lay_coordinator)
    CoordinatorLayout layCoordinator;
    @BindView(R.id.im_emoji)
    ImageView imEmoji;
    @BindView(R.id.im_record)
    ImageView imRecord;

    private RecyclerAdapter<Message> mAdapter;
    protected String mReceiverId;

    private AirPanel.Boss mPanelBoss;

    private PanelFragment mPanelFragment;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_common;
    }

    @Override
    protected void initArgs(Bundle arguments) {
        super.initArgs(arguments);
        mReceiverId = arguments.getString(KEY_RECEIVER_ID);
    }

    @Override
    protected void initWidget() {
        viewStubHeader = mRoot.findViewById(R.id.view_stub_header);
        initViewStubHeader(viewStubHeader);
        super.initWidget();

//        mPanelBoss初始化
        mPanelBoss = mRoot.findViewById(R.id.lay_content);
        mPanelBoss.setup(new AirPanel.PanelListener() {
            @Override
            public void requestHideSoftKeyboard() {
                Util.hideKeyboard(editContent);
            }
        });
        mPanelBoss.setOnStateChangedListener(new AirPanel.OnStateChangedListener() {
            @Override
            public void onPanelStateChanged(boolean isOpen) {
                if (isOpen)
                {
                    appbar.setExpanded(false);
                }
            }

            @Override
            public void onSoftKeyboardStateChanged(boolean isOpen) {
                if (isOpen)
                {
                    appbar.setExpanded(false);
                }
            }
        });
//        mPanelBoss stateChangeListener
        //面板  和 键盘的改变都可以 监听到，可以影响，appbarlayout的缩放.

        //mPanelFragment 初始化
        mPanelFragment = (PanelFragment) getChildFragmentManager().findFragmentById(R.id.frag_panel);
        Objects.requireNonNull(mPanelFragment).setPanelCallback(this);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter();
        recycler.setAdapter(mAdapter);
        initEidtContent();
        appbar.addOnOffsetChangedListener(this);
    }

    private void initEidtContent() {
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = s.toString().trim();
                boolean needSending = !TextUtils.isEmpty(content);
                //设置状态 改变对应icon
                imSubmit.setActivated(needSending);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }

    @Override
    public RecyclerAdapter<Message> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChange() {

    }

    protected abstract void initViewStubHeader(ViewStub viewStubHeader);


    @OnClick(R.id.im_emoji)
    public void onImEmojiClicked() {
        //打开底部界面
        //显示 face view
        mPanelFragment.showEmoji();
        mPanelBoss.openPanel();

    }

    @OnClick(R.id.im_record)
    public void onImRecordClicked() {
        //打开底部界面
        //显示 Record view
        mPanelFragment.showRecord();
        mPanelBoss.openPanel();
    }


    class Adapter extends RecyclerAdapter<Message> {

        @Override
        protected int getItemViewType(int position, Message message) {
            //如果发送者是自己，在右边
            boolean isRight = Objects.equals(message.getSender().getId(), Account.getUser().getId());
            switch (message.getType()) {
                case Message.TYPE_STR:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
                case Message.TYPE_PIC:
                    return isRight ? R.layout.cell_chat_pic_right : R.layout.cell_chat_pic_left;
                case Message.TYPE_AUDIO:
                    return isRight ? R.layout.cell_chat_audio_right : R.layout.cell_chat_audio_left;
                default:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
            }
        }

        @Override
        protected ViewHolder<Message> onCreateViewHolder(@NonNull View root, int viewtype) {
            switch (viewtype) {
                case R.layout.cell_chat_text_right:
                case R.layout.cell_chat_text_left:
                    return new TextHolder(root);
                case R.layout.cell_chat_pic_left:
                case R.layout.cell_chat_pic_right:
                    return new PicHolder(root);
                case R.layout.cell_chat_audio_left:
                case R.layout.cell_chat_audio_right:
                    return new AudioHolder(root);
            }
            return new TextHolder(root);
        }

    }

    abstract class BaseHolder extends RecyclerAdapter.ViewHolder<Message> {

        @BindView(R.id.im_portrait)
        PortraitView imPortrait;

        @Nullable
        @BindView(R.id.loading)
        Loading loading;

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void onBind(Message mData) {
            User sender = mData.getSender();
            //本地数据库
            sender.load();

            imPortrait.setup(Glide.with(getActivity()), sender);
        }

        @OnClick(R.id.im_portrait)
        void onViewClick() {
            //头像点击
            //重新发送消息
            if (mData.getStatus() == Message.STATUS_FAILED) {

            }
        }
    }

    //文字的holder
    class TextHolder extends BaseHolder {

        @BindView(R.id.txt_content)
        TextView txtContent;

        public TextHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Message mData) {
            super.onBind(mData);
            SpannableString spannableString = new SpannableString(mData.getContent());
            //解析code 表情
            SpannableString decode = Face.decode(spannableString, txtContent, Ui.dipToPx(getResources(),20));

            txtContent.setText(decode);
        }
    }

    @OnClick(R.id.im_submit)
    void onViewClick() {
        if (imSubmit.isActivated()) {
            String content = editContent.getText().toString();
            editContent.setText("");
            mPresenter.pushText(content);
        }
        else {
            //more 操作
            mPanelFragment.showGallery();
            mPanelBoss.openPanel();
        }
    }

    @Override
    public EditText getInputEditText() {
        return editContent;
    }

    @Override
    public void onSendGallery(String[] paths) {
        mPresenter.pushImage(paths);
    }

    @Override
    public void onSendAudio(File file, long time) {
        mPresenter.pushAudio(file,time);
    }

    class PicHolder extends BaseHolder {
        @BindView(R.id.im_msg_picture)
        ImageView imMsgPic;

        public PicHolder(View root) {
            super(root);
        }

        @Override
        protected void onBind(Message mData) {
            super.onBind(mData);
            String content = mData.getContent();

            Glide.with(getContext())
                    .load(content)
                    .asBitmap()
                    .format(DecodeFormat.PREFER_RGB_565)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imMsgPic);
        }
    }

    class AudioHolder extends BaseHolder{
        @BindView(R.id.txt_audio)
        TextView txtAudio;

        public AudioHolder(View root) {
            super(root);
        }

        @Override
        protected void onBind(Message mData) {
            super.onBind(mData);
            String content = mData.getContent();

            txtAudio.setText(mData.getAttach());

            //播放音频
            txtAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


}
