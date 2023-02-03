package com.example.hytera

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.sim.scanner.ScannerManager
import com.sim.scanner.ScannerManager.BCR_ERROR
import com.sim.scanner.ScannerManager.ScannerManagerListener
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class ScanManagerHandler() : MethodChannel.MethodCallHandler, EventChannel.StreamHandler {
    private var mEventSink: EventChannel.EventSink? = null

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "init"-> init()
            "start" -> scan(result)
            "getProps" -> props(result)
            "close" -> close()
            else -> result.notImplemented()
        }
    }

    private fun init(){
        ScannerManager.getInstance().addScannerManagerListener(object : ScannerManagerListener {
            override fun Error(p0: Int, p1: String?) {
                Log.d("ScannerPlugin", "Error $p0 $p1")
            }

            override fun decodeResult(p0: Int, p1: String?) {
                Handler(Looper.getMainLooper()).post(Runnable {
                    Log.d("ScannerPlugin", "decodeResult $p0 $p1")
//                    mEventSink?.success(p1)

                })
            }
        })
    }

    private fun scan(result: MethodChannel.Result) {
        val initScanner = ScannerManager.getInstance().initScanner()
        if(initScanner == BCR_ERROR){
            Log.d("ScannerPlugin", "initScanner $initScanner")
//            result.error("initScanner", "error initScanner $initScanner", null)
        }
        val openScannerStatus = ScannerManager.getInstance().OpenScanner()
        if ( openScannerStatus == BCR_ERROR) {
            Log.d("ScannerPlugin", "openScanner $openScannerStatus")
//            result.error("openScanner", "error openScanner $openScannerStatus", null)
        }
    }

    private fun close(){
        ScannerManager.getInstance().CloseScanner()
    }

    private fun props(result: MethodChannel.Result){
        val prop = ScannerManager.getInstance().prop
        Log.d("ScannerPlugin", "prop $prop")
        result.success(prop)
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        this.mEventSink = events
    }

    override fun onCancel(arguments: Any?) {
        this.mEventSink = null
    }
}
