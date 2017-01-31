Woorlds SDK for iOS
===================

This is the iOS SDK for using the Woorlds offered capabilities

## Cocoapods

a pod may be used as simple as adding

```
pod "WoorldsSDK", :git => 'https://github.com/woorlds-sdk/pod.git'
```

## Manual

If you are not using Cocoapods then a static library file can be found at https://github.com/woorlds-sdk/pod and the header file to be used with it.
Please make sure the frameworks defined in the podspec are added to your project's Linked Frameworks and Libraries section.

## SDK key

An SDK key must be provided in the `info.plist` file of your application with the following key:

```xml
<key>woorldsSdkKey</key>
<string>your-api-key</string>
```

If you have not received your api key yet please send us mail to <support@woorlds.com> or go to http://www.woorlds.com for the sign up process

## Requirements

Location always authorization must be required from users, our SDK calls `requestAlwaysAuthorization` upon its initialization process which happens once using a singleton type `sharedInstance` static method. As documented this as well requires adding two more keys in the `info.plist` file of your application with the following keys

```xml
<key>NSLocationAlwaysUsageDescription</key>
<string></string>
<key>NSLocationWhenInUseUsageDescription</key>
<string></string>
```

Background Mode should also be enabled for location updates.


For versions 0.3.2 and above, need to add

```xml
<key>NSMotionUsageDescription</key>
<string></string>
```


##  Transport Security

please follow the instructions on this link to override security settings, we will supply a secure transport very soon.

use the information here to override it:
http://stackoverflow.com/questions/31254725/transport-security-has-blocked-a-cleartext-http

## Instance

the class implemented in the library is defined in the `woorldsSDK.h` file

```objc
#import <WoorldsSDK.h>
```

at a point as early as possible in your app you should acquire an instance of the `WoorldsSDK` class

```objc
WoorldsSDK *woorldsSDK = [WoorldsSDK sharedInstance];
```

## Campaigns

When you need to display an ad you should make sure you have an instance of the Woorlds object and call the `[woorldsSDK getCampaign]` method, the method returns a string which can be used to hint your system which information to present according to server set criteria, here's an example:

```objc
NSString *campaignId = [woorldsSDK getCampaign];
```

## Identity

When your user provides some concrete identity you may attach that identity to the tracked events and activity using

```objc
    [woorldsSDK identify:@"johndoe@there.now"];
```

## Start/Stop SDK at runtime

When you want to start/stop the SDK at runtime

```objc
[woorldsSDK startSdk];
[woorldsSDK stopSdk];
```


## Debug Mode

In order to examine SDK logs, you can set debug mode to true

```objc
[woorldsSDK setDebugMode:YES]; // Defualt is NO
```


## Tracking

A track is an event sent to server upon a specific event or user interaction of your
application.

There are pre-defined tracking events that may be used by specifying a campaign id:

```objc
- (void)trackDownload:(NSString*)campaignId;
- (void)trackInstall:(NSString*)campaignId;
- (void)trackClick:(NSString*)campaignId;
- (void)trackAction:(NSString*)campaignId;
```

In order to create a custom tracking event you may specify event name, and additional data as NSDictionary, value types be any type that a JSON serializer can handle such as in this example:

```objc
[woorldsSDK track:@"clicked" :@{@"button-name": @"send", @"section": @"news"}]
```

In order to let us keep tracking application status, please implement to following in your AppDelegate file

```objc
- (void)applicationDidEnterBackground:(UIApplication *)application {
  WoorldsSDK *woorldsSDK = [WoorldsSDK sharedInstance];
  [woorldsSDK trackAppState:NO];
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
  WoorldsSDK *woorldsSDK = [WoorldsSDK sharedInstance];
  [woorldsSDK trackAppState:YES];
 }
```


## Notifications

in order to support notification analytics please incorporate the following to your AppDelegate code:

```swift
func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
    woorlds?.processLaunchOptions(launchOptions)
    return true
}

func application(application: UIApplication, didReceiveLocalNotification notification: UILocalNotification) {
    woorlds?.processUserInfo(notification.userInfo)
}
```

```objc

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  if (application.applicationState == UIApplicationStateActive ) {
    [[WoorldsSDK sharedInstance] processLaunchOptions:launchOptions];
  }
  return YES;
}
- (void)application:(UIApplication *)application didReceiveLocalNotification:(UILocalNotification *)notification {
  if (application.applicationState == UIApplicationStateActive ) {
    [[WoorldsSDK sharedInstance] processUserInfo:notification.userInfo];
  }
}

```

On every notification sent from the SDK, you can listen to the event as following:

1. Via observer:

```objc
[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(notificationReceived:) name:@"woorldsNotification" object:nil];

-(void)notificationReceived:(NSNotification *)notification {
    if ([notification.name isEqualToString:@"notificationSent"]) {
        NSDictionary *notifInfo = notification.userInfo;
        // do something with notifInfo
    }
}

```

2. Via delegate (this delegate set to run on the main thread):

```objc
@interface MyViewController : UIViewController <WoorldsNotificationDelegate>

- (void)viewDidLoad
{
    ...
    woorldsSDK = [WoorldsSDK sharedInstance];
    woorldsSDK.delegate = self;
}

//version <= 0.3.3
-(void)notificationSent:(NSDictionary *)notifInfo {
    // do something with notifInfo
}

//version >= 0.3.4
-(void)notificationSent:(NSString *)notifInfo {
    // do something with notifInfo
}

```

Disable/Enable notifications at runtime

```objc
[woorldsSDK setNotificationsEnabled:<true/false>];

```


## Updates

A possible use case is to receive information about a places as the user engages them, and receive raw information offered about that place. You may be notified by providing a delegate instance which conforms to the WoorldsSDKDelegate protocol:

```objc
[[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(placesUpdated:) name:@"placesUpdated" object:nil];

-(void)placesUpdated:(NSNotification *)notification {
    if ([notification.name isEqualToString:@"woorldsUpdate"]) {
        NSArray *places = [woorldsSDK getPlaces];
        // do something with places
    }
}

```
the data is an array of dictionaries each is a place

```
[    {
  "in_place" = true;
  "brand_name" = apple;
  "display_name" = "5_infinite_loop__cupertino";
  location =             {
      addr = "5 Infinite Loop, Cupertino, CA 95014, USA";
      distance = 181;
      lat = "37.331696";
      lng = "-122.028964";
  };
  tags =             (
      "electronics_store"
  );
, ...]
```
