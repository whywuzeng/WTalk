package com.utsoft.jan.widget.recycler;

/**
 * Created by Administrator on 2019/7/4.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.widget.recycler
 */
public interface AdapterCallback<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder holder);
}
