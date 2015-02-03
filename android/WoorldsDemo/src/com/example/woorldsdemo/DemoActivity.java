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
	}

	// this is mandatory
	@Override
	protected void onStop() {
		super.onStop();
		mWoorldsSDK.destroy();
	}
	
}
