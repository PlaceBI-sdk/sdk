package com.example.woorldsdemo;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.woorlds.woorldssdk.WoorldsSDK;
import com.woorlds.woorldssdk.WoorldsSDK.WoorldsEventsReceiver;
import com.woorlds.woorldssdk.client.WoorldInfo;
import com.woorlds.woorldssdk.client.WoorldsData;

public class DemoActivity extends Activity {
	private static String TAG = "DemoActivity";

	WoorldsSDK mWoorldsSDK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mWoorldsSDK = new WoorldsSDK(this);
		mWoorldsSDK.registerWoorldsEvents(woorldsEventsReceiver);
	}

	// this is mandatory
	@Override
	protected void onStop() {
		super.onStop();
		mWoorldsSDK.destroy();
	}
	
	WoorldsEventsReceiver woorldsEventsReceiver = new WoorldsEventsReceiver() {
		
		@Override
		public void woorldsError(String arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void woorldsDataUpdated(WoorldsData woorldsData) {
			// TODO Auto-generated method stub
	        WoorldInfo inWoorld = null;
	        if (null != woorldsData.serverData && null != woorldsData.serverData.wifiWorlds) {
	            List<WoorldInfo> woorlds = woorldsData.serverData.wifiWorlds;
	            
	            // find the world we are in
	            for (WoorldInfo woorld : woorlds) {
	                if (woorld.InWoorld) {
	                    inWoorld = woorld;
	                }
	            }
	        }
	        if (inWoorld != null) {
	            Log.i(TAG, "We are in woorld: " + inWoorld.worldName);
	        }	// insert some logic here
			
		}
	};	
}
