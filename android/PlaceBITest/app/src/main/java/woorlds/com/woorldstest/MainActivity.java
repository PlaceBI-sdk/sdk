package woorlds.com.woorldstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.woorlds.woorldssdk.PlaceBI;

import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    PlaceBI placebi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        placebi = new PlaceBI(this);
        placebi.setNotificationSmallIcon(R.mipmap.ic_launcher);
    }

    private BroadcastReceiver placesUpdateReceiver =  new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Collection<PlaceBI.Place> places = placebi.getPlaces();
            for (PlaceBI.Place place : places) {
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
