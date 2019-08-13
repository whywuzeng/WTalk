package com.utsoft.jan.wtalker.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.common.app.PresenterToolbarActivity;
import com.utsoft.jan.factory.model.card.FriendCircleCard;
import com.utsoft.jan.factory.persenter.friend.ReleaseFriendCircleContract;
import com.utsoft.jan.factory.persenter.friend.ReleaseFriendCirclePresenter;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;
import com.utsoft.jan.wtalker.R;
import com.utsoft.jan.wtalker.frags.media.GalleryFragment;
import com.yalantis.ucrop.UCrop;

import net.qiujuer.widget.airpanel.Util;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

public class ReleaseFriendCircleActivity extends PresenterToolbarActivity<ReleaseFriendCircleContract.Presenter>
        implements ReleaseFriendCircleContract.View, View.OnClickListener {


    @BindView(R.id.edit_moment)
    EditText mMoment;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private RecyclerAdapter<String> mAdapter;


    public static void show(Context context) {
        context.startActivity(new Intent(context, ReleaseFriendCircleActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_release_friend_circle;
    }

    PopupWindow popupWindow;

    @Override
    protected void initWidget() {
        super.initWidget();
        mRecycler.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<String>() {
            @Override
            protected ViewHolder<String> onCreateViewHolder(View root, int viewType) {
                return new ImageHolder(root);
            }

            @Override
            protected int getItemViewType(int position, String userCard) {
                //其实是返回cell的布局id
                return R.layout.cell_image;
            }
        });
        mAdapter.setAdapterListener(new RecyclerAdapter.AdapterListenerImpl<String>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, String s) {
                if (getString(R.string.label_add).equals(s)) {
                    popupWindow = new PopupWindow();
                    View view = LayoutInflater.from(ReleaseFriendCircleActivity.this).inflate(R.layout.real_name_popup, null);
                    view.findViewById(R.id.txt_photograph).setOnClickListener(ReleaseFriendCircleActivity.this);
                    view.findViewById(R.id.txt_album).setOnClickListener(ReleaseFriendCircleActivity.this);
                    view.findViewById(R.id.txt_cancel).setOnClickListener(ReleaseFriendCircleActivity.this);
                    popupWindow.setContentView(view);
                    popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                    //设置SelectPicPopupWindow弹出窗体的高
                    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    //设置SelectPicPopupWindow弹出窗体可点击
                    popupWindow.setFocusable(true);
                    //设置SelectPicPopupWindow弹出窗体动画效果
                    popupWindow.setAnimationStyle(R.style.PopupWindowToTopAnim);
                    //实例化一个ColorDrawable颜色为半透明
                    ColorDrawable dw = new ColorDrawable(0xb0000000);
                    //设置SelectPicPopupWindow弹出窗体的背景
                    popupWindow.setBackgroundDrawable(dw);
                    popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                } else {
                    View view = holder.itemView.findViewById(R.id.im_content);
                    //BigPictureActivity.show(ReleaseFriendCircleActivity.this,view, s);
                }
            }
        });
        mAdapter.add(getString(R.string.label_add));
    }

    //发布
    @OnClick(R.id.txt_release)
    void onReleaseClick() {
        mPresenter.release(mMoment.getText().toString(), mAdapter.getListData());
    }

    @Override
    public void onReleaseDone(FriendCircleCard friendCircleCard) {
        Application.showToast(R.string.label_release_success);
        hideDialogLoading();
        finish();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new ReleaseFriendCirclePresenter(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mAdapter.notifyDataSetChanged();
    }

    // 创建一个以当前时间为名称的文件
    private File tempFile = new File(Environment.getExternalStorageDirectory(), Application.getPhotoFileName());

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_photograph: {
                if (popupWindow != null)
                    popupWindow.dismiss();
                //请求隐藏软件盘
                Util.hideKeyboard(mMoment);
                tempFile = new File(Environment.getExternalStorageDirectory(), Application.getPhotoFileName());
                // 调用系统的拍照功能
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后照片的储存路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.txt_album: {
                if (popupWindow != null)
                    popupWindow.dismiss();
                selectPhoto();
                break;
            }
            case R.id.txt_cancel: {
                if (popupWindow != null)
                    popupWindow.dismiss();
                break;
            }
        }
    }

    /*** 选择照片并且截图的逻辑*/
    private void selectPhoto() {
        new GalleryFragment()
                .setSelectListener(new GalleryFragment.OnSelectListener() {
                    @Override
                    public void selectedImage(String path) {
                        UCrop.Options options = new UCrop.Options();
                        //设置图片处理的格式JPEG
                        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                        //设置压缩的精度
                        options.setCompressionQuality(96);
                        //得到图片的缓存地址
                        File dPath = Application.getPortraitTmpFile();
                        //第一个参数原始路径，第二个接收路径
                        //发起剪切
                        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(dPath))
                                .withAspectRatio(1, 1)//1比1比例
                                .withMaxResultSize(520, 520)//返回最大的尺寸
                                .withOptions(options)//相关参数
                                .start(ReleaseFriendCircleActivity.this);
                    }
                })
                .show(getSupportFragmentManager()
                        , GalleryFragment.class.getName());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //收到从activity传递的信息，然后取出其中的值进行图片加载
        //如果是我能够处理的类型
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == UCrop.REQUEST_CROP) {
                //通过UCORP得到对应的URI
                final Uri resultUri = UCrop.getOutput(data);
                if (resultUri != null) {
                    //加载所有图片出来,添加图片路径到布局中，图片为阿里云中的路径也是为了上传路径到服务器做准备
                    mAdapter.add(resultUri.getPath());
                }
            } else {
                UCrop.Options options = new UCrop.Options();
                //设置图片处理的格式JPEG
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                //设置压缩的精度
                options.setCompressionQuality(96);
                //得到头像的缓存地址
                File dPath = Application.getPortraitTmpFile();
                //第一个参数原始路径，第二个接收路径
                //发起剪切
                UCrop.of(Uri.fromFile(tempFile), Uri.fromFile(dPath))
                        .withAspectRatio(1, 1)//1比1比例
                        .withMaxResultSize(520, 520)//返回最大的尺寸
                        .withOptions(options)//相关参数
                        .start(ReleaseFriendCircleActivity.this);
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Application.showToast(R.string.data_rsp_error_unknown);
        }
    }

    class ImageHolder extends RecyclerAdapter.ViewHolder<String> {

        @BindView(R.id.im_content)
        ImageView mContent;

        public ImageHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(String s) {
            Glide.with(ReleaseFriendCircleActivity.this)
                    .load(s)
                    .asBitmap()
                    .placeholder(R.drawable.default_portrait)
                    .into(mContent);
        }
    }
}
