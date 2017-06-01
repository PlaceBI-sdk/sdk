PlaceBI SDK for Android
=======================

This is the Android SDK for using PlaceBI offered capabilities

## Android Studio - Gradle

```
repositories{
  maven { url 'https://raw.github.com/PlaceBI-sdk/android-sdk/master' }
}

dependencies {

  compile 'com.woorlds:woorldssdk:1.0.36@aar'
  compile 'org.jetbrains.kotlin:kotlin-stdlib:1.0.2'
  compile 'com.google.code.gson:gson:2.4'

  //if placebi sdk version is heigher or equal to '1.0.29' (not including '1.0.33')
  compile 'com.google.android.gms:play-services-location:9.2.0'
}

```

## Manual

get the latest .aar from our repository on github: https://github.com/PlaceBI-sdk/android-sdk/tree/master/com/placebi/placebisdk/ and rename the .aar to .jar and extract it, then take within the classes.jar and rename it to placebisdk.jar to include it with the dependencies, which you can find on http://mvnrepository.com/ and download the files manually.

The following permissions are used in manifest.xml file

```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

if placebi sdk version is lower or equal to '1.0.28'

```xml
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
```

if placebi sdk version is heigher or equal to '1.0.29' (not including '1.0.33')

```xml
    <uses-permission android:name="com.google.android.gms.permission.ACTIVITY_RECOGNITION" />
```


if you are not using the library then you must attach the following system events to a receiver implemented in our libraries

```xml
<application >
    <receiver android:name="com.woorlds:woorldssdk.Receiver">
        <intent-filter>
            <action android:name="android.net.wifi.SCAN_RESULTS" />
            <action android:name="android.net.wifi.STATE_CHANGE" />
            <action android:name="com.woorlds.message" />
            <action android:name="com.woorlds.notification" />
            <action android:name="com.woorlds.notificationclicked" />
            <action android:name="com.woorlds.wakeupintent" />

            <!-- Optional (Added in version 1.0.35), take a look in #SDK Issue Messenger -->
            <action android:name="com.woorlds.issuemessenger" /> 
        </intent-filter>
    </receiver>
    <service android:exported="false" android:name="com.woorlds.woorldssdk.Service"/>

    <!-- from version 1.0.36 - required if targetSdkVersion >= 24 in build.gradle -->
    <service
            android:name="com.woorlds.woorldssdk.WoorldsJobServices"
            android:permission="android.permission.BIND_JOB_SERVICE"
            android:exported="false" />
</application>
```

## SDK Key

An SDK key must be provided in the manifest.xml

In order to identify your app you need to specify the api key in the manifest.
```xml
    <meta-data
        android:name="com.woorlds.ApiKey"
        android:value="your-api-key" />
```

If you have not received your api key yet please send us mail to <info@placebi.com> or go to http://www.placebi.com for the sign up process

## Android 6.0 and up

Android OS version 6.0 and up apps may need to ask the user for fine location permission,please make sure you ask for this permission as early as possible

```java
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 0);
```

## Instance

An instance of the placebi object must receive the context of your application but it has a small footprint.

```java
PlaceBI placebi = new PlaceBI(context);
```

for analyzing user's beahviour we need as well to signal an application close by calling destory() on pausing

```java
placebi.destroy();
```


## Campaigns

When you need to display an ad you should make sure you have an instance of the placebi object and call the getCampaign() method, the method returns a string which can be used to hint your system which information to present according to server set criteria.

```java
PlaceBI placebi = new PlaceBI(context);
String campaign = placebi.getCampaign();
showAd(campaign);
```

## Segment

Some users may alternatively receive segments instead

```java
  PlaceBI placebi = new PlaceBI(context);
  Set<String> segments = placebi.getSegmentations();
```

## Identity

If you want to identify the user with your own name or id, you should set this identity using:

```java
    placebi.identity("johndoe");
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
    placebi.track("user-action", data);
