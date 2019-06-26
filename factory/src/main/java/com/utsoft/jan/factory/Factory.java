package com.utsoft.jan.factory;

import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.factory.persistence.Account;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory
 */
public class Factory {

    public static void setup(){

        //加载数据
        Account.load(app());
    }

    public static Application app(){
        return Application.getInstance();
    }
}
