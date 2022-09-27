package com.example.scanner

import com.sim.scanner.ScannerManager

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** ScannerPlugin */
class ScannerPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    ScannerManager.init(flutterPluginBinding.applicationContext)
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "scanner")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall,  result: Result) {
    when (call.method) {
      "init" -> {
        ScannerManager.getInstance().initScanner()
        result.success("scan")
      }
      "open" -> {
        ScannerManager.getInstance().OpenScanner()
        result.success("stop")
      }
      "release" -> {
        ScannerManager.getInstance().ReleaseScanner()
        result.success("release")
      }
      "getProps" -> {
        result.success(ScannerManager.getInstance().prop)
      }
      else -> result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
