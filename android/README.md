Woorlds SDK for Android
=======================

This is the Android SDK for using the Woorlds offered capabilities

## Android Studio - Gradle

```
repositories{
  maven { url 'https://raw.github.com/woorlds-sdk/android-sdk/master' }
}

dependencies {
  compile "com.woorlds:woorldssdk:1.0.14@aar"
  compile "org.jetbrains.kotlin:kotlin-stdlib:1.0.0"
  compile 'com.google.code.gson:gson:2.4'
}

```


## Manual

include the main woorldsSDK.jar and its dependencies

The following permissions are used in manifest.xml file

```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

if you are not using the library then you must attach the following system events to a receiver implemented in our libraries

```xml
<application >
    <receiver android:name=".Woorlds$GeneralReceiver">
        <intent-filter>
            <action android:name="android.net.wifi.SCAN_RESULTS" />
            <action android:name="android.net.wifi.WIFI_STATE_CHANGED" />
            <action android:name="com.woorlds.message" />
        </intent-filter>
    </receiver>
</application>
```



## SDK Key

An SDK key must be provided in the manifest.xml

In order to identify your app you need to specify the api key in the manifest, <support@woorlds.com>
```xml
    <meta-data
        android:name="com.woorlds.ApiKey"
        android:value="your-api-key" />
```

If you have not received your api key yet please send us mail to <support@woorlds.com> or go to http://www.woorlds.com for the sign up process

## Marshmallow

Android 6.0 Marshmallow users may need to ask the user for authorization for fine location permission,please make sure you ask for this permission as early as possible


## Instance

An instance of the Woorlds object must receive the context of your application but it has a small footprint.

```java
Woorlds woorlds = new Woorlds(context);
```

for analyzing user's beahviour we need as well to signal an application close by calling destory() on pausing

```java
woorlds.destroy();
```


## Campaigns

When you need to display an ad you should make sure you have an instance of the Woorlds object and call the getCampaign() method, the method returns a string which can be used to hint your system which information to present according to server set criteria.

```java
Woorlds woorlds = new Woorlds(context);
String campaign = woorlds.getCampaign();
showAd(campaign);
```

## Segment

Some users may alternatively receive segments instead

```java
  Woorlds woorlds = new Woorlds();
  Set<String> segments = woorlds.getSegmentations();
```

## Identity

When your user provides some concrete identity you may attach that identity to the tracked events and activity using

```java
    woorlds.identity("johndoe");
```

## Tracking

A track is an event sent to server upon a sepecific event or user interaction of your application.

There are pre-defined tracking events that may be used by specifying a campaign id:

```java
    public void trackDownload(String campaignId);
    public void trackInstall(String campaignId);
    public void trackClick(String campaignId);
    public void trackAction(String campaignId);
```

In order to create a custom tracking event you may specify event name, and additional data in a HashMap<String,Object>. Object can be any type that a JSON serializer can handle.

```java
    HashMap<String,Object> data = new HashMap<String,Object>();
    data.put("click", "button1");
    data.put("activity","welcome-screen");
    woorlds.track("user-action", data);
```

## Notifications

Notifications may be sent to to clients according to pre-set rules defined in http://dashboard.woorlds.com for more information refer to our website

Server may push notifications according to rules defined in our dashboard. the small icon as defined by android notifications must be defined at least once, the value of the resource to be used as small icon will be persisted in shared preferences.

```java
 mWoorldsSDK.setNotificationSmallIcon(R.drawable.ic_launcher);
```

when a notification is clicked the default behavior is to start the default launcher activity. if you want to specify a different activity you may add the following meta data to your AndroidManifest.xml

```xml
    <meta-data
        android:name="com.woorlds.notificationintent"
        android:value="com.example.someintent" />
```

all notifications will be redirected to that activity's intent filter.

you may disable notifications for your application by calling to `woorlds.setNotificationsEnabled(false)` or vice versa.

## Updates

A possible use case is to receive information about places as the user engage them, and receive raw information offered about that place. You may be notified using a local broadcast intent. This ability requires extended permissions, contact us for this feature.

```java
private BroadcastReceiver placesUpdateReceiver =  new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        // code that uses data from woorlds.getPlaces()
    }
};

@Override
protected void onPause() {
    super.onPause();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(placesUpdateReceiver);
}

@Override
protected void onResume() {
    super.onResume();
    LocalBroadcastManager.getInstance(this).registerReceiver(placesUpdateReceiver, new IntentFilter("com.woorlds.update.intent"));
}

```

then you may get the information in this manner on the receiver

```java
    Collection<Woorlds.Place> places = woorlds.getPlaces();
```

For a working example please take a look at the [Demo Application](WoorldsTest/app/src/main/java/woorlds/com/woorldstest/MainActivity.java)

If you have any questions, please write to <support@woorlds.com>.
