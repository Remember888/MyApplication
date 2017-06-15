package com.example.newclient.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferUtils {
	public void setBoolean(Context context,String key,boolean deValue){
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean(key, deValue).commit();
	}
	public boolean getBoolean(Context context,String key,boolean Value){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getBoolean(key, Value);
	}
	
	
	public void setString(Context context,String key,String deValue){
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString(key, deValue).commit();
	}
	public String getString(Context context,String key,String value){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(key, value);
	}
	
	
	public void setInt(Context context,String key,int deValue){
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putInt(key, deValue).commit();
	}
	public int getInt(Context context,String key,int value){
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getInt(key, value);
	}

}
