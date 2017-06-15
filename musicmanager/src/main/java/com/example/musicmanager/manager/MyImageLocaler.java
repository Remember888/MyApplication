package com.example.musicmanager.manager;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.musicmanager.util.StreamUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/6/5 0005.
 */

public class MyImageLocaler {
    public static LruCache<String, Bitmap> lruCache = null;

    static {
        int maxSize = (int) Runtime.getRuntime().maxMemory() / 8;
        lruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getHeight() * value.getRowBytes();
            }
        };
    }

    private OutputStream out;

    /**
     * 1.当Bitmap为空时，直接退出
     * 2.判断在内部存储中是否存在该图片
     * 3.判断在文件中是否存在该图片
     * 4.若都不存在时，启动异步
     * @param context
     * @param view
     * @param imageUri
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void setImageBitmap(Context context, ImageView view, String imageUri) {
        Bitmap bitmap = null;
        if (TextUtils.isEmpty(imageUri)) {
            return;
        }
        bitmap = getImageFromCache(imageUri);
        if (bitmap != null) {
            bitmap = ImageManager.getBitmap(context, bitmap);
            view.setImageBitmap(bitmap);
        }
        bitmap = getImageFromFile(context, view, imageUri);
        if (bitmap != null) {
            bitmap = ImageManager.getBitmap(context, bitmap);
            view.setImageBitmap(bitmap);
        }
        MyAsyncLoadTask asynmusic = new MyAsyncLoadTask(context, view);
        asynmusic.execute(imageUri);
    }

    /**
     * 思路：1.获得图片的名称
     *       2.获得内部存储的文件
     *       3.当文件不为空时，获得该文件中的目录文件，遍历，进而获得该目录文件的名称
     *       4.比较图片的名称和目录文件是否相同
     *       5.若相同时，获得Bitmap
     * @param context
     * @param view
     * @param imageUri
     * @return
     */
    private static Bitmap getImageFromFile(Context context, ImageView view, String imageUri) {
        Bitmap bitmap = null;
        String name = imageUri.substring(imageUri.lastIndexOf("/") + 1);
        File file = context.getCacheDir();
        if (file != null) {
            File[] file1 = file.listFiles();
            for (File file2 : file1) {
                String fileName = file2.getName();
                if (name.equals(fileName)) {
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    return bitmap;
                }
            }
        }

        return bitmap;
    }

    private static Bitmap getImageFromCache(String imageUri) {
        Bitmap bitmap = null;
        bitmap = lruCache.get(imageUri);
        return bitmap;
    }



    //实现异步图片加载的任务类
    private static class MyAsyncLoadTask extends AsyncTask<String,Void,Bitmap>{
        private Context mcontext;
        private ImageView miview;
        public MyAsyncLoadTask(Context context, ImageView view) {
            mcontext = context;
            miview = view;

        }
        @Override
        protected Bitmap doInBackground(String... params) {
            String path = params[0];
            Bitmap bitmap = null;
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setDoInput(true);
                conn.connect();

                int statusCode = conn.getResponseCode();
                if (statusCode == 200) {
                    InputStream is = conn.getInputStream();
                    bitmap = compressBitmap(is);

                    if (bitmap != null) {
                        lruCache.put(path, bitmap);
                        saveBitmapForFile(mcontext,bitmap,path);
                        return bitmap;
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            bitmap = ImageManager.getBitmap(mcontext, bitmap);
            miview.setImageBitmap(bitmap);
        }




    }


    private static Bitmap compressBitmap(InputStream is) {
        Bitmap bitmap;
        byte[] datas = StreamUtil.createBytes(is);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(datas, 0, datas.length, options);
        int width = options.outWidth;
        int height = options.outHeight;

        int targetWidth = 65;
        int targetHeight = 65;

        int blw = width / targetWidth;
        int blh = height / targetHeight;

        int radius = blw > blh ? blw : blh;
        if (radius <= 0) {
            radius = 1;
        }
        options.inSampleSize = radius;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length, options);
        return bitmap;
    }


    /**
     * 保存图片
     * 思路：
     * 1.获得内部存储的文件
     * 2.判断此抽象路径名表示的文件或目录是否存在，若不存在时，则创建一个
     * 3.获得图片的名称
     * 4.根据 parent 抽象路径名和 child 路径名字符串创建一个新 File 实例
     * 5.将其转换成输入流，并写位图的压缩版本指定的OutputStream。
     * 如果返回true，位图可通过使相应的InputStream BitmapFactory.decodeStream被重构（）。
     */
    private static void saveBitmapForFile(Context mcontext, Bitmap bitamp,String path) {
        OutputStream out = null;
        try {
            File cacheFile = mcontext.getCacheDir();
            if (cacheFile.exists()) {
                cacheFile.mkdir();
            }
            String fileName = path.substring(path.lastIndexOf("/") + 1);
            File file = new File(cacheFile, fileName);

            out =  new FileOutputStream(file);
            bitamp.compress(Bitmap.CompressFormat.JPEG, 100, out);
           /* format	Bitmap.CompressFormat：压缩图像的格式
              quality	int：提示压缩机，0-100。0小尺寸的意义压缩，100的意义压缩的最大质量。有些格式，如PNG是无损的，将忽略质量设置
              stream	OutputStream：OutputStream中写压缩数据。*/

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
