package com.utsoft.jan.wtalker.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;

/**
 * Created by Administrator on 2019/7/1.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.wtalker.helper
 */
public class NavHelper<T> {
    private SparseArray<Tab<T>> tabs = new SparseArray<>();

    private FragmentManager manager;
    private int layContainer;
    private Context context;
    private  TabChangeListener<T> listener;

    public NavHelper(FragmentManager manager,int layContainer,Context context,TabChangeListener<T> listener) {
        this.manager = manager;
        this.layContainer = layContainer;
        this.context = context;
        this.listener = listener;
    }

    public Tab<T> getCurTab() {
        return CurTab;
    }

    private Tab<T> CurTab;
    //add 方法
    public void add(int menuId,Tab<T> tab){
        if (tab!=null)
        tabs.put(menuId,tab);
    }
    //doselect 方法
    public boolean menuClick(int menuId){
        Tab<T> tTab = tabs.get(menuId);
        if (tTab!=null){
            doSelect(tTab);
            return true;
        }
        return false;
    }

    private void doSelect(Tab<T> tTab) {
        Tab<T> old = null;
        if (CurTab!=null){

        }

        CurTab = tTab;
        doTabChange(CurTab,old);
    }

    private void doTabChange(Tab<T> newTab, Tab<T> oldTab) {

        FragmentTransaction ft = manager.beginTransaction();

        if (oldTab!=null)
        {
            //要暂存
            if (oldTab.fragment!=null)
            {
                ft.detach(oldTab.fragment);
            }
        }

        if (newTab!=null){
            //新的就添加
            if (newTab.fragment==null)
            {
                Fragment fragment = Fragment.instantiate(context, newTab.tClass.getName());
                newTab.fragment = fragment;
                ft.add(layContainer,fragment);
            }else {
                //暂存的就 呈现
                ft.attach(newTab.fragment);
            }
        }

        ft.commit();

        //通知回调
        notifyTabChange(newTab,oldTab);

    }

    private void notifyTabChange(Tab<T> newTab, Tab<T> oldTab) {
        if (listener!=null)
        {
            listener.onTabChangeClick(newTab,oldTab);
        }
    }

    //listener 回调
    public interface TabChangeListener<T>{
        void onTabChangeClick(Tab<T> newtab,Tab<T> oldtab);
    }

    //tab  model
    public static class Tab<T>{
        public T extra;
        public Class<?> tClass;

        public Tab(T extra, Class<?> tClass) {
            this.extra = extra;
            this.tClass = tClass;
        }

        //外部不能访问
        Fragment fragment;

    }
}
