package com.cordova.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.pm.PackageManager;
import java.io.ByteArrayOutputStream;

import android.util.Log;

import java.io.FileOutputStream;
import android.net.Uri;
import android.content.Intent;
import android.app.Activity;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import androidx.core.content.FileProvider;
import java.io.InputStream;

public class IGStory extends CordovaPlugin {
  private static final String TAG = "WebCap";
  private CallbackContext callback = null;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (action.equals("takeScreenshot")) {
      final String url = args.getString(0);
      Thread t = new Thread(() -> {
        
      });
      t.start();
    }
    return true;
  }
}
