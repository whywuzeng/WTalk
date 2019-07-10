package com.utsoft.jan.wtalker.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.utsoft.jan.common.app.PresenterToolbarActivity;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.persenter.contact.PersonalContract;
import com.utsoft.jan.factory.persenter.contact.PersonalPresenter;
import com.utsoft.jan.widget.PortraitView;
import com.utsoft.jan.wtalker.R;

import net.qiujuer.genius.res.Resource;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/7/9.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.activities
 */
public class PersonalToolbarActivity extends PresenterToolbarActivity<PersonalContract.Presenter>
        implements PersonalContract.View {

    @BindView(R.id.im_header)
    ImageView imHeader;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.im_portrait)
    PortraitView imPortrait;
    @BindView(R.id.txt_follow)
    TextView txtFollow;
    @BindView(R.id.txt_following)
    TextView txtFollowing;
    @BindView(R.id.txt_desc)
    TextView txtDesc;
    @BindView(R.id.btn_say_hello)
    Button btnSayHello;

    public static final String EXTRA_USERID = "EXTRA_USERID";
    private String mUserId;
    private MenuItem followItem;
    private boolean mIsFollowUser =false;

    public static void show(Context context,String userId){
        Intent intent = new Intent(context, PersonalToolbarActivity.class);
        intent.putExtra(EXTRA_USERID,userId);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle extras) {
        mUserId = extras.getString(EXTRA_USERID);
        return !TextUtils.isEmpty(mUserId);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new PersonalPresenter(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.personal,menu);

        followItem = menu.findItem(R.id.action_follow);
        changeFollowItemStatus();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_follow){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFollowItemStatus() {
        if (followItem == null)
            return;
        Drawable drawableRes = mIsFollowUser?getResources().getDrawable(R.drawable.ic_favorite):getResources().getDrawable(R.drawable.ic_favorite_border);
        drawableRes = DrawableCompat.wrap(drawableRes);
        DrawableCompat.setTint(drawableRes,Resource.Color.WHITE);
        followItem.setIcon(drawableRes);
    }

    @Override
    public String getUserID() {
        return mUserId;
    }

    @Override
    public void onLoadDone(User user) {
        hideDialogLoading();
        if (user==null)
            return;
        txtName.setText(user.getName());
        imPortrait.setup(Glide.with(this),user.getPortrait());
        txtFollow.setText(String.format(getString(R.string.label_follows),user.getFollows()));
        txtFollowing.setText(String.format(getString(R.string.label_following),user.getFollowing()));
        txtDesc.setText(user.getDesc());
    }

    @Override
    public void allowSayHello(boolean isAllow) {
        btnSayHello.setEnabled(isAllow);
    }

    @Override
    public void setFollowStatus(boolean isFollow) {
        mIsFollowUser = isFollow;
        changeFollowItemStatus();
    }

    @OnClick(R.id.btn_say_hello)
    public void onViewClicked() {
        User user = mPresenter.getUserPersonal();
        if (user == null)
            return;
        MessageActivity.show(this,user);
    }
}
