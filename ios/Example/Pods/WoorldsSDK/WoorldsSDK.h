//
//  WoorldsSDK.h
//  Pods
//
//  Created by Yehonatan Levi on 1/13/15.
//
//

#ifndef Pods_WoorldsSDK_h
#define Pods_WoorldsSDK_h

#import <CoreLocation/CoreLocation.h>

@protocol WoorldsSDKDelegate
@required
-(void)woorldsDataDidUpdate:(NSDictionary *)data;
@end


@interface WoorldsSDK : NSObject <CLLocationManagerDelegate>
@property (nonatomic, assign) id <WoorldsSDKDelegate> delegate;
- (id) init;
+ (instancetype)sharedInstance;
- (NSArray*)getSegmentation:(NSString*)campaignId;
- (void)trackDownload:(NSString*)campaignId;
- (void)trackInstall:(NSString*)campaignId;
- (void)trackClick:(NSString*)campaignId;
- (void)trackAction:(NSString*)campaignId;

@end

#endif
