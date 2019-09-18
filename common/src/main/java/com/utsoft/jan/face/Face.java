package com.utsoft.jan.face;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.utsoft.jan.common.R;
import com.utsoft.jan.common.app.Application;
import com.utsoft.jan.common.tools.gif.DrawableTarget;
import com.utsoft.jan.common.tools.gif.GlidePreDrawable;
import com.utsoft.jan.common.tools.gif.IsoheightImageSpan;
import com.utsoft.jan.utils.StreamUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
     * 少于1000个元素 用Android自己的ArrayMap 集合。
     */
    private static ArrayMap<String, Emoji> arrayMap = new ArrayMap<>();

    /**
     * 拿到所有的表情面板 和 表情个数
     */
    public static List<EmojiTab> all() {
        init();
        return emojiTabs;
    }

    private static void init() {
        if (emojiTabs == null) {
            EmojiTab tab = initAssets();
            //            得到ResourceFace资源
            ArrayList<EmojiTab> tabArrayList = new ArrayList<>();
            if (tab != null) {
                tabArrayList.add(tab);
            }

            EmojiTab face = initResourceFace();
            if (face != null) {
                tabArrayList.add(face);
            }

            for (EmojiTab emojiTab : tabArrayList) {
                emojiTab.copyToMap(arrayMap);
            }

            emojiTabs = Collections.unmodifiableList(tabArrayList);
        }
    }

    private static EmojiTab initResourceFace() {
        //根据资源ID去拿资源
        List<Emoji> emojis = new ArrayList<>();

        Resources resources = Application.getInstance().getApplicationContext().getResources();
        String packageName = Application.getInstance().getApplicationContext().getPackageName();
        for (int i = 1; i < 142; i++) {
            String drawFormat = String.format(Locale.ENGLISH, "face_base_%03d", i);
            String keyFormat = String.format(Locale.ENGLISH, "fb%03d", i);
            int resId = resources.getIdentifier(drawFormat, "drawable", packageName);
            if (resId == 0)
                continue;

            Emoji emoji = new Emoji(keyFormat, resId);
            emojis.add(emoji);
        }

        return new EmojiTab(emojis, "FACENAME");
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
            if (!fileCacheDir.exists()) {
                if (fileCacheDir.mkdirs()) {
                    InputStream stream = context.getAssets().open(assetsRes);

                    //取出的 字节流 要保存到一个地方
                    File tmpSourceFolder = new File(fileCacheDir, "source.zip");
                    //copy
                    StreamUtil.copy(stream, tmpSourceFolder);
                    //解压到文件夹
                    UnZipFile(tmpSourceFolder, fileCacheDir);

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

        EmojiTab tab = gson.fromJson(jsonReader, EmojiTab.class);

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
        for (Enumeration<? extends ZipEntry> entries = srcFile.entries(); entries.hasMoreElements(); ) {
            // ZipEntry 取出Entry
            ZipEntry zipEntry = entries.nextElement();
            String name = zipEntry.getName();

            //获得输入流 zf.getinputStream
            InputStream in = srcFile.getInputStream(zipEntry);

            //拼路径 存的路径
            String desZipFile = desPath + File.separator + name;

            //需要 String str支持中文。str 变成8859  然后用GB2313 编码
            String strChar = new String(desZipFile.getBytes("8859_1"), "GB2312");
            //复制到  目的文件夹
            File file = new File(strChar);

            StreamUtil.copy(in, file);
        }

    }

    /**
     * push表情到editText
     *
     * @param context
     * @param editText
     * @param emoji
     * @param size
     */
    public static void setInputEditTextFace(final Context context, final EditText editText, final Emoji emoji, final int size) {
        Glide.with(context)
                .load(emoji.preview)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(size, size) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        SpannableString spannableString = new SpannableString(String.format("[%s]", emoji.getKey()));
                        ImageSpan imageSpan = new ImageSpan(context, resource, ImageSpan.ALIGN_BASELINE);
                        spannableString
                                .setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        editText.append(spannableString);
                    }
                });
    }

    public static SpannableString decode(final SpannableString mData, final TextView txtContent, final float size) {

        if (TextUtils.isEmpty(mData)) {
            return mData;
        }

        String dataString = mData.toString();
        if (TextUtils.isEmpty(dataString)) {
            return mData;
        }

        //[ft108][ft107][ft114]
        // \[[^\[\]:\s\n]\]
        Pattern compile = Pattern.compile("\\[[^\\[\\]:\\s\\n]+\\]");
        Matcher matcher = compile.matcher(dataString);
        while (matcher.find()) {
            String matchString = matcher.group();
            // [
            String key = matchString.replaceAll("\\[", "").replaceAll("\\]", "");

            Emoji emoji = getEmoji(key);

            if (emoji == null)
                continue;

            int start = matcher.start();
            int end = matcher.end();

            EmojiSpan emojiSpan = new EmojiSpan(txtContent.getContext(), txtContent, emoji.getSource(), (int) size);

            mData.setSpan(emojiSpan,start,end,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return mData;
    }

    public static Emoji getEmoji(String key) {
        init();
        if (TextUtils.isEmpty(key))
            return null;
        Emoji emoji = arrayMap.get(key);
        return emoji;
    }

    public static class EmojiSpan extends IsoheightImageSpan {

        public EmojiSpan(Drawable d) {
            super(d);
        }

        public EmojiSpan(@NonNull Drawable d, int verticalAlignment) {
            super(d, verticalAlignment);
        }

        public EmojiSpan(@NonNull Drawable d, @NonNull String source) {
            super(d, source);
        }

        public EmojiSpan(@NonNull Drawable d, @NonNull String source, int verticalAlignment) {
            super(d, source, verticalAlignment);
        }

        public EmojiSpan(Context context, Uri uri) {
            super(context, uri);
        }

        public EmojiSpan(@NonNull Context context, @NonNull Uri uri, int verticalAlignment) {
            super(context, uri, verticalAlignment);
        }

        public EmojiSpan(@NonNull Context context, int resourceId) {
            super(context, resourceId);
        }

        public EmojiSpan(@NonNull Context context, int resourceId, int verticalAlignment) {
            super(context, resourceId, verticalAlignment);
        }

        public EmojiSpan(@NonNull Bitmap b) {
            super(b);
        }

        public EmojiSpan(@NonNull Bitmap b, int verticalAlignment) {
            super(b, verticalAlignment);
        }

        public EmojiSpan(@NonNull Context context, @NonNull Bitmap b) {
            super(context, b);
        }

        public EmojiSpan(@NonNull Context context, @NonNull Bitmap b, int verticalAlignment) {
            super(context, b, verticalAlignment);
        }

        private  int size;
        private Drawable mDrawable;

        public EmojiSpan(Context context, final View view, Object resource, final int Size) {
            super(context, R.drawable.default_face, ALIGN_BOTTOM);

            this.size = Size;

            //先得到Drawable 用Glide加载得到
            //Glide.with(context)
            //        .load(resource)
            //        .fitCenter()
            //        .into(new SimpleTarget<GlideDrawable>(size,size) {
            //            @Override
            //            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
            //                mDrawable = resource.getCurrent();
            //                // 获取自测量高宽  图片的高宽么？
            //                int width = mDrawable.getIntrinsicWidth();
            //                int height = mDrawable.getIntrinsicHeight();
            //                //设置mDrawable 的高宽
            //                mDrawable.setBounds(new Rect(0, 0, width > 0 ? width : Size, height > 0 ? height : Size));
            //
            //                //然后 刷新View
            //                view.invalidate();
            //
            //            }
            //        });


            GlidePreDrawable glidePreDrawable1 = new GlidePreDrawable();
            //new DrawableTarget(glidePreDrawable)

            Glide.with(context)
                    .load(resource)
                    .asGif()
                    .dontAnimate()
                    .into(new DrawableTarget(glidePreDrawable1,Size));
            mDrawable = glidePreDrawable1;

        }

        //@Override
        //public int getSize( Paint paint, CharSequence text, int start, int end,  Paint.FontMetricsInt fm) {
        //    Rect rect = mDrawable != null ? mDrawable.getBounds() : new Rect(0, 0, size, size);
        //    if (fm != null) {
        //        fm.ascent = -rect.bottom;
        //        fm.descent = 0;
        //
        //        fm.top = fm.ascent;
        //        fm.bottom = 0;
        //    }
        //
        //    return rect.right;
        //}

        @Override
        public Drawable getDrawable() {
            return mDrawable;
        }

        @Override
        public void draw( Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,  Paint paint) {
            if (mDrawable!=null)
            super.draw(canvas, text, start, end, x, top, y, bottom, paint);
        }
    }

    public static class EmojiTab {
        // emoji 表情list
        private List<Emoji> faces;
        private String name;
        private String preview;

        public EmojiTab(List<Emoji> faces, String name) {
            this.faces = faces;
            this.name = name;
        }

        public List<Emoji> getFaces() {
            return faces;
        }

        public void setFaces(List<Emoji> faces) {
            this.faces = faces;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public void copyToMap(ArrayMap<String, Emoji> arrayMap) {
            for (Emoji face : faces) {
                arrayMap.put(face.key, face);
            }
        }
    }

    public static class Emoji {

        private String desc;
        private String key;
        private Object preview;
        private Object source;

        public Emoji(String key, Object source) {
            this.key = key;
            this.source = source;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getPreview() {
            return preview;
        }

        public void setPreview(String preview) {
            this.preview = preview;
        }

        public Object getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }
}
