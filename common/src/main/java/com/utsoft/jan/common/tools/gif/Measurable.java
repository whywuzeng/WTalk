package com.utsoft.jan.common.tools.gif;

/**
 * Created by Administrator on 2019/8/14.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools.gif
 */
public interface Measurable {
    int getWidth();//获取真正的drawable的宽度

    int getHeight();//获取真正的drawable的高度

    boolean canMeasure();//当可以获取到宽高是返回true,否则返回false

    boolean needResize();//只有当drawable被设置时needResize返回true, onBoundsChange之后设为false,保证设置drawable边界的方法不被多次调用
}
