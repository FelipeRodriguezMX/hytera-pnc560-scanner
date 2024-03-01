package com.hyterscan.hyterscan

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel

class HyterscanPlugin: FlutterPlugin, ActivityAware {
  private var method : MethodChannel?= null
  override fun onAttachedToEngine(flutter: FlutterPlugin.FlutterPluginBinding) {
    method = MethodChannel(flutter.binaryMessenger, "scanner")
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {}

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    val handler = ScanManagerHandler(binding.activity.applicationContext)
    method?.setMethodCallHandler(handler)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    onDetachedFromActivity()
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    onAttachedToActivity(binding)
  }

  override fun onDetachedFromActivity() {
    method?.setMethodCallHandler(null)
    method = null
  }
}


