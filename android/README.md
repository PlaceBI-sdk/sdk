Woorlds SDK for Android
=======================

This is the Android SDK for using the Woorlds offered capabilities

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
```

Android 6.0 Marshmallow users may need to ask the user for authorization for location services.

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

## Android Library

Will be available soon

## SDK Key

An SDK key must be provided in the manifest.xml

In order to identify your app you need to specify the api key in the manifest, <support@woorlds.com>
```xml
    <meta-data
        android:name="com.woorlds.ApiKey"
        android:value="your-api-key" />
```

If you have not received your api key yet please send us mail to <support@woorlds.com> or go to http://www.woorlds.com for the sign up process

## Instance

An instance of the Woorlds object must receive the context of your application but it has a small footprint.

```java
Woorlds woorlds = new Woorlds(context);
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
  Stringp[]
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

when a notification is clicked the default behavior is to start the default launcher activity. if you want to specify a different activity you may add the following meta data to your AndroidManifest.xml

```xml
    <meta-data
        android:name="com.woorlds.notificationintent"
        android:value="com.example.someintent" />
```
## Updates

A possible use case is to receive information about a places as the user engage them, and receive raw information offered about that place. You may be notified using an intent which you must define per application using a simple manifest key:

```xml
    <meta-data
        android:name="com.woorlds.update.intent"
        android:value="org.example.woorldsupdates" />
```

and register a receiver or declare a filter in the manifest

here's an example how to register in runtime
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
    unregisterReceiver(placesUpdateReceiver);
}

@Override
protected void onResume() {
    super.onResume();
    registerReceiver(placesUpdateReceiver, new IntentFilter("com.woorlds.UpdatesReceiver"));
}

```

then you may get the information parsed

```java
    Set<Woorlds.Place> places = woorlds.getParsedPlaces();
```

or if you wish you may parse it manually

```java
void parsePlaces() {
    for (Map<String, ?> place : woorlds.getPlaces()) {

        String brand_name = (String) place.get("brand_name");
        String display_name = (String) place.get("display_name");
        boolean in_place = (boolean) place.get("in_place");
        Set<String> tags = (Set<String>) place.get("tags");
        Map<String, ?> location = (Map<String, ?>) place.get("location");
        if (location != null) {
            double lat = (double) location.get("lat");
            double lng = (double) location.get("lng");
            String addr = (String) location.get("addr");
            float distance = (float) location.get("distance");
        }
    }
}
```

For a working example please take a look at the [Demo Application](WoorldsDemo/src/com/example/woorldsdemo/DemoActivity.java)

If you have any questions, please write to <support@woorlds.com>.
