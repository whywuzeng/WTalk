package com.utsoft.jan.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2019/7/19.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.model.db
 */
@Table(database = AppDataBase.class)
public class Session extends BaseDbModel<Session>{

    @PrimaryKey
    private String id;

    @Column
    private String name;

    @Column
    private Date lastModify;

    @Column
    private String lastMsgContent;

    @ForeignKey(tableClass = Message.class)
    private Message message;

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }

    @Column
    private int receiverType = Message.RECEIVER_TYPE_NONE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastModify() {
        return lastModify;
    }

    public void setLastModify(Date lastModify) {
        this.lastModify = lastModify;
    }

    public String getLastMsgContent() {
        return lastMsgContent;
    }

    public void setLastMsgContent(String lastMsgContent) {
        this.lastMsgContent = lastMsgContent;
    }


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public boolean isSame(Session old) {
        return Objects.equals(id,old.getId());
    }

    @Override
    public boolean isUIContentSame(Session old) {

        return Objects.equals(id,old.getId())
                &&Objects.equals(name,old.getName())
                &&Objects.equals(lastModify,old.getLastModify())
                &&Objects.equals(lastMsgContent,old.getLastMsgContent());
    }
}
