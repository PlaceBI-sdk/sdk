Woorlds SDK for iOS
===================

This is the iOS SDK for using the Woorlds offered capabilities

## Cocoapods

a pod may be used as simple as adding

```
pod "WoorldsSDK", :git => 'https://github.com/woorlds-sdk/pod.git'
```

## Manual

If you are not using Cocoapods then a static library file can be found at https://github.com/woorlds-sdk/pod and the header file to be used with it

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

```java
    [woorldsSDK identify:@"johndoe@there.now"];
```

# Tracking

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

## Updates

A possible use case is to receive information about a places as the user engages them, and receive raw information offered about that place. You may be notified by providing a delegate instance which conforms to the WoorldsSDKDelegate protocol:

```objc
@protocol WoorldsSDKDelegate
@required
-(void)woorldsDataDidUpdate:(NSDictionary *)data;
@end
```
the data is a dictionary with a single "places" entry which is structured as list of places with this information for example

'''text
(
  "places":
    {
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

'''
