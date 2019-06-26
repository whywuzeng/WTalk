package com.utsoft.jan.wtalker;

import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.factory.Factory;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Factory.setup();
    }
}
