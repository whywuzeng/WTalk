package com.utsoft.jan.factory.presenter;

import android.support.v7.util.DiffUtil;

import com.utsoft.jan.widget.recycler.RecyclerAdapter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

/**
 * Created by Administrator on 2019/7/4.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.presenter
 */
public abstract class BaseRecyclerPresenter<ViewModel,View extends BaseContract.RecyclerView> extends BasePresenter<View>{

    public BaseRecyclerPresenter(View mView) {
        super(mView);
    }

    //replace 更新一堆数据
    public void refreshData(final List<ViewModel> datas) {

        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                if (datas == null || datas.size() == 0)
                    return;
                View view = getView();
                if (view == null)
                    return;

                RecyclerAdapter<ViewModel> adapter = view.getRecyclerAdapter();
                adapter.replace(datas);
                view.onAdapterDataChange();
            }
        });
    }


    //差异更新数据
    public void refreshData(final DiffUtil.DiffResult result, final List<ViewModel> datas){
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                refreshDataOnUIThread(result,datas);
            }
        });
    }

    private void refreshDataOnUIThread(DiffUtil.DiffResult result, List<ViewModel> datas) {
        View view = getView();
        if (view==null)
            return;
        RecyclerAdapter<ViewModel> adapter = view.getRecyclerAdapter();

        adapter.getListData().clear();
        adapter.getListData().addAll(datas);

        view.onAdapterDataChange();
        //刷新ui界面
        result.dispatchUpdatesTo(adapter);
    }
}
