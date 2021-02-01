package com.cordova.plugin;

import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileOutputStream;

public class WebCap extends CordovaPlugin {
  private static final String TAG = "WebCap";
  private CallbackContext callback = null;

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
  }

  void takeScreenshot(final Canvas cnv,final Bitmap bmp, final CallbackContext callbackContext, final String url,final String branding, final String name, final boolean ov) {
    this.cordova.getActivity().runOnUiThread(() -> {
      final WebView w = new WebView(this.cordova.getContext());

      // This is the important code :)
      w.setDrawingCacheEnabled(true);

      //width x height of your webview and the resulting screenshot
      w.measure(720, 1280);
      w.layout(0, 0, 720, 1280);
      w.setWebChromeClient(new WebChromeClient());
      w.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
      w.getSettings().setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1");
      if(ov){
        w.setBackgroundColor(0);
      }

      w.setWebViewClient(new WebViewClient() {

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
          callbackContext.error(description);
        }

        public void onPageFinished(WebView view, String url) {

          Picture picture = view.capturePicture();
          picture.draw(cnv);
          try {
            if(!ov) {
              takeScreenshot(cnv, bmp, callbackContext, branding, null, name, true);
            } else {
              ContextWrapper ctx = new ContextWrapper(view.getContext());
              FileOutputStream fos = new FileOutputStream(String.format("%s/%s.jpg", ctx.getFilesDir().getAbsoluteFile(), name));
              bmp.compress(Bitmap.CompressFormat.JPEG, 70, fos);
              fos.close();
              callbackContext.success(String.format("%s/%s.jpg", ctx.getFilesDir().getAbsoluteFile(), name));
            }
          } catch (Exception e) {
            e.printStackTrace();
            callbackContext.error("failed to save image.");
          }
        }
      });
      Log.e(TAG, "Loading image");
      w.loadUrl(url);
    });
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if (action.equals("takeScreenshot")) {
      final String url = args.getString(0);
      final String branding = args.getString(1);
      final Bitmap bmp = Bitmap.createBitmap(720, 1280, Bitmap.Config.ARGB_8888);
      final Canvas cnv = new Canvas(bmp);
      this.takeScreenshot(cnv, bmp, callbackContext, url, branding, "saved_screenshot", false);
    }
    return true;
  }
}
