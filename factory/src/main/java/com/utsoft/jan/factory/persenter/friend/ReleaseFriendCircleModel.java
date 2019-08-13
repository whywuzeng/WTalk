package com.utsoft.jan.factory.persenter.friend;

import com.google.gson.annotations.Expose;

/**
 * @author fengyun
 * 发布朋友圈的Model
 */
public class ReleaseFriendCircleModel {


    @Expose
    private String content;//内容
    @Expose
    private String releaseId;//发布人Id
    @Expose
    private String imgs;//朋友圈图片


    public ReleaseFriendCircleModel( String content,  String releaseId, String imgs) {
        this.content = content;
        this.releaseId = releaseId;
        this.imgs = imgs;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId;
    }

    @Override
    public String toString() {
        return "ReleaseFriendCircleModel{" +
                "content='" + content + '\'' +
                ", releaseId='" + releaseId + '\'' +
                ", imgs='" + imgs + '\'' +
                '}';
    }
}
