package com.utsoft.jan.factory.model.api.message;

import com.utsoft.jan.factory.model.card.MessageCard;
import com.utsoft.jan.factory.model.db.Message;
import com.utsoft.jan.factory.persistence.Account;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.model.api.message
 */
public class MsgCreateModel {

    //uuid
    private String id;

    private String content;

    private String attach;

    // 消息类型
    private int type;

    // 接收者 可为空
    private String receiverId;

    // 接收者类型，群，人
    private int receiverType = Message.RECEIVER_TYPE_NONE;

    private MsgCreateModel(){
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
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

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }
    //  -------------------------方法---------------------------------

    // 返回一个Card
    private MessageCard card;

    public MessageCard buildCard() {
        if (card == null) {
            card = new MessageCard();
            card.setAttach(attach);
            card.setContent(content);
            card.setCreateAt(new Date());
            card.setId(id);
            card.setSenderId(Account.getUser().getId());
            if (type == Message.RECEIVER_TYPE_NONE)
            {
                card.setReceiverId(receiverId);
            }
            card.setType(type);
            card.setStatus(Message.STATUS_CREATED);
        }
        return this.card;
    }

    // 同步到卡片的最新状态

    /**
     * 建造者模式，快速的建立一个发送Model
     */
   public   static class ModelBuild {
        private MsgCreateModel model;

        public ModelBuild(){
            model  = new MsgCreateModel();
        }

        public MsgCreateModel Build(){
            return model;
        }

        public ModelBuild content(String content,int type){
            model.content = content;
            model.type = type;
            return this;
        }

        public ModelBuild attach(String attach){
            model.attach = attach;
            return this;
        }

        public ModelBuild receiver(String receiverId,int receiverType){
            model.receiverId = receiverId;
            model.receiverType = receiverType;
            return this;
        }

    }


    /**
     * 把一个Message消息，转换为一个创建状态的CreateModel
     *
     * @param message Message
     * @return MsgCreateModel
     */
    public static MsgCreateModel buildWithMessage(Message message){
        MsgCreateModel model = new MsgCreateModel();
        model.setAttach(message.getAttach());
        model.setContent(message.getContent());
        model.setReceiverId(message.getReceiver().getId());
        model.setType(message.getType());
        model.setReceiverType(Message.RECEIVER_TYPE_NONE);
        return model;
    }
}
