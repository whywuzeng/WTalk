package com.utsoft.jan.clickevent;

/**
 * Created by Administrator on 2019/8/19.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.clickevent
 */
public class XClickUtil {
    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime = 0L;

    private static int mIdView;

    public static boolean  isFastDoubleClick(int idView, long time_interval) {
        long now = System.currentTimeMillis();

        if (Math.abs(now - mLastClickTime) < time_interval && idView == mIdView) {
            return true;
        }
        else {
            mIdView = idView;
            mLastClickTime = now;
            return false;
        }

    }
}
