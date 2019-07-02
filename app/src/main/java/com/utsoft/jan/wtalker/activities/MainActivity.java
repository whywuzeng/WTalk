package com.utsoft.jan.wtalker.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.utsoft.jan.common.app.Activity;
import com.utsoft.jan.factory.persistence.Account;
import com.utsoft.jan.widget.PortraitView;
import com.utsoft.jan.wtalker.R;
import com.utsoft.jan.wtalker.frags.main.ActiveFragment;
import com.utsoft.jan.wtalker.helper.NavHelper;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends Activity implements NavHelper.TabChangeListener<Integer>, BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.im_portrait)
    PortraitView imPortrait;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_action)
    FloatActionButton btnAction;

    private NavHelper<Integer> mHelper;

    @Override
    protected void initWidget() {
        super.initWidget();
        mHelper = new NavHelper<>(getSupportFragmentManager(), R.id.lay_container, this, this);
        mHelper.add(R.id.action_home, new NavHelper.Tab<Integer>(R.string.action_home, ActiveFragment.class));
        mHelper.add(R.id.action_group, new NavHelper.Tab<Integer>(R.string.action_group, ActiveFragment.class));
        mHelper.add(R.id.action_contact, new NavHelper.Tab<Integer>(R.string.action_contact, ActiveFragment.class));

        navigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(appbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable>
                            glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        Menu menu = navigation.getMenu();
        menu.performIdentifierAction(R.id.action_home, 0);

        imPortrait.setup(Glide.with(this), Account.getUser().getPortrait());
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean initArgs(Bundle extras) {
        if (Account.isComplete()) {
            return super.initArgs(extras);
        }
        else {
            UserActivity.show(this);
            return false;
        }
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @OnClick(R.id.im_portrait)
    public void onClick() {
        //跳转到个人界面
    }

    //切换回调
    @Override
    public void onTabChangeClick(NavHelper.Tab<Integer> newtab, NavHelper.Tab<Integer> oldtab) {
        tvTitle.setText(newtab.extra);

        float transY = 0;
        float rotation = 0;
        if (newtab.extra == R.string.action_home) {
            transY = Ui.dipToPx(getResources(), 76);
        }
        else if (Objects.equals(newtab.extra , R.string.action_group)) {
            rotation = -360;
        }
        else if (Objects.equals(newtab.extra , R.string.action_contact)) {
            rotation = 360;
        }
        btnAction.setImageResource(R.drawable.ic_group);

        btnAction.animate()
                .translationY(transY)
                .rotation(rotation)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(500)
                .start();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return mHelper.menuClick(menuItem.getItemId());
    }

}
