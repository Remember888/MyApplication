package com.example.musicmanager;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import com.example.musicmanager.constant.IURL;
import com.example.musicmanager.entity.Music;
import java.util.ArrayList;
import java.util.List;


public class MyAppWidget extends AppWidgetProvider {
    public static List<Music> list;
    public static int position = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        if (list != null && list.size() > 0) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setViewVisibility(R.id.ib_play, View.GONE);
            views.setViewVisibility(R.id.ib_pause, View.VISIBLE);
            views.setViewVisibility(R.id.ib_next, View.VISIBLE);
            setView(context,views);
            appWidgetManager.updateAppWidget(appWidgetIds,views);
        }
    }

    private void setView(Context context, RemoteViews views) {
        Music music = list.get(position);
        views.setTextViewText(R.id.tv_singer_widget, music.getSinger());
        views.setTextViewText(R.id.tv_name_widget, music.getName());
        String musicPath = IURL.ROOT + music.getMusicpath();

        Intent intent_play = new Intent(IURL.PLAYMUSIC_ACTION);
        intent_play.putExtra("musicPath", musicPath);
        PendingIntent pending_play = PendingIntent.getBroadcast(context, 1, intent_play, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.ib_pause, pending_play);

        Intent intent_pause = new Intent(IURL.PAUSEMUSIC_ACTION);
        PendingIntent pending_pause = PendingIntent.getBroadcast(context, 1, intent_pause, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.ib_play, pending_pause);

        Intent intent_next = new Intent(IURL.PLAYNEXTWIDGET_ACTION);
        intent_next.putExtra("musicPath", musicPath);
        PendingIntent pendint_next = PendingIntent.getBroadcast(context, 1, intent_next, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.ib_next, pendint_next);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        ComponentName name = new ComponentName(context, MyAppWidget.class);
        String action = intent.getAction();
        if (action.equals(IURL.STARTWIDGET_ACTION)) {
            list = (ArrayList<Music>)intent.getSerializableExtra("list");
            Log.i("TAG:", "音乐加载完成:" + list.size());

        } else if (action.equals(IURL.UPPAUSEWIDGET_ACTION)) {
            views.setViewVisibility(R.id.ib_play, View.GONE);
            views.setViewVisibility(R.id.ib_pause, View.VISIBLE);
            String musicPath = intent.getStringExtra("musicPath");
            for (int i = 0; i < list.size(); i++) {
                String path = IURL.ROOT + list.get(i).getMusicpath();
                if (path.equals(musicPath)) {
                    position = i;
                    setView(context, views);
                    manager.updateAppWidget(name, views);
                    break;
                }
            }
        } else if (action.equals(IURL.UPPLAYER_ACTION)) {
            views.setViewVisibility(R.id.ib_play,View.VISIBLE);
            views.setViewVisibility(R.id.ib_pause, View.GONE);
            manager.updateAppWidget(name, views);

        } else if (action.equals(IURL.PLAYNEXT_ACTION)) {
            if (position < list.size() - 1) {
                position += 1;
            } else {
                position = list.size() - 1;
            }
            String musicPath = IURL.ROOT + list.get(position).getMusicpath();
            Intent intent1 = new Intent(IURL.PLAYMUSIC_ACTION);
            intent1.putExtra("musicPath", musicPath);
            context.sendBroadcast(intent1);

        }
    }
}

