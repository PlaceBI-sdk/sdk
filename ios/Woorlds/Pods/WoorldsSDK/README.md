vlc-sdk
=======

###Receiving a SDK Key

Request a SDK key at info@woorlds.com. same sdk-key used on android can be used.

###Installation

You can install install the SDK either manually or with CocoaPods (preffered).

#####Using CocoaPods

Add the following line to the podfile:
```
pod "WoorldsSDK", :git => "https://github.com/woorlds-sdk/pod.git"
```

#####Third Party Libraries

The SDK uses the following third party libraries which must be added manually.

AFNetworking
```
https://github.com/AFNetworking/AFNetworking
```


### Setting Up

the sdk uses location services and requires background mode so in order to work properly you have to enable it under
Capabilties -> Background Modes -> Location updates

as well as of iOS 8 you have to include the follwing two keys in your info.plist and specify the reason why you ask 
to use location(it can be empty as well):
```
  NSLocationAlwaysUsageDescription
  NSLocationWhenInUseUsageDescription
```

your info.plist should include the following key specifying the sdk key: woorldsSdkKey


###Usage


```objc
  #import <WoorldsSDK.h>
  
  woorldsSDK = [WoorldsSDK sharedInstance]
```

Get the sdk instance by calling [WoorldsSDK sharedInstance] which returns a singleton. All methods are called using this object.


#### Getting Segmentations
```objc
- (NSArray*)getSegmentation:(NSString*)campaignId;
```

in order to receive the current segmentations just call:
```
  [woorldsSDK getSegmentation:@"my-campaign-id"];
```

#### Tracking

You can track the follwing events:
```objc
- (void)trackDownload:(NSString*)campaignId;
- (void)trackInstall:(NSString*)campaignId;
- (void)trackClick:(NSString*)campaignId;
- (void)trackAction:(NSString*)campaignId;
```

