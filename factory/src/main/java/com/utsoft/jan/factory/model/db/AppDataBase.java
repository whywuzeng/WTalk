package com.utsoft.jan.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Administrator on 2019/7/2.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.model.db
 */
@Database(name = AppDataBase.Name, version = AppDataBase.version)
public class AppDataBase {
    public static final String Name = "AppDatabase";
    public static final int version = 4;
}
