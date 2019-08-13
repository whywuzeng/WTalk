package com.utsoft.jan.factory.persenter.friend;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;

import com.utsoft.jan.factory.Factory;
import com.utsoft.jan.factory.R;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.helper.FriendCircleHelper;
import com.utsoft.jan.factory.model.card.FriendCircleCard;
import com.utsoft.jan.factory.net.UploadHelper;
import com.utsoft.jan.factory.persistence.Account;
import com.utsoft.jan.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;


/**
 * @author fengyun
 * 发布朋友圈的逻辑
 */

public class ReleaseFriendCirclePresenter  extends BasePresenter<ReleaseFriendCircleContract.View>
        implements ReleaseFriendCircleContract.Presenter, DataSource.CallBack<FriendCircleCard>{
    public ReleaseFriendCirclePresenter(ReleaseFriendCircleContract.View view) {
        super(view);
    }

    @Override
    public void release(final String content, final List<String> paths) {
        start();
        final ReleaseFriendCircleContract.View view = getView();
        if(TextUtils.isEmpty(content) || TextUtils.isEmpty(content.trim())){
            view.showErrorMsg(R.string.data_rsp_error_release_content_null);
        }else if(paths==null || paths.size()<2){
            view.showErrorMsg(R.string.data_rsp_error_release_image_null);
        }else{
            //上传图片
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    String urls = uploadPicture(paths);
                    if(TextUtils.isEmpty(urls))
                        return;
                    ReleaseFriendCircleModel model = new ReleaseFriendCircleModel(content, Account.getUser().getId(), urls);
                    Log.e(TAG, model.toString() );
                    FriendCircleHelper.release(model,ReleaseFriendCirclePresenter.this);
                }
            });

        }
    }

    private static final String TAG = "ReleaseFriendCirclePres";
    //同步上传
    private String uploadPicture(List<String> path) {
        //一种情况 当他只有一个的时候也就是只有ADD的时候不操作
        String img="";
        for (int i = 0; i < path.size(); i++) {
            if(i>0) {
                String url = UploadHelper.uploadPortrait(path.get(i));
                img += url+",";
            }
        }
        img = img.substring(0,img.length()-1);
        if (TextUtils.isEmpty(img)) {
            //切换到UI 线程，提示信息
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    ReleaseFriendCircleContract.View view = getView();
                    if (view != null) {
                        view.showErrorMsg(R.string.data_upload_error);
                    }
                }
            });
        }
        return img;
    }

    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {

        final ReleaseFriendCircleContract.View view = getView();
        if (view == null)
            return;
        //强制执行在主线程
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showErrorMsg(strRes);
            }
        });
    }

    @Override
    public void onDataLoad(final FriendCircleCard friendCircleCard) {
        final ReleaseFriendCircleContract.View view = getView();
        if (view == null)
            return;
        //强制执行在主线程
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.onReleaseDone(friendCircleCard);
            }
        });
    }
}
