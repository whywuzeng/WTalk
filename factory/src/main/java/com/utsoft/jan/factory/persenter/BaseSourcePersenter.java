package com.utsoft.jan.factory.persenter;

import com.utsoft.jan.factory.data.DataSource;
import com.utsoft.jan.factory.data.DbDataSource;
import com.utsoft.jan.factory.presenter.BaseContract;
import com.utsoft.jan.factory.presenter.BaseRecyclerPresenter;

import java.util.List;

/**
 * Created by Administrator on 2019/7/5.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.persenter
 */
public abstract class BaseSourcePersenter<Data,ViewModel,
        View extends BaseContract.RecyclerView,
        Source extends DbDataSource<Data>>
        extends BaseRecyclerPresenter<ViewModel,View>
        implements DataSource.SucceedCallback<List<Data>> {

    private Source mSource;

    public BaseSourcePersenter(Source source,View mView) {
        super(mView);
        this.mSource = source;
    }

    @Override
    public void start() {
        super.start();
        if (mSource!=null) {
            mSource.load(this);
        }
    }

    @Override
    public void destory() {
        super.destory();
        mSource.dispose();
        mSource=null;
    }
}
