package com.utsoft.jan.factory.data;

import java.util.List;

/**
 * Created by Administrator on 2019/7/5.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data
 */
public interface DbDataSource<Data> extends DataSource{
    void load(DataSource.SucceedCallback<List<Data>> callback);
}
