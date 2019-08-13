package com.utsoft.jan.factory.model.card;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * 朋友圈的卡片
 */
public class FriendCircleCard {

    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String content;
    @Expose
    private String head;
    @Expose
    private String imgs;
    @Expose
    private String releaseId;

    @Expose
    private Date createAt;// 发布时间

    @Expose
    private int commentSize;//评论数量

    //是否点赞
    @Expose
    private boolean isFabulous = false;

    //点赞数量
    @Expose
    private int fabulousSize = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public int getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(int commentSize) {
        this.commentSize = commentSize;
    }

    public boolean isFabulous() {
        return isFabulous;
    }

    public void setFabulous(boolean fabulous) {
        isFabulous = fabulous;
    }

    public int getFabulousSize() {
        return fabulousSize;
    }

    public void setFabulousSize(int fabulousSize) {
        this.fabulousSize = fabulousSize;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
