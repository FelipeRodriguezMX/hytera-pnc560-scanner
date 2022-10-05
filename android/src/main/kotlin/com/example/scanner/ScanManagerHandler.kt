package com.example.scanner


import android.util.Log
import com.sim.scanner.ScannerManager
import com.sim.scanner.ScannerManager.ScannerManagerListener
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class ScanManagerHandler: MethodChannel.MethodCallHandler, EventChannel.StreamHandler {

    private var mEventSink: EventChannel.EventSink? = null

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
//            "initScanner" -> result.success(ScannerManager.getInstance().initScanner())
            "start" -> start(result)
            "setParams" -> setParams(call, result)
            "getParams" -> getParams(call, result)
            "getProps" -> result.success(ScannerManager.getInstance().prop)
            "close" -> ScannerManager.getInstance().CloseScanner()
            "release" -> ScannerManager.getInstance().ReleaseScanner()
            else -> result.notImplemented()
        }
    }

    private fun setParams(call: MethodCall, result: MethodChannel.Result) {
        val number = call.argument<String>("parameterNumber")
        val value = call.argument<String>("value")
        val status = ScannerManager.getInstance().setParams(number, value)
        Log.d("ScannerPlugin", "setParams $status")
        result.success(status)
    }

    private fun getParams(call: MethodCall, result: MethodChannel.Result) {
        val number = call.argument<String>("parameterNumber")
        val status = ScannerManager.getInstance().getParams(number)
        result.success(status)
    }

    private fun start(result: MethodChannel.Result) {
        val openScannerStatus = ScannerManager.getInstance().OpenScanner()
        Log.d("ScannerPlugin", "openScanner $openScannerStatus")

        if (openScannerStatus == 0) {
            Log.d("ScannerPlugin", "test")
            ScannerManager.getInstance().addScannerManagerListener(object : ScannerManagerListener {
                override fun Error(p0: Int, p1: String?) {
                    Log.d("ScannerPlugin", "Error $p0 $p1")
                }

                override fun decodeResult(p0: Int, p1: String?) {
                    Log.d("ScannerPlugin", "decodeResult $p0 $p1")
                }
            })
            ScannerManager.getInstance()
        }

        ScannerManager.getInstance().addScannerManagerListener(object : ScannerManagerListener {
            override fun Error(p0: Int, p1: String?) {
                Log.d("ScannerPlugin", "Error $p0 $p1")
            }

            override fun decodeResult(p0: Int, p1: String?) {
                Log.d("ScannerPlugin", "decodeResult $p0 $p1")
            }
        })
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        this.mEventSink = events
    }

    override fun onCancel(arguments: Any?) {
        this.mEventSink = null
    }
}
