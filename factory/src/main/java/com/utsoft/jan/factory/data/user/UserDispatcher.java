package com.utsoft.jan.factory.data.user;

import android.text.TextUtils;

import com.utsoft.jan.factory.data.helper.DbHelper;
import com.utsoft.jan.factory.model.card.UserCard;
import com.utsoft.jan.factory.model.db.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2019/7/8.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.user
 */
public class UserDispatcher implements UserCenter {

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public void dispatch(UserCard... cards) {
        if (cards == null && cards.length == 0)
            return;

        //丢到单线程池中
        executor.execute(new UserCardHandle(cards));
    }

    private static class UserDispatcherHolder {
        private static final UserDispatcher instance = new UserDispatcher();
    }

    private UserDispatcher() {

    }

    public static final UserDispatcher getInstance(){
        return UserDispatcherHolder.instance;
    }


    private class UserCardHandle implements Runnable {

        private final UserCard[] cards;

        public UserCardHandle(UserCard[] cards) {
            this.cards = cards;
        }

        @Override
        public void run() {
            List<User> users = new ArrayList<>();
            for (UserCard usercard :
                    cards) {
                if (usercard == null && TextUtils.isEmpty(usercard.getId())) {
                    continue;
                }
                users.add(usercard.build());
            }
            DbHelper.save(User.class,users.toArray(new User[0]));
        }
    }
}
