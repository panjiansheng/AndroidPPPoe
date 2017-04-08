package com.pan.pppoe;

import android.app.Application;
import android.os.Build.VERSION;

public class ContextUtil extends Application {
	private static ContextUtil instance;
	public static String VERSION_NOW="1.0.2";
	
	
	
	public static ContextUtil getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
	}
}
