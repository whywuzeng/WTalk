package com.utsoft.jan.factory.presenter;

import android.support.annotation.StringRes;

import com.utsoft.jan.widget.recycler.RecyclerAdapter;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.presenter
 */
public interface BaseContract {

    interface View<T extends Presenter>{
        void showLoading();

        void showErrorMsg(@StringRes int strId);

        void setPresenter(T presenter);
    }

    interface Presenter{
        void start();

        void destory();
    }

    interface RecyclerView<T extends Presenter,ViewMode> extends View<T>{

        RecyclerAdapter<ViewMode> getRecyclerAdapter();

        void onAdapterDataChange();
    }
}
