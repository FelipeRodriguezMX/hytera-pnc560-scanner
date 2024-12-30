package com.hyterscan.hyterscan

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel

class HyterscanPlugin : FlutterPlugin, ActivityAware {
    private var methodChannel: MethodChannel? = null
    private var scanManagerHandler: ScanManagerHandler? = null

    override fun onAttachedToEngine(flutterBinding: FlutterPlugin.FlutterPluginBinding) {
        methodChannel = MethodChannel(flutterBinding.binaryMessenger, "scanner")
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        methodChannel?.setMethodCallHandler(null)
        methodChannel = null
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        scanManagerHandler = ScanManagerHandler(binding.activity.applicationContext)
        methodChannel?.setMethodCallHandler(scanManagerHandler)
    }

    override fun onDetachedFromActivityForConfigChanges() {
        onDetachedFromActivity()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        onAttachedToActivity(binding)
    }

    override fun onDetachedFromActivity() {
        scanManagerHandler?.cancel()
        methodChannel?.setMethodCallHandler(null)
        scanManagerHandler = null
    }
}
