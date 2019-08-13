package com.utsoft.jan.factory.persenter.friend;

import com.google.gson.annotations.Expose;

/**
 * 评论的model
 */
public class CommentModel {

    @Expose
    private String friendCircleId;//朋友圈Id

    @Expose
    private String content;//评论内容


    public CommentModel( String friendCircleId, String content) {
        this.friendCircleId = friendCircleId;
        this.content = content;
    }

    public String getFriendCircleId() {
        return friendCircleId;
    }

    public void setFriendCircleId(String friendCircleId) {
        this.friendCircleId = friendCircleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "friendCircleId='" + friendCircleId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
