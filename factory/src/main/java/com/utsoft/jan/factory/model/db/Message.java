package com.utsoft.jan.factory.model.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Administrator on 2019/7/11.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.model.db
 */
public class Message extends BaseDbModel<Message> implements Serializable {
    // 接收者类型
    public static final int RECEIVER_TYPE_NONE = 1;
    public static final int RECEIVER_TYPE_GROUP = 2;

    // 消息类型
    public static final int TYPE_STR = 1;
    public static final int TYPE_PIC = 2;
    public static final int TYPE_FILE = 3;
    public static final int TYPE_AUDIO = 4;

    // 消息状态
    public static final int STATUS_DONE = 0;
    public static final int STATUS_CREATED = 1;
    public static final int STATUS_FAILED = 2;

    //主键
    @PrimaryKey
    private String id;

    // 内容
    @Column
    private String content;

    // 附属信息
    @Column
    private String attach;

    // 消息类型
    @Column
    private int type;

    // 创建时间
    @Column
    private Date createAt;

    // 当前消息的状态
    @Column
    private int status;

    // 接收者群外键
    // @ForeignKey(tableClass = )

    // 在加载Message信息的时候，User并没有，懒加载
    // 发送者 外键
    @ForeignKey(tableClass = User.class,stubbedRelationship = true)
    private User sender;

    // 接收者人外键
    @ForeignKey(tableClass = User.class,stubbedRelationship = true)
    private User receiver;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    /**
     * 如果该消息为普通消息，那么返回跟我聊天的人是谁，
     * 要么是接收者，要么是发送者
     */
    User getOther(){
        return null;
    }

    /**
     * 构建一个简单的消息提示  就是audio、 是MP3的图片样子
     * 文件就是  文件的样子
     */
    String getSampleContent(){

        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj==null||getClass()!=obj.getClass())
            return false;
        Message o = (Message) obj;

        return type == o.type
                &&status == o.status
                && Objects.equals(createAt,o.createAt)
                && Objects.equals(attach,o.attach)
                && Objects.equals(content,o.content)
                && Objects.equals(sender,o.sender)
                && Objects.equals(receiver,o.receiver);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean isSame(Message old) {
        return Objects.equals(id, old.id);
    }

    @Override
    public boolean isUIContentSame(Message old) {
        // 对于同一个消息当中的字段是否有不同
        // 这里，对于消息，本身消息不可进行修改；只能添加删除
        // 唯一会变化的就是本地（手机端）消息的状态会改变
        return  old == this || status == old.status;
    }
}
