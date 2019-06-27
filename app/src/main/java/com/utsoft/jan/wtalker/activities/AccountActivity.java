package com.utsoft.jan.wtalker.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.utsoft.jan.common.app.Activity;
import com.utsoft.jan.common.app.Fragment;
import com.utsoft.jan.wtalker.R;
import com.utsoft.jan.wtalker.frags.account.AccountTrigger;
import com.utsoft.jan.wtalker.frags.account.LoginFragment;
import com.utsoft.jan.wtalker.frags.account.RegisterFragment;

import net.qiujuer.genius.ui.compat.UiCompat;

import butterknife.BindView;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.activities
 */
public class AccountActivity extends Activity implements AccountTrigger {
    @BindView(R.id.im_bg)
    ImageView imBg;
    @BindView(R.id.lay_container)
    FrameLayout layContainer;

    //    1.当前curfragment
    //    2.loginfragment
    //    3.registerfragment
    private Fragment curFragment;
    private Fragment loginFragment;
    private Fragment registerFragment;

    public static void show(Context context){
        context.startActivity(new Intent(context,AccountActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_account;
    }

    /**
     * Activity show 显示界面
     */

    @Override
    protected void initWidget() {
        super.initWidget();

        //初始化fragment
        curFragment= loginFragment = new LoginFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.lay_container,loginFragment);
        ft.commit();
        //初始化背景

        Glide.with(this)
                .load(R.drawable.bg_src_tianjin)
                .centerCrop()
                .into(new ViewTarget<ImageView,GlideDrawable>(imBg) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        Drawable current = resource.getCurrent();
                        current = DrawableCompat.wrap(current);
                        current.setColorFilter(UiCompat.getColor(getResources(),R.color.colorAccent),
                                PorterDuff.Mode.SCREEN);
                        this.view.setImageDrawable(current);
                    }
                });
    }

    @Override
    public void triggerView() {
        //切换界面
        if (curFragment!=null){
            if (curFragment == loginFragment){
                registerFragment = new RegisterFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.lay_container,registerFragment);
                ft.commit();
                curFragment = registerFragment;
            }else {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.lay_container,loginFragment);
                ft.commit();
                curFragment = loginFragment;
            }
        }
    }
}
