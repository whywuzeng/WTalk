package com.utsoft.jan.factory.model.card;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * 评论内容的卡片
 * @author fengyun
 */
public class CommentCard {

    @Expose
    private String id;

    @Expose
    private String friendCircleId;

    @Expose
    private Date createAt;
    @Expose
    private String content;//评论的内容

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFriendCircleId() {
        return friendCircleId;
    }

    public void setFriendCircleId(String friendCircleId) {
        this.friendCircleId = friendCircleId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
