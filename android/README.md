Woorlds SDK
============

Welcome to the Woorlds SDK Readme file.

In order to use Woorlds you will need to include the supplied jars in the [libs](WoorldsDemo/libs) folder in your project, if any of those libraries are already used by your project it is not needed to be added. please add it to your Java Build Path from your Project Properties (in eclipse) under Libraries.

the next step is to alter the AndroidManifest.xml.

The service requires the following permissions in order to function properly, please add them to your AndroidManifest.xml:
```xml
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```
The service definition should be unique by its name so multiple instances of the service can co-exist:

```xml
    <service android:name="com.woorlds.woorldssdk.WoorldsService"/>
```

The api key provided by woorlds should be specified in the manifest as well:
```xml
    <meta-data
        android:name="com.woorlds.ApiKey"
        android:value="your-api-key" />
```

Please contact info@woorlds.com for a unique key for your application.

To allow the service to start at device boot add the following receiver:
```xml
    <receiver android:name="com.woorlds.woorldssdk.StartupReceiver" >
        <intent-filter>
            <action android:name="android.intent.action.BOOT_COMPLETED" />
        </intent-filter>
    </receiver>
```
For reference please see the full [AndroidManifest.xml](WoorldsDemo/AndroidManifest.xml).


You must instantiate the WoorldsSDK class to ensure service is started and call destroy() when activity stops to ensure proper disconnection from the service:
```java
    @Override
    protected void onResume() {
        super.onResume();
        if (mWoorldsSDK == null) {
            mWoorldsSDK = new WoorldsSDK(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mWoorldsSDK != null) {
            mWoorldsSDK.destroy();
            mWoorldsSDK = null;
        }
    }

```

Campaigns
=========
whenever necessary you may query for a recommended campaign_id:
```java
    String campaignId = mWoorldsSDK.getCampaign();
```

When you fire the constructor new WoorldsSDK() you need to wait for the onConnected() event to fire in order to have a fresh campaign id:
so if you need to have the value ready as soon as an Activity starts you should:

```java
    @Override
    protected void onStart() {
        super.onStart();
        if (getServiceSettingState()) {
            mWoorldsSDK = new WoorldsSDK(this) {
                @Override
                public void onConnected() {                 
                    super.onConnected();
                    Log.i(TAG, "Current Campaign ID is: " + mWoorldsSDK.getCampaign()); 
                }
            };
        }
    }

```



Tracking
========
Custom tracking events can be sent, and an identity may(or not) be specified. an Identity is persisted and can be called once per user identification, ofcourse it may be called again to update user's identity. An identity can be specified like this:

```java
    mWoorlds.identity("johndoe");
```

There are pre-defined tracking events that may be used by specifying a campaign id:

```java
    public void trackDownload(String campaignId);
    public void trackInstall(String campaignId);
    public void trackClick(String campaignId);
    public void trackAction(String campaignId);
```

In order to create a custom tracking event you may specify event name, and additional data in a HashMap<String,Object>. Object can be any type of object which can be serialized using the jackson json serializer.
```java
    HashMap<String,Object> data = new HashMap<String,Object>();
    data.put("click", "button1");
    data.put("activity","welcome-screen");
    mWoorlds.track("user-action", data);
```
For a working example please take a look at the [Demo Application](WoorldsDemo/src/com/example/woorldsdemo/DemoActivity.java)


Woorlds Updates
===============
This requires special permissions - please contact us


```java
    @Override
    protected void onStart() {
        super.onStart();
        mWoorldsSDK = new WoorldsSDK(this);
    
        WoorldsEventsReceiver eventsReceiver = new WoorldsEventsReceiver() {
            @Override
            public void woorldsError(String errorString) {
                // write it to log
                Log.e(TAG, "Woorlds Error: " + errorString);
                Toast.makeText(getApplicationContext(), "Error: " + errorString, Toast.LENGTH_SHORT).show();
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
                }   // insert some logic here
                
            }
        };

        mWoorldsSDK.registerWoorldsEvents(eventsReceiver);
    }
```

