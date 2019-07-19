package com.utsoft.jan.factory.persenter.session;

import android.support.v7.util.DiffUtil;

import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.session.SessionDataSource;
import com.utsoft.jan.factory.model.db.Session;
import com.utsoft.jan.factory.persenter.BaseSourcePersenter;
import com.utsoft.jan.factory.utils.DiffUIDataCallback;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2019/7/19.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.session
 */
public class SessionPresenter extends BaseSourcePersenter<Session, Session,
        SessionContract.SessionView<Session>,SessionDataSource>
        implements SessionContract.Presenter,DataSource.SucceedCallback<List<Session>> {


    public SessionPresenter(SessionDataSource source, SessionContract.SessionView<Session> mView) {
        super(source, mView);
    }

    @Override
    public void onDataLoad(List<Session> sessions) {
        SessionContract.SessionView<Session> view = getView();
        if (view==null)
            return;
        RecyclerAdapter<Session> recyclerAdapter = view.getRecyclerAdapter();

        List<Session> oldData = recyclerAdapter.getListData();

        DiffUIDataCallback<Session> callback = new DiffUIDataCallback<>(oldData, sessions);

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        refreshData(result,sessions);
    }
}
