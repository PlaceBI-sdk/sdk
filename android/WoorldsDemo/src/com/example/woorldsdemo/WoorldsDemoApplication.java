package com.example.woorldsdemo;

import com.woorlds.woorldssdk.WoorldsSDK;

import android.app.Application;

public class WoorldsDemoApplication extends Application {
	public WoorldsSDK woorldsSDK;

	  @Override
	  public void onCreate()
	  {
	    super.onCreate();

	    woorldsSDK = new WoorldsSDK(this);
	  }
	  	 
}
