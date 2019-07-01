package com.utsoft.jan.factory.persenter.user;

import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.helper.UserHelper;
import com.utsoft.jan.factory.model.api.user.UserUpdateModel;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.presenter.BasePresenter;

/**
 * Created by Administrator on 2019/7/1.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.user
 */
public class UserPresenter extends BasePresenter<UserContract.View> implements UserContract.Presenter,DataSource.CallBack<UserCard> {

    public UserPresenter(UserContract.View mView) {
        super(mView);
    }

    @Override
    public void onDataLoad(UserCard userCard) {
        UserContract.View view = getView();
        if (view!=null){
            view.loadSuccess(userCard);
        }
    }

    @Override
    public void onDataNotAvailable(int resId) {
        UserContract.View view = getView();
        if (view!=null){
            view.showErrorMsg(resId);
        }
    }

    @Override
    public void loadUserMessage() {
        UserUpdateModel model = new UserUpdateModel("吴增", "http://www.baidu.com", "这是测试", 1);
        UserHelper.update(model,this);
    }
}
