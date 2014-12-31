package com.example.woorldsdemo;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.woorlds.woorldssdk.WoorldsSDK;
import com.woorlds.woorldssdk.WoorldsSDK.WoorldsEventsReceiver;
import com.woorlds.woorldssdk.client.WoorldInfo;
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
	
	@Override
	public void woorldsError(String error) {
		Log.e(TAG, "Woorlds Error Message: " + error);
		
	}
	
}
