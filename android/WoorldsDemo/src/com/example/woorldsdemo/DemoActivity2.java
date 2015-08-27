package com.example.woorldsdemo;

import com.woorlds.woorldssdk.WoorldsSDK;
import com.woorlds.woorldssdk.WoorldsSDK.WoorldsEventsReceiver;
import com.woorlds.woorldssdk.client.WoorldInfo;
import com.woorlds.woorldssdk.client.WoorldsData;
import com.woorlds.woorldssdk.client.WoorldsData.ServerData;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DemoActivity2 extends Activity implements WoorldsEventsReceiver {
	private static String TAG = "DemoActivity2";
	private WoorldsSDK mWoorldsSDK;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mWoorldsSDK = new WoorldsSDK(this);
        mWoorldsSDK.registerWoorldsEvents(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		if (mWoorldsSDK != null) {
			mWoorldsSDK.destroy();
		}
	}

	@Override
	public void woorldsDataUpdated(WoorldsData woorldsData) {
        if (null != woorldsData.serverData) {
            ServerData woorlds = woorldsData.serverData;
            int i = 0;
            if (woorlds.wifiWorlds != null) {
            	boolean found = false;
                for (WoorldInfo woorld : woorlds.wifiWorlds) {
                	if (woorld.InWoorld) {
                		found = true;
                		Log.i(TAG, "currently in woorld " + woorld.displayName);
                	}
                }
                if (!found) {
                	Log.i(TAG, "Currently not in woorld");
                }
            }
        }
		
	}

	@Override
	public void woorldsError(String message) {
		Log.i(TAG, "Error has occured: " + message);
		
	}

}
