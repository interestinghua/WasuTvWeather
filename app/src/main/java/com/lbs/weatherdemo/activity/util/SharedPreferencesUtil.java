package com.lbs.weatherdemo.activity.util;

import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesUtil {
	
	SharedPreferences sp;
	
	public SharedPreferencesUtil(SharedPreferences sp){
		this.sp=sp;
	}

	public void write(String city){
		SharedPreferences.Editor editor=sp.edit();
		editor.putString("city", city);
		editor.commit();
	}
	
	public Map read(){        
		Map<String, Object> map=new HashMap<String, Object>();	
		map.put("city", sp.getString("city", "null"));
		return map;
	}

}
