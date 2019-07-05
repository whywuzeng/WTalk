package com.utsoft.jan.factory.utils;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by Administrator on 2019/7/5.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.utils
 */
public class DiffUIDataCallback<T extends DiffUIDataCallback.UIDataDiffer<T>> extends DiffUtil.Callback {

    private List<T> mOldList;
    private List<T> mNewList;

    public DiffUIDataCallback(List<T> mOldList, List<T> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T beanOld = mOldList.get(oldItemPosition);
        T beanNew = mNewList.get(newItemPosition);
        return beanNew.isSame(beanOld);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T beanOld = mOldList.get(oldItemPosition);
        T beanNew = mNewList.get(newItemPosition);
        return beanNew.isUIContentSame(beanOld);
    }

   public interface UIDataDiffer<T>{
        //给你一个旧数据 是相同的数据么？
        boolean isSame(T old);

        // 你和旧数据对比，内容是否相同
        boolean isUIContentSame(T old);
    }
}
