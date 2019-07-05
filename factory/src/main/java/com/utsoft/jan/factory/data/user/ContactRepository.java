package com.utsoft.jan.factory.data.user;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.utsoft.jan.factory.data.BaseDbRepository;
import com.utsoft.jan.factory.model.db.User;
import com.utsoft.jan.factory.model.db.User_Table;
import com.utsoft.jan.factory.persistence.Account;

import java.util.List;

/**
 * Created by Administrator on 2019/7/5.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.user
 */
public class ContactRepository extends BaseDbRepository<User> implements ContactDataSource {

    @Override
    public void load(SucceedCallback<List<User>> callback) {
        super.load(callback);

        //加载本地数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUser().getId()))
                .orderBy(User_Table.name,true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(User user) {
        return user.isFollow()&&!Account.getUser().getId().equals(user.getId());
    }
}
