
var exec = require('cordova/exec');

var PLUGIN_NAME = 'WebCap';

var WebCap = {
  takeScreenshot: function(opts, cb, err) {
    exec(cb, err, PLUGIN_NAME, 'takeScreenshot', [opts.url, opts.branding]);
  }
};

module.exports = WebCap;
