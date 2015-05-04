package com.example.woorldsdemo;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.woorlds.woorldssdk.WoorldsSDK;
import com.woorlds.woorldssdk.WoorldsSDK.WoorldsEventsReceiver;
import com.woorlds.woorldssdk.client.WoorldInfo;
import com.woorlds.woorldssdk.client.WoorldsData;

public class DemoActivity extends Activity {
	private static String TAG = "DemoActivity";
	private Button buttonToggleServiceState;
	private LinearLayout linearLayout;

	WoorldsSDK mWoorldsSDK;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		linearLayout = new LinearLayout(this);
		setContentView(linearLayout);
		buttonToggleServiceState = new Button(this);
		buttonToggleServiceState.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleServiceSettingState();
				
			}
		});
		setButtonText();
		linearLayout.addView(buttonToggleServiceState);
		
		setContentView(linearLayout);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		if (getServiceSettingState()) {
			mWoorldsSDK = new WoorldsSDK(this);
		}
	}

	// this is mandatory
	@Override
	protected void onStop() {
		super.onStop();
		if (mWoorldsSDK != null) {
			mWoorldsSDK.destroy();
		}
	}
	
	private boolean getServiceSettingState() {
		return getSharedPreferences("settings", MODE_PRIVATE).getBoolean("service_active", true);
	}
	
	private void toggleServiceSettingState() {
		boolean isRunning = getServiceSettingState();
		if (isRunning) {
			if (mWoorldsSDK != null) {
				mWoorldsSDK.stopService();
			}
		} else {
			mWoorldsSDK = new WoorldsSDK(this);
		}
		getSharedPreferences("settings", MODE_PRIVATE).edit().putBoolean("service_active", !isRunning).commit();
		setButtonText();
	}
	
	private void setButtonText() {
		buttonToggleServiceState.setText(getServiceSettingState() ? "kill service" : "start service");
	}
	
}
