package com.utsoft.jan.wtalker.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.common.app.PresenterToolbarActivity;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.persenter.user.UserContract;
import com.utsoft.jan.factory.persenter.user.UserPresenter;
import com.utsoft.jan.widget.PortraitView;
import com.utsoft.jan.wtalker.R;
import com.utsoft.jan.wtalker.frags.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.genius.ui.widget.Button;
import net.qiujuer.genius.ui.widget.Loading;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/6/29.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.activities
 */
public class UserActivity extends PresenterToolbarActivity<UserContract.Presenter> implements UserContract.View  {
    @BindView(R.id.im_portrait)
    PortraitView imPortrait;
    @BindView(R.id.im_sex)
    ImageView imSex;
    @BindView(R.id.edit_desc)
    EditText editDesc;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.loading)
    Loading loading;

    private boolean isSex = false;
    //头像的路径
    private String mPortrait;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_user;
    }

    public static void show(Context context) {
        context.startActivity(new Intent(context, UserActivity.class));
    }

    @OnClick(R.id.im_portrait)
    public void onImPortraitClicked() {
        new GalleryFragment().setSelectListener(new GalleryFragment.OnSelectListener() {
            @Override
            public void selectedImage(String path) {
                UCrop.Options options = new UCrop.Options();
                //设置图片处理格式
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                //设置图片处理的精度
                options.setCompressionQuality(96);

                File portraitTmpFile = Application.getPortraitTmpFile();

                UCrop.of(Uri.fromFile(new File(path)),Uri.fromFile(portraitTmpFile))
                        .withAspectRatio(1,1)
                        .withMaxResultSize(520,520)
                        .withOptions(options)
                        .start(UserActivity.this);
            }
        }).show(getSupportFragmentManager(), GalleryFragment.class.getName());
    }

    @OnClick(R.id.im_sex)
    public void onImSexClicked() {
        isSex = !isSex;
        Drawable drawable = imSex.getDrawable();
        int drawableRes = isSex ? R.drawable.ic_sex_woman : R.drawable.ic_sex_man;
        imSex.setImageDrawable(getResources().getDrawable(drawableRes));
        drawable.setLevel(isSex ? 1 : 0);

    }

    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {
        String editDescription = editDesc.getText().toString();
        final int sex = isSex? 0 : 1;

        mPresenter.loadUserMessage(mPortrait,editDescription,sex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (resultUri!=null)
            {
                loadPortrait(resultUri);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            Application.showToast(cropError.toString());
        }
    }

    private void loadPortrait(final Uri resultUri) {
        mPortrait = resultUri.getPath();

        Glide.with(this)
                .load(resultUri)
                .asBitmap()
                .centerCrop()
                .into(imPortrait);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new UserPresenter(this);
    }


    @Override
    public void loadSuccess(UserCard card) {
        hideDialogLoading();
        // 更新成功跳转到主界面
        MainActivity.show(this);
       finish();
    }

    @Override
    public void showErrorMsg(int strId) {
        super.showErrorMsg(strId);
        hideDialogLoading();
    }
}
