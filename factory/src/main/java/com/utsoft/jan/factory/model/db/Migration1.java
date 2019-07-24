package com.utsoft.jan.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * Created by Administrator on 2019/7/24.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.model.db
 */
@Migration(version = AppDataBase.version,database = AppDataBase.class)
public class Migration1 extends AlterTableMigration<Session> {

    public Migration1(Class<Session> table) {
        super(table);
    }

    @Override
    public void onPreMigrate() {
        super.onPreMigrate();
        addColumn(SQLiteType.TEXT,Session_Table.pictureUrl.getNameAlias().name());
    }
}