```

## Notifications

Notifications may be sent to to clients according to pre-set rules defined in http://dashboard.placebi.com for more information refer to our website

Server may push notifications according to rules defined in our dashboard. the small icon as defined by android notifications must be defined at least once, the value of the resource to be used as small icon will be persisted in shared preferences (without setting the small icon, notifications will not appear)

```java
 placebi.setNotificationSmallIcon(R.drawable.ic_launcher);
```

when a notification is clicked the default behavior is to start the default launcher activity. if you want to specify a different activity you may add the following meta data to your AndroidManifest.xml

```xml
    <meta-data
        android:name="com.woorlds.notificationintent"
        android:value="com.example.someintent" />
```

all notifications will be redirected to that activity's intent filter.

you may disable notifications for your application by calling to `placebi.setNotificationsEnabled(false)` or vice versa.

## Updates

A possible use case is to receive information about places as the user engage them, and receive raw information offered about that place. You may be notified using a local broadcast intent.

```xml
    <receiver android:name=".Receiver">
        <intent-filter>
            <action android:name="com.woorlds.update.intent" />
        </intent-filter>
    </receiver>
```

```java
private BroadcastReceiver placesUpdateReceiver =  new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        // code that uses data from placebi.getPlaces() / placebi.getMyPlace();
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
    LocalBroadcastManager.getInstance(this).registerReceiver(placesUpdateReceiver, new IntentFilter("com.placebi.update.intent"));
}
```

## Get place/places

In order to get user current place/places, you can call this methods

```java
    Collection<placebi.Place> places = placebi.getPlaces();
    //return Collection of places (include near by places) or empty Collection

    placebi.Place place = placebi.getMyPlace();
    //return placebi.Place or null
```


## Enabling/Disabling

Although we have means to disable the client activity from the server, you may
choose to disable it from the client side. The default behavior is enabled. 
Adding a meta-data value to the AndroidManifest.xml
can change it.

```xml
<meta-data
    android:name="com.woorlds.sdk.enabled.default"
    android:value="false" />
```

At runtime you may use the setEnabled/getEnabled to change/query the current
state.


```java
    placebi.setEnabled(true);
```

## SDK Issue Messenger

The app can recieve messages about issues that might afect the SDK operation at runtime.
There are messages about issues such as device location which might be disabled, permission problems etc.
The app may use these messages to notify the users 

```java
private BroadcastReceiver issueMessengerReceiver =  new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        int code = intent.getIntExtra("code", 0);
        //do something with code
    }
};

@Override
protected void onPause() {
    super.onPause();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(issueMessengerReceiver);
}

@Override
protected void onResume() {
    super.onResume();
    LocalBroadcastManager.getInstance(this).registerReceiver(issueMessengerReceiver, new IntentFilter("com.woorlds.issuemessenger"));
}
```

Code Mapping:
    - 102: Device Location is disabled
    - 103: No Location Permissions //from Android 6 and above


## Supporting Android SDK build numbers

Android OS versions 6.0 and up require to use permissions in a different way than earlier versions. 
Apps which do not support Android  6 (build versions 23 ) and cannot support methods such as 
ContextCompat.checkSelfPermission(...) can configure that in their Manifset file 
and set their value to less then '23'. In this case, the SDK ignores dangerous method calls
which may cause exceptions.
Please note: The default settings is for build version 23. 


```xml
<meta-data
    android:name="com.woorlds.sdk.androidsdk.support"
    android:value="23" />
```


## proguard-rules.pro
-dontwarn com.woorlds.woorldssdk.**

-keep class com.woorlds.woorldssdk.**
-keepclassmembers class com.woorlds.woorldssdk.** { *; }

-keep class com.google.android.gms.ads.identifier.**
-keepclassmembers class com.google.android.gms.ads.identifier.** { *; }


For a working example please take a look at the [Demo Application](PlaceBITest/app/src/main/java/woorlds/com/woorldstest/MainActivity.java)

If you have any questions, please write to <info@placebi.com>.
