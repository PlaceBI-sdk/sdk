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

@protocol WoorldsSDKLog
@required
-(void)logMessage:(NSString *)message;
@end



@interface WoorldsSDK : NSObject <CLLocationManagerDelegate>
@property (nonatomic, assign) id <WoorldsSDKDelegate> delegate;
@property (nonatomic, assign) id <WoorldsSDKLog> logDelegate;
- (id) init;
- (NSString*)logBuffer;
+ (instancetype)sharedInstance;
- (NSArray*)getSegmentation:(NSString*)campaignId;
- (NSString*)getCampaign;
- (void)identify:(NSString *)identity;
- (void)trackDownload:(NSString*)campaignId;
- (void)trackInstall:(NSString*)campaignId;
- (void)trackClick:(NSString*)campaignId;
- (void)trackAction:(NSString*)campaignId;
- (void)track:(NSString*)name withData:(NSDictionary*)data;

@end

#endif
