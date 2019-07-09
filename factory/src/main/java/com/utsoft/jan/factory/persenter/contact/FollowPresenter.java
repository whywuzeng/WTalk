package com.utsoft.jan.factory.persenter.contact;

import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.helper.UserHelper;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * Created by Administrator on 2019/7/9.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.contact
 */
public class FollowPresenter extends BasePresenter<FollowContract.View>
        implements DataSource.CallBack<UserCard>
                    ,FollowContract.Presenter{

    public FollowPresenter(FollowContract.View mView) {
        super(mView);
    }

    @Override
    public void onDataLoad(final UserCard card) {
        final FollowContract.View view = getView();
        if (view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.followDone(card);
                }
            });
        }
    }

    @Override
    public void onDataNotAvailable(final int resId) {
        final FollowContract.View view = getView();
        if (view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showErrorMsg(resId);
                }
            });
        }
    }

    @Override
    public void follow(String id) {
        start();
        //逻辑从网络或者 本地

        UserHelper.follow(id,this);
    }
}
