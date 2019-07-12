package com.utsoft.jan.factory.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utsoft.jan.factory.Factory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/7/12.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.model
 */
public class PushModel {

    // 退出登录
    public static final int ENTITY_TYPE_LOGOUT = -1;
    // 普通消息送达
    public static final int ENTITY_TYPE_MESSAGE = 200;
    // 新朋友添加
    public static final int ENTITY_TYPE_ADD_FRIEND = 1001;
    // 新群添加
    public static final int ENTITY_TYPE_ADD_GROUP = 1002;
    // 新的群成员添加
    public static final int ENTITY_TYPE_ADD_GROUP_MEMBERS = 1003;
    // 群成员信息更改
    public static final int ENTITY_TYPE_MODIFY_GROUP_MEMBERS = 2001;
    // 群成员退出
    public static final int ENTITY_TYPE_EXIT_GROUP_MEMBERS = 3001;

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    private List<Entity> entities = new ArrayList<>();

    public PushModel(List<Entity> entities) {
        this.entities = entities;
    }

    public static PushModel decode(String json){
        Gson gson = Factory.getGson();
        Type type = new TypeToken<List<Entity>>() {
        }.getType();
        List<Entity> entities = (List<Entity>) gson.fromJson(json, type);
        return new PushModel(entities);
    }

    public static class Entity{
        public Entity(){

        }
        // 消息类型
        public int type;
        // 消息实体
        public String content;
        // 消息生成时间
        public Date createAt;

        @Override
        public String toString() {
            return "Entity{" +
                    "type=" + type +
                    ", content='" + content + '\'' +
                    ", createAt=" + createAt +
                    '}';
        }
    }
}
