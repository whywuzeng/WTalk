package com.utsoft.jan.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.utsoft.jan.factory.data.helper.MessageHelper;
import com.utsoft.jan.factory.data.helper.UserHelper;

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
    private String pictureUrl;

    @Column
    private Date lastModify;

    @Column
    private String lastMsgContent;

    @ForeignKey(tableClass = Message.class)
    private Message message;

    public Session(){

    }

    public Session(Identity identity) {
        this.id = identity.id;
        this.receiverType = identity.type;
    }

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

    public static Identity createSessionIdentity(Message message){
        Identity identity = new Identity();
        if (message.getType() == Message.RECEIVER_TYPE_NONE)
        {
            User other = message.getOther();
            identity.setId(other.getId());
            identity.setType(message.getType());

        }
        return identity;
    }

    public void refreshNow() {
        if (receiverType == Message.RECEIVER_TYPE_NONE)
        {
            Message lastMessage = MessageHelper.findLastMessage(id);
            if (lastMessage == null)
            {
                User user = UserHelper.findFromLocal(id);
                this.name = user.getName();
                this.pictureUrl = user.getPortrait();
                this.lastModify = new Date();
                this.lastMsgContent = "";
            }else {
                User other = lastMessage.getOther();
                this.name = other.getName();
                this.lastModify = lastMessage.getCreateAt();
                this.lastMsgContent = lastMessage.getContent();
                this.pictureUrl = other.getPortrait();
            }
        }
    }

    /**
     * 与一个  关注人 所有的聊天就是一个ID。
     */
    public static class Identity {
        String id;
        int type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Identity identity = (Identity) o;
            return type == identity.type &&
                    Objects.equals(id, identity.id);
        }

        @Override
        public int hashCode() {

            return Objects.hash(id, type);
        }
    }
}
