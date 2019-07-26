package com.utsoft.jan.face;

import android.content.Context;

import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.utils.StreamUtil;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2019/7/26.
 * <p>
 * by author wz
 * 提取emoji的数据类
 * <p>
 * com.utsoft.jan.face
 */
public class Face {

    private static List<EmojiTab> emojiTabs;

    /**
     * 拿到所有的表情面板 和 表情个数
     */
    public static List<EmojiTab> all() {
        init();
        return emojiTabs;
    }

    private static void init() {
        if (emojiTabs == null) {
            initAssets();
        }
    }

    private static void initAssets() {
        //拿到 Assets 里的zip 表情包
        //拿到zip包，然后解压到 cache文件夹，然后 emoji对象 把相对路径换成 绝对路径。
        //最后形成emojiTab
        //face-t.zip
        final Context context = Application.getInstance().getApplicationContext();
        String assetsRes = "face-t.zip";
        //拼接// data/data/包名/files/face/ft/*
        String cacheDir = String.format("%s/face/ft/", context.getFilesDir());
        File fileCacheDir = new File(cacheDir);
        if (!fileCacheDir.exists())
        {
            if (fileCacheDir.mkdirs())
            {
                InputStream stream = context.getAssets().open(assetsRes);

                //取出的 字节流 要保存到一个地方
                File tmpSourceFolder = new File(fileCacheDir, "source.zip");
                //copy
                StreamUtil.copy(stream,tmpSourceFolder);
                //解压到文件夹
                UnZipFile();
            }
        }

    }

    public class EmojiTab {
        // emoji 表情list
        private List<Emoji> faces;
        private String name;
        private String preview;
    }

    public class Emoji {
        private String desc;
        private String key;
        private String preview;
        private String source;
    }
}
