package woorlds.com.woorldstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.woorlds.woorldssdk.Woorlds;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Woorlds woorlds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        woorlds = new Woorlds(this);
        woorlds.setNotificationSmallIcon(R.mipmap.ic_launcher);
    }

    private BroadcastReceiver placesUpdateReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Set<Woorlds.Place> places = woorlds.getPlaces();
            for (Woorlds.Place place : places) {
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(placesUpdateReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(placesUpdateReceiver, new IntentFilter("com.woorlds.example.UpdatesReceiver"));
    }
}
