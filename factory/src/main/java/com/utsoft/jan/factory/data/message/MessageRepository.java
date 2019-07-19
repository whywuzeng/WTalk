package com.utsoft.jan.factory.data.message;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.OperatorGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.utsoft.jan.factory.data.BaseDbRepository;
import com.utsoft.jan.factory.model.db.Message;
import com.utsoft.jan.factory.model.db.Message_Table;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.data.message
 */
public class MessageRepository extends BaseDbRepository<Message> implements MessageDataSource {

    private final String receiverId;

    public MessageRepository(String receiverId) {
        super();
        this.receiverId = receiverId;
    }

    @Override
    public void load(SucceedCallback<List<Message>> callback) {
        super.load(callback);
        //把 他 发给我的  以及 我发给别人的。全部查出来
        //receiverid = receiverid or senderid = receiverid and groupid = null;
        SQLite.select()
                .from(Message.class)
                .where(OperatorGroup.clause()
                        .and(Message_Table.receiver_id.eq(receiverId))
                        )
                .or(Message_Table.sender_id.eq(receiverId))
                .orderBy(Message_Table.createAt,false)
                .limit(30)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(Message message) {
        return (receiverId.equalsIgnoreCase(message.getSender().getId())) ||
                (message.getReceiver() != null && receiverId.equalsIgnoreCase(message.getReceiver().getId()));
    }

    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Message> tResult) {
        Collections.reverse(tResult);
        super.onListQueryResult(transaction, tResult);
    }
}
