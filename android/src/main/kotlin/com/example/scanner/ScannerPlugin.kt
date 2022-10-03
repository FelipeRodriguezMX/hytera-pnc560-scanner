package com.example.scanner


import com.sim.scanner.ScannerManager
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel

/** ScannerPlugin */
class ScannerPlugin: FlutterPlugin, ActivityAware {
  private var flutter: FlutterPlugin.FlutterPluginBinding ?= null
  private var method : MethodChannel ?= null
  private var event : EventChannel ?= null
  private var handler : ScanManagerHandler?= null
  private var activity : ActivityPluginBinding? = null

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    this.flutter = flutterPluginBinding
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    flutter = null
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    ScannerManager.init(this.flutter!!.applicationContext)
    activity = binding
    handler = ScanManagerHandler( activity!!.activity, flutter!!.textureRegistry, this.flutter!!)
    method = MethodChannel(flutter!!.binaryMessenger, "scanner")
    event = EventChannel(flutter!!.binaryMessenger, "scannerStream")
    method?.setMethodCallHandler(handler)
    event?.setStreamHandler(handler)
  }

  override fun onDetachedFromActivityForConfigChanges() {
    onDetachedFromActivity()
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    onAttachedToActivity(binding)
  }

  override fun onDetachedFromActivity() {
    event!!.setStreamHandler(null)
    method!!.setMethodCallHandler(null)
    method = null
    event = null
    handler = null
    activity = null
  }
}



