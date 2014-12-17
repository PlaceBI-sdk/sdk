Woorlds SDK
============

Welcome to the Woorlds SDK Readme file.

in order to use Woorlds you will need to include the supplied [jar](WoorldsDemo/libs/WoorldsSDK.jar) file in your project. please add it to your Java Build Path from your Project Properties (in eclipse) under Libraries.

the next step is to alter the AndroidManifest.xml.

The service requires the following permissions in order to function properly, please add them to your AndroidManifest.xml:
```xml
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```
The service definition should be unique by its name so multiple instances of the service can co-exist:

```xml
    <service android:name="com.woorlds.woorldssdk.WoorldsService">
        <intent-filter>
             <action android:name="com.example.yourapp.serviceintent" />
        </intent-filter>
    </service>
```

The unique name should be notified in the manifest with a meta-data item:
```xml
    <meta-data
        android:name="com.woorlds.serviceintent"
        android:value="com.example.yourapp.serviceintent" />
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

Now that your project is set up lets see how to initialize:
```java
    mWoorlds = new WoorldsSDK();
```

Once it has been initialized you can subscribe to events by passing an events listener
```java
    WoorldsEventsReceiver eventsReceiver = new WoorldsEventsReceiver() {
        @Override
        public void woorldsError(String errorString) {
            // write it to log
            Log.e(TAG, "Woorlds Error: " + errorString);
            Toast.makeText(getApplicationContext(), "Error: " + errorString, Toast.LENGTH_SHORT).show();
        }
    
        @Override
        public void woorldsDataUpdated(WoorldsData woorldsData) {
            // check if we are inside a woorld
            WoorldInfo inWoorld = null;
            if (null != mWoorlds.serverData && null != mWoorlds.serverData.wifiWorlds) {
                List<WoorldInfo> woorlds = mWoorlds.serverData.wifiWorlds;
                // find the world we are in
                for (WoorldInfo woorld : woorlds) {
                    if (woorld.inWoorld) {
                        inWoorld = woorld;
                    }
                }
            }
            if (inWoorld != null) {
                Log.i(TAG, "We are in woorld: " + inWoorld.worldName);
            }
        }
    };

    // Register the event receiver
    mWoorlds.registerWoorldsEvents(eventsReceiver);
```

Update events will now be handled by the receiver

Tracking
========
Custom tracking events can be sent, and an identity may(or not) be specified. an Identity is persisted and can be called once per user identification, ofcourse it may be called again to update user's identity. An identity can be specified like this:

```java
    mWoorlds.identity("johndoe");
```

In order to create a custom tracking event you may specify event name, and additional data in a HashMap<String,Object>. Object can be any type of object which can be serialized using the jackson json serializer.
```java
    HashMap<String,Object> data = new HashMap<String,Object>();
    data.put("click", "button1");
    data.put("activity","welcome-screen");
    mWoorlds.track("user-action", data);
```
For a working example please take a look at the [Demo Application](WoorldsDemo/src/com/example/woorldsdemo/DemoActivity.java)
