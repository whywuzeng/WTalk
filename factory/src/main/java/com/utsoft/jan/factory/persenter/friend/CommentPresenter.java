package com.utsoft.jan.factory.persenter.friend;

import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.Log;

import com.utsoft.jan.factory.R;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.helper.FriendCircleHelper;
import com.utsoft.jan.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;


/**
 * @author fengyun
 *         评论的逻辑
 */

public class CommentPresenter extends BasePresenter<CommentContract.CommentView>
        implements CommentContract.Presenter, DataSource.CallBack {
    public CommentPresenter(CommentContract.CommentView view) {
        super(view);
    }

    @Override
    public void comment(String friendCircleId, String content) {
        start();
        final CommentContract.CommentView view = getView();
         if (TextUtils.isEmpty(friendCircleId)) {
            view.showErrorMsg(R.string.hint_comment_info);
        } else {
            //评论
            CommentModel commentModel = new CommentModel(friendCircleId, content);
            Log.e("commentModel", commentModel.toString());
            FriendCircleHelper.comment(commentModel, this);
        }
    }

    @Override
    public void onDataLoad(Object user) {
        final CommentContract.CommentView view = getView();
        if (view == null)
            return;
        //强制执行在主线程
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showLoading();
            }
        });

    }

    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {
        final CommentContract.CommentView view = getView();
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
}
