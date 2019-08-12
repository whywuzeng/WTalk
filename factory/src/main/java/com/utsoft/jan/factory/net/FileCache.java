package com.utsoft.jan.factory.net;

import android.os.Environment;

import com.utsoft.jan.utils.HashUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2019/8/9.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.net
 */
public class FileCache<Holder> {

    private CacheCallBack<Holder> mCallBack;

    private Holder mHolder;

    private boolean isNet =false ;

    public FileCache(CacheCallBack<Holder> callBack) {
        mCallBack = callBack;
    }

    public void getCache(final String content,Holder holder) {
        if (content.startsWith(Environment.getExternalStorageDirectory().getPath()))
            return;
        //判断相同的文件名来就可以return
        File file = getFileString(content);
        if (file!=null&&file.exists())
        {
            CacheCallBack mCallBack = FileCache.this.mCallBack;
            if (mCallBack != null) {
                mCallBack.successDown(file.getAbsolutePath(), holder);
            }
            return;
        }

        if (isNet)
            return;
        isNet =true;
        getNetCache(content,holder);
    }

    private void getNetCache(final String content, final Holder holder) {
        OkHttpClient build = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(content)
                .get()
                .build();

        build.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        isNet =false;
                        CacheCallBack mCallBack = FileCache.this.mCallBack;
                        if (mCallBack != null) {
                            mCallBack.failureDown();
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        isNet =false;
                        if (response.isSuccessful()) {
                            InputStream inputStream = response.body().byteStream();
                            /**
                             * 根据 md5 String 形成一个固定的string，文件名.
                             */
                            String path = saveFile(inputStream, content);
                            CacheCallBack mCallBack = FileCache.this.mCallBack;
                            if (mCallBack != null) {
                                mCallBack.successDown(path, holder);
                            }
                        }
                    }
                });
    }

    private String saveFile(InputStream inputStream, String content) {
        File outFile = createFile(content);
        byte[] by = new byte[1024];
        int read;
        try {
            FileOutputStream outputStream = new FileOutputStream(outFile);

            while ((read = inputStream.read(by)) != -1) {
                outputStream.write(by, 0, read);
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            try {
                outputStream.close();
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return outFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private File getFileString(String urlPath)
    {
        String dirPath = Environment.getExternalStorageDirectory().getPath() + "/AudioRecord/";
        String cacheKey = getFileCacheKey(urlPath);
        return new File(dirPath + cacheKey);
    }

    private File createFile(String urlPath) {
        //tmp临时缓存
        String dirPath = Environment.getExternalStorageDirectory().getPath() + "/AudioRecord/";
        File file = new File(dirPath);

        if (!file.exists()) {
            file.mkdirs();
        }

        String cacheKey = getFileCacheKey(urlPath);

        String filePath = dirPath + cacheKey;
        File objFile = new File(filePath);
        if (!objFile.exists()) {
            try {
                objFile.createNewFile();
                return objFile;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return objFile;
    }


    private String getFileCacheKey(String path) {
        String md5String = HashUtil.getMD5String(path);
        return String.format("audiocache%s.wav", md5String);
    }

    public interface CacheCallBack<Holder> {
        void successDown(String path, Holder holder);

        void failureDown();
    }
}
