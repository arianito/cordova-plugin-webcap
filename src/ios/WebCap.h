#import <Cordova/CDVPlugin.h>

@interface WebCap : CDVPlugin {
}

- (void)takeScreenshot:(CDVInvokedUrlCommand *)command;

@end
