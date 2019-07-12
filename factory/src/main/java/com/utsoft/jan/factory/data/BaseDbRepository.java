package com.utsoft.jan.factory.data;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.utsoft.jan.factory.data.helper.DbHelper;
import com.utsoft.jan.factory.model.db.BaseDbModel;
import com.utsoft.jan.utils.CollectionUtil;

import net.qiujuer.genius.kit.reflect.Reflector;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/7/5.
 * <p>
 *     基础的数据仓库操作
 *     实现对数据库的基本操作监听
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data
 */
public abstract class BaseDbRepository<Data extends BaseDbModel<Data>>
        implements DbHelper.ChangedListener<Data>,
        DbDataSource<Data>,
        QueryTransaction.QueryResultListCallback<Data> {

    private DataSource.SucceedCallback<List<Data>> mCallback;
    private Class<Data> aClass;
    private List<Data> mDataList = new ArrayList<>();

    public BaseDbRepository() {
        Type[] types = Reflector.getActualTypeArguments(BaseDbRepository.class, this.getClass());
        aClass = (Class<Data>) types[0];
    }

    @Override
    public void load(SucceedCallback<List<Data>> callback) {
        this.mCallback = callback;
        registerListener();
    }

    @Override
    public void dispose() {
        DbHelper.removeChangeListener(aClass,this);
        this.mCallback = null;
        this.mDataList.clear();
    }

    private void registerListener() {
        DbHelper.addChangeListener(aClass,this);
    }

    /**
     * 数据库数据变化
     */
    @Override
    public void onDataSave(Data... datas) {
        boolean isChange = false;
        for (Data data :
                datas) {
            if (isRequired(data)) {
                insertOrReplace(data);
                isChange = true;
            }
        }

        if (isChange){
            notifyDataChange();
        }
    }

    private void insertOrReplace(Data data) {
        int index = indexOf(data);
        if (index>=0){
            replace(index,data);
        }else {
            insert(data);
        }
    }

    private int indexOf(Data newData) {
        int index = -1;
        for (Data data : mDataList) {
            index++;
            if (data.isSame(newData)){
                return index;
            }
        }
        return -1;
    }

    private void insert(Data data) {
        mDataList.add(data);
    }

    private void replace(int index, Data data) {
        mDataList.remove(index);
        mDataList.add(index,data);
    }

    protected abstract boolean isRequired(Data data);

    private void notifyDataChange() {
        if (mCallback!=null){
            mCallback.onDataLoad(mDataList);
        }
    }

    //dbflow查询的回调
    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Data> tResult) {
        if (tResult.size() == 0)
        {
            mDataList.clear();
            notifyDataChange();
            return;
        }

        Data[] data = CollectionUtil.toArray(tResult, aClass);
//      回调数据集更新
        onDataSave(data);
    }
}
