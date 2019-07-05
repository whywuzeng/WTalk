package com.utsoft.jan.factory.model.db;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.utsoft.jan.factory.utils.DiffUIDataCallback;

/**
 * Created by Administrator on 2019/7/5.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.model.db
 */
public abstract class BaseDbModel<Model> extends BaseModel implements DiffUIDataCallback.UIDataDiffer<Model> {
}
