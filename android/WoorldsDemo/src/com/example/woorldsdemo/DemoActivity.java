package com.example.woorldsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.woorlds.woorldssdk.WoorldsSDK;
import com.woorlds.woorldssdk.WoorldsSDK.WoorldsEventsReceiver;
import com.woorlds.woorldssdk.client.WoorldsData;

public class DemoActivity extends Activity implements WoorldsEventsReceiver {
	private static String TAG = "WoordsTestActivity";

	WoorldsSDK mWoorldsSDK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mWoorldsSDK = new WoorldsSDK(this);
		mWoorldsSDK.registerWoorldsEvents(this);
	}

	@Override
	public void woorldsDataUpdated(WoorldsData woorldsData) {
		// insert some logic here
		
	}
	
	@Override
	public void woorldsError(String error) {
		Log.e(TAG, "Woorlds Error Message: " + error);
		
	}
	
}
