package com.utsoft.jan.clickevent;

import android.support.annotation.IdRes;
import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2019/8/19.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.clickevent
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SingleClick {

    @IdRes int[] valueId() default { View.NO_ID };
    /* 点击间隔时间 */
    long value() default 1000;
}
