package com.utsoft.jan.factory.persenter.contact;

import android.support.v7.util.DiffUtil;

import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.user.ContactDataSource;
import com.utsoft.jan.factory.data.user.ContactRepository;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.persenter.BaseSourcePersenter;
import com.utsoft.jan.factory.utils.DiffUIDataCallback;
import com.utsoft.jan.widget.recycler.RecyclerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2019/7/4.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter.contact
 */
public class ContactPresenter extends BaseSourcePersenter<User,User,ContactContract.View,ContactDataSource>
        implements ContactContract.Presenter,DataSource.SucceedCallback<List<User>> {

    public ContactPresenter(ContactDataSource source, ContactContract.View mView) {
        super(new ContactRepository(), mView);
    }

    @Override
    public void start() {
        super.start();
//        UserHelper.update();
    }

    @Override
    public void onDataLoad(List<User> users) {
        ContactContract.View view = getView();
        if (view==null)
            return;
        RecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> olds = adapter.getListData();

        DiffUtil.Callback callback = new DiffUIDataCallback<>(olds, users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        refreshData(result,users);
    }
}
