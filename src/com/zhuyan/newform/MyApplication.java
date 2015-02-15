package com.zhuyan.newform;

import android.app.Application;

public class MyApplication  extends Application{
		
	@Override  
	public void onCreate() {  
	   	super.onCreate();  
	    CrashHandler crashHandler = CrashHandler.getInstance();  
	    crashHandler.init(getApplicationContext());  
	 }  
}
