Woorlds SDK
============

Welcome to the Woorlds SDK Readme file.

* Installation Instructions

in order to use Woorlds you will need to include the supplied [jar](WoorldsDemo/libs/WoorldsSDK.jar) file in your project. please add it to your Java Build Path from your Project Properties (in eclipse) under Libraries.

the next step is to alter the AndroidManifest.xml.

The service requires the following permissions in order to function properly, please add them to your AndroidManifest.xml:
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

The service definition should be unique by its name so multiple instances of the service can co-exist:
    <service android:name="com.woorlds.woorldssdk.WoorldsService">
        <intent-filter>
             <action android:name="com.example.yourapp.serviceintent" />
        </intent-filter>
    </service>

The unique name should be notified in the manifest with a meta-data item:
    <meta-data
        android:name="com.woorlds.serviceintent"
        android:value="<b>com.example.yourapp.serviceintent</b>" />

The api key provided by woorlds should be specified in the manifest as well:
    <meta-data
        android:name="com.woorlds.ApiKey"
        android:value="32498ddc-f0de-4056-a77c-7dbaefe59536" />

To allow the service to start at device boot add the following receiver:
    <receiver android:name="com.woorlds.woorldssdk.StartupReceiver" >
        <intent-filter>
            <action android:name="android.intent.action.BOOT_COMPLETED" />
        </intent-filter>
    </receiver>

For reference use the full [AndroidManifest.xml](WoorldsDemo/AndroidManifest.xml).

For a working example please take a look at the [Demo Application](WoorldsDemo/src/com/example/woorldsdemo/DemoActivity.java)