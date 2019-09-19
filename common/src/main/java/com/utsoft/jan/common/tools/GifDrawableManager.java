package com.utsoft.jan.common.tools;

/**
 * Created by Administrator on 2019/9/19.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.common.tools
 */
public class GifDrawableManager {

    private GifDrawableManager(){

    }

    private static final class Holder{
        private static final GifDrawableManager mInstance = new GifDrawableManager();
    }

    public static GifDrawableManager getInstance(){
        return Holder.mInstance;
    }

}
