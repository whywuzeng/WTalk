package com.utsoft.jan.factory.persenter.user;

import android.text.TextUtils;

import com.utsoft.jan.factory.Factory;
import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.helper.UserHelper;
import com.utsoft.jan.factory.model.api.user.UserUpdateModel;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.net.UploadHelper;
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
    public void loadUserMessage(final String portrait, final String editDescription, final int sex) {
        start();

        final UserContract.View view = getView();

        if (TextUtils.isEmpty(editDescription))
        {
            if (view!=null)
            {
                view.showErrorMsg(com.utsoft.jan.common.R.string.data_account_update_invalid_parameter);
            }
            return;
        }

        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                String url = UploadHelper.uploadPortrait(portrait);
                if (TextUtils.isEmpty(url))
                {
                    if (view!=null)
                    {
                        view.showErrorMsg(com.utsoft.jan.common.R.string.data_picture_upload_error);
                    }

                }

                UserUpdateModel model = new UserUpdateModel("", url, editDescription, sex);
                UserHelper.update(model,UserPresenter.this);
            }
        });

    }
}
