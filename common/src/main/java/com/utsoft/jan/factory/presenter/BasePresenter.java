package com.utsoft.jan.factory.presenter;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.presenter
 */
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter{

    private T mView;

    //构造方法必须 传入view
    public BasePresenter(T mView){
        setView(mView);
    }

    protected void setView(T mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    protected final T getView(){
        return mView;
    }

    /**
     * 传入 view 开始loading
      */
    @Override
    public void start() {
        if (this.mView!=null){
            mView.showLoading();
        }
    }

    /**
     * 置空view presenter
     */
    @Override
    public void destory() {
        if (this.mView!=null){
            T view = this.mView;
            this.mView = null;
            view.setPresenter(null);
        }
    }
}
