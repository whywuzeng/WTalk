package com.utsoft.jan.factory.persenter.friend;

import com.utsoft.jan.factory.presenter.BaseContract;

/**
 * @author fengyun
 * 点赞评论契约
 */

public interface CommentContract {

    interface Presenter extends BaseContract.Presenter{
        //评论请求
        void comment(String friendCircleId, String content);
    }

    //用户界面
    interface CommentView extends BaseContract.View<Presenter>{
        //评论成功
        void onCommentDone();
    }
}
