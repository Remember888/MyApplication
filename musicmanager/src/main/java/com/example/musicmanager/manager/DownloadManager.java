package com.example.musicmanager.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.StringDef;

import com.example.musicmanager.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/7 0007.
 */

public class DownloadManager {
    public static void sendNotification(Context context,String ticket,String content,String title){
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.
                NOTIFICATION_SERVICE);

        Notification builder = new Notification.Builder(context)
                .setTicker(ticket)
                .setContentTitle(title)
                .setContentText(content)
                .setContentInfo(new SimpleDateFormat("HH:mm:ss").format(new Date()))
                .setSmallIcon(R.drawable.favo)
                .build();
        manager.notify(100,builder);
    }

    public static void downloadFile(final Context context, String musicPath, String name) {
        String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.
                DIRECTORY_DOWNLOADS).getAbsolutePath();
        final File file = new File(downloadPath, name);
        new AsyncTask<String, Void, File>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                sendNotification(context, "准备下载", "准备下载", "准备中");
            }

            @Override
            protected File doInBackground(String... params) {
                String path = params[0];
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setConnectTimeout(5000);
                    connection.connect();

                    int statusCode = connection.getResponseCode();
                    if (statusCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedInputStream inputStream1 = new BufferedInputStream(inputStream);
                        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                        int len = 0;
                        int downloadCound = 0;
                        int contentLenth = connection.getContentLength();
                        byte[] buffer = new byte[1024 * 8];
                        while ((len = inputStream1.read(buffer)) != -1) {
                            downloadCound += len;
                            sendNotification(context, "下载中", "下载百分比", downloadCound * 100 / contentLenth + "%");
                            outputStream.write(buffer, 0, buffer.length);

                        }
                        outputStream.flush();
                        outputStream.close();
                        inputStream1.close();
                        return file;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(File file) {
                sendNotification(context,"下载完毕",file.getName(),"文件下载完成");
            }
        }.execute(musicPath);
    }
    }

