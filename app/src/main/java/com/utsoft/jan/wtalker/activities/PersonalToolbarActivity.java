package com.utsoft.jan.wtalker.activities;

import com.utsoft.jan.common.app.PresenterToolbarActivity;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.persenter.contact.PersonalContract;
import com.utsoft.jan.factory.persenter.contact.PersonalPresenter;

/**
 * Created by Administrator on 2019/7/9.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.activities
 */
public class PersonalToolbarActivity extends PresenterToolbarActivity<PersonalContract.Presenter>
        implements PersonalContract.View {

    @Override
    protected void initPresenter() {
        mPresenter = new PersonalPresenter(this);
    }

    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    @Override
    public String getUserID() {
        return null;
    }

    @Override
    public void onLoadDone(User user) {

    }

    @Override
    public void allowSayHello(boolean isAllow) {

    }

    @Override
    public void setFollowStatus(boolean isFollow) {

    }
}
