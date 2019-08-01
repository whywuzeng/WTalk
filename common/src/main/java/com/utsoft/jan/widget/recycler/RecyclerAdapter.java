package com.utsoft.jan.widget.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utsoft.jan.common.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2019/7/4.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.widget.recycler
 */
public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener,View.OnLongClickListener,AdapterCallback<Data> {

    private List<Data> mListData;


    private AdapterListener<Data> mAdapterListener;

    /**
     *构造方法，要传参数，data listener
     */
    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> mAdapterListener){
        this(new ArrayList<Data>(),mAdapterListener);
    }

    public RecyclerAdapter(@NonNull List<Data> mListData, AdapterListener<Data> mAdapterListener) {
        this.mListData = mListData == null?new ArrayList<Data>():mListData;
        this.mAdapterListener = mAdapterListener;
    }
    /**
     * 复写getItemViewType 获得id 作为唯一id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position,mListData.get(position));
    }

    protected abstract int getItemViewType(int position, Data data);

    /**
     * onCreateViewHolder实现 创建一个ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        //得到layoutInflater用于xml初始化view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewtype, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder(root, viewtype);
        root.setTag(R.id.tag_recycler_holder,holder);

        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        holder.unbinder = ButterKnife.bind(holder,root);
        holder.callback = this;
        return holder;
    }

    protected abstract ViewHolder<Data> onCreateViewHolder(@NonNull View root,int viewtype);


    /**
     * 绑定数据到一个Holder上
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> viewHolder, int position) {
        viewHolder.bind(mListData.get(position));
    }


    /**
     * data总数
     */
    @Override
    public int getItemCount() {
        return mListData.size();
    }


    /**
     * 返回整个集合
     */
    public List<Data> getListData(){
        return mListData;
    }


    /**
     * 插入一条数据并通知插入
     */
    public void add(Data data){
        mListData.add(data);
        //这有没有动画
        notifyItemInserted(mListData.size()-1);
    }


    /**
     *  插入一堆数据，并通知这段集合更新
     */
    public void add(Data... dataList){
        if (dataList!=null && dataList.length>0){
            int startpos = mListData.size();
            Collections.addAll(mListData,dataList);
            notifyItemRangeInserted(startpos,dataList.length);
        }
    }

    public void add(List<Data> dataList){
        if (dataList!=null && dataList.size()>0){
            int startpos = mListData.size();
            mListData.addAll(dataList);
            notifyItemRangeInserted(startpos,dataList.size());
        }
    }

    /**
     * 删除操作
     */
     public void clear(){
         mListData.clear();
         notifyDataSetChanged();
     }

    /**
     * 替换为一个新的集合，其中包括了清空
     */
    public void replace(List<Data> list){
        if (list!=null && list.size()>0){
            mListData.clear();
            mListData.addAll(list);
            notifyDataSetChanged();
        }
    }

    //更新这一条数据
    @Override
    public void update(Data data, ViewHolder holder) {

    }

    public void setAdapterListener(AdapterListener<Data> mAdapterListener) {
        this.mAdapterListener = mAdapterListener;
    }

    @Override
    public void onClick(View v) {
        ViewHolder holder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (mAdapterListener!=null)
        {
            //可见
            int position = holder.getAdapterPosition();
            mAdapterListener.onItemClick(holder,mListData.get(position));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ViewHolder holder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (mAdapterListener!=null){
            int position = holder.getAdapterPosition();
            mAdapterListener.onItemLongClick(holder,mListData.get(position));
            return true;
        }
        return false;
    }

    public static class AdapterListenerImpl<Data> implements AdapterListener<Data>{

        @Override
        public void onItemClick(ViewHolder holder, Data data) {

        }

        @Override
        public void onItemLongClick(ViewHolder holder, Data data) {

        }
    }

    public interface AdapterListener<Data>{
        void onItemClick(RecyclerAdapter.ViewHolder holder,Data data);
        void onItemLongClick(RecyclerAdapter.ViewHolder holder,Data data);
    }

    /*
    自定义 Viewholder
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder{

        private Unbinder unbinder;
        private AdapterCallback<Data> callback;
        protected Data mData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        /**
         * 绑定数据触发
         */
        public void bind(Data data) {
            this.mData = data;
            onBind(mData);
        }

        protected abstract void onBind(Data mData);

        public void updateData(Data data) {
            if (callback != null) {
                callback.update(data, this);
            }
        }
    }
}
