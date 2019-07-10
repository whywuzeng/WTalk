package com.utsoft.jan.factory.persenter.contact;

import com.utsoft.jan.factory.Factory;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.helper.UserHelper;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.persistence.Account;
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
public class PersonalPresenter extends BasePresenter<PersonalContract.View>
        implements PersonalContract.Presenter
        ,DataSource.CallBack<User> {

    public PersonalPresenter(PersonalContract.View mView) {
        super(mView);
    }

    private User mData;

    @Override
    public void start() {
        super.start();
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                final PersonalContract.View view = getView();
                if (view!=null){
                    String userID = view.getUserID();
                    //访问数据库
                    final User user = UserHelper.searchFirstNet(userID);
                    mData = user;
                    if (user.getId()!=Account.getUser().getId() && user.isFollow()){
                        Run.onUiAsync(new Action() {
                            @Override
                            public void call() {
                                //可以聊天
                                view.allowSayHello(true);
                                view.onLoadDone(user);
                                view.setFollowStatus(true);
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onDataLoad(User user) {

    }

    @Override
    public void onDataNotAvailable(final int resId) {
        final PersonalContract.View view = getView();
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
    public User getUserPersonal() {
        if (mData!=null)
        {
            return mData;
        }
        return null;
    }
}
