package com.utsoft.jan.wtalker.activities;

import com.utsoft.jan.common.app.Activity;
import com.utsoft.jan.wtalker.frags.account.AccountTrigger;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.activities
 */
public class AccountActivity extends Activity implements AccountTrigger {

//    1.当前curfragment
//    2.loginfragment
//    3.registerfragment

    @Override
    protected int getContentLayoutId() {
        return 0;
    }

    /**
     * Activity show 显示界面
     */

    @Override
    protected void initWidget() {
        super.initWidget();

        //初始化fragment

        //初始化背景

    }

    @Override
    public void triggerView() {
        //切换界面

    }
}
