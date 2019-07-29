package com.utsoft.jan.face;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.utils.StreamUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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

    private static EmojiTab initAssets() {
        //拿到 Assets 里的zip 表情包
        //拿到zip包，然后解压到 cache文件夹，然后 emoji对象 把相对路径换成 绝对路径。
        //最后形成emojiTab
        //face-t.zip
        final Context context = Application.getInstance().getApplicationContext();
        String assetsRes = "face-t.zip";
        //拼接// data/data/包名/files/face/ft/*
        String cacheDir = String.format("%s/face/ft/", context.getFilesDir());
        File fileCacheDir = new File(cacheDir);
        try {
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
                    UnZipFile(tmpSourceFolder,fileCacheDir);

                    StreamUtil.delete(tmpSourceFolder.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File infoFile = new File(fileCacheDir, "info.json");

        Gson gson = new Gson();
        JsonReader jsonReader;

        try {
            jsonReader = gson.newJsonReader(new FileReader(infoFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        EmojiTab tab = gson.fromJson(jsonReader,EmojiTab.class);

        for (Emoji face : tab.faces) {
            face.preview = String.format("%s%s%s", cacheDir, File.separator, face.preview);
            face.source = String.format("%s%s%s", cacheDir, File.separator, face.source);

        }
        return tab;
    }

    private static void UnZipFile(File zipFile, File desFile) throws IOException {

        String desPath = desFile.getAbsolutePath();

        // ZipFile 使用
        ZipFile srcFile = new ZipFile(zipFile);
        //zf.entries
        for (Enumeration<? extends ZipEntry> entries = srcFile.entries();entries.hasMoreElements()) {
            // ZipEntry 取出Entry
            ZipEntry zipEntry = entries.nextElement();
            String name = zipEntry.getName();

            //获得输入流 zf.getinputStream
            InputStream in = srcFile.getInputStream(zipEntry);

            //拼路径 存的路径
            String desFile = desPath + File.separator + name;

            //需要 String str支持中文。str 变成8859  然后用GB2313 编码
            String strChar = new String(desFile.getBytes("8859_1"), "GB2312");
            //复制到  目的文件夹
            File file = new File(strChar);

            StreamUtil.copy(in,file);
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
