package com.utsoft.jan.factory.persenter.search;

import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.helper.UserHelper;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import retrofit2.Call;

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

    private Call searchCall;

    public SearchUserPresenter(SearchContract.UserView mView) {
        super(mView);
    }

    @Override
    public void onDataLoad(final List<UserCard> userCards) {
        final SearchContract.UserView view = getView();
        if (view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.searchDone(userCards);
                }
            });
        }
    }

    @Override
    public void onDataNotAvailable(final int resId) {
        final SearchContract.UserView view = getView();
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
    public void search(String text) {
        //开始搜素  防止重复点击
        start();
        Call call = searchCall;
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
        searchCall = UserHelper.search(text, this);
    }
}
