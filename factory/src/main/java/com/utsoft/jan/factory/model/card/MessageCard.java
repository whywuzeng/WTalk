package com.utsoft.jan.factory.model.card;

import com.utsoft.jan.factory.model.db.Message;
import com.utsoft.jan.factory.model.db.User;

import java.util.Date;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.model.card
 */
public class MessageCard {

    private String id;
    private String content;
    private String attach;
    private int type;
    private Date createAt;
    private String senderId;
    private String receiverId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private transient int status = Message.STATUS_DONE;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Message build(User sender, User receiver){
        Message message = new Message();
        message.setId(id);
        message.setContent(content);
        message.setAttach(attach);
        message.setType(type);
        message.setCreateAt(createAt);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setStatus(status);

        return message;
    }
}
