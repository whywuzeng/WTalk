package com.utsoft.jan.factory.persenter.search;

import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.presenter.BaseContract;

import java.util.List;

/**
 * Created by Administrator on 2019/7/8.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.search
 */
public interface SearchContract {
    interface Presenter extends BaseContract.Presenter {
        void search(String text);
    }

    interface UserView extends BaseContract.View<Presenter> {
        void searchDone(List<UserCard> userCards);
    }
}
