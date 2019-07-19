package com.utsoft.jan.factory.data.session;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.utsoft.jan.factory.data.BaseDbRepository;
import com.utsoft.jan.factory.model.db.Session;
import com.utsoft.jan.factory.model.db.Session_Table;

import java.util.List;

/**
 * Created by Administrator on 2019/7/19.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.session
 */
public class SessionRepository extends BaseDbRepository<Session> implements SessionDataSource {
    @Override
    public void load(SucceedCallback<List<Session>> callback) {
        super.load(callback);
        SQLite.select()
                .from(Session.class)
                .orderBy(Session_Table.lastModify,true)
                .async()
                .queryListResultCallback(this)
                .execute();

    }

    @Override
    protected boolean isRequired(Session message) {
        return true;
    }
}
