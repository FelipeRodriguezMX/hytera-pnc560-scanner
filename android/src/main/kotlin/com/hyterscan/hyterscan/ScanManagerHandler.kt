package com.hyterscan.hyterscan

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.sim.scanner.ScannerManager
import com.sim.scanner.ScannerManager.*
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class ScanManagerHandler( context : Context) : MethodChannel.MethodCallHandler, EventChannel.StreamHandler {
    private var mEventSink: EventChannel.EventSink? = null
    private var ctx: Context? = context

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        Log.d("ScanManagerHandler", "onMethodCall: ${call.method}")

        when (call.method) {
            "init"-> init()
            "scan" -> scan(result)
            "getProps" -> props(result)
            "close" -> close()
            else -> result.notImplemented()
        }
    }

    private fun init(){
        Log.d("ScanManagerHandler", "context: $ctx")

        ScannerManager.init(ctx)
        ScannerManager.getInstance().addScannerManagerListener(object : ScannerManagerListener {
            override fun Error(p0: Int, p1: String?) {
                Log.d("ScannerPlugin", "Error $p0 $p1")
            }

            override fun decodeResult(p0: Int, p1: String?) {
                Handler(Looper.getMainLooper()).post(Runnable {
                    Log.d("ScannerPlugin", "decodeResult $p0 $p1")
                })
            }
        })
    }

    private fun scan(result: MethodChannel.Result) {
        val initScanner = ScannerManager.getInstance().initScanner()
        if(initScanner == BCR_ERROR){
            Log.d("ScannerPlugin", "initScanner $initScanner")
        }
        val openScannerStatus = ScannerManager.getInstance().OpenScanner()
        if ( openScannerStatus == BCR_ERROR) {
            Log.d("ScannerPlugin", "openScanner $openScannerStatus")
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