package com.utsoft.jan.factory.persenter.search;

import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.presenter.BasePresenter;

import java.util.List;

/**
 * Created by Administrator on 2019/7/8.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.search
 */
public class SearchUserPresenter extends BasePresenter<SearchContract.UserView>
        implements SearchContract.Presenter,
        DataSource.CallBack<List<UserCard>> {

    public SearchUserPresenter(SearchContract.UserView mView) {
        super(mView);

    }

    @Override
    public void onDataLoad(List<UserCard> userCards) {

    }

    @Override
    public void onDataNotAvailable(int resId) {

    }

    @Override
    public void search(String text) {

    }
}
