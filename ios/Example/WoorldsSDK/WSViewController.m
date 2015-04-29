//
//  WSViewController.m
//  WoorldsSDK
//
//  Created by Yehonatan Levi on 01/12/2015.
//  Copyright (c) 2014 Yehonatan Levi. All rights reserved.
//

#import "WSViewController.h"
#import <WoorldsSDK.h>

@interface WSViewController ()

@end

@implementation WSViewController  {
    WoorldsSDK *woorldsSDK;
    __weak IBOutlet UITableView *woorldsTableView;
    NSArray *woorlds;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    woorldsSDK = [WoorldsSDK sharedInstance];
    woorldsSDK.delegate = self;
    [woorldsSDK getSegmentation:@"my-campaign-id"];
    // Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(void)woorldsDataDidUpdate:(NSDictionary *)data {
    woorlds = data[@"woorlds"];
    [woorldsTableView reloadData];
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    NSLog(@"numberOfRowsInSection: %lu", (unsigned long)woorlds.count);
    return woorlds.count;
}

-(NSString*)getFullWoorldName:(NSDictionary *)woorld {
    return [NSString stringWithFormat:@"%@ - %@", woorld[@"woorldName"], woorld[@"subdomainName"]];
}

-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    static NSString *tableIdentifier = @"WoorldsNearbyTable";
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:tableIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleValue1 reuseIdentifier:tableIdentifier];
        cell.detailTextLabel.layer.cornerRadius = cell.detailTextLabel.frame.size.height / 2;
    }
    
    NSDictionary *woorld = woorlds[indexPath.row];
    
    cell.textLabel.text = [self getFullWoorldName:woorld];
    if ([woorld[@"inWoorld"] boolValue] == YES) {
        cell.imageView.image = [UIImage imageNamed:@"green.png"];
    } else {
        // show distance
        //cell.detailTextLabel.text =
    }
    return cell;
}

@end