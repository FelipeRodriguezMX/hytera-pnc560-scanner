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
        when (call.method) {
            "init"-> init()
            "scan" -> scan(result)
            "getProps" -> result.success(getInstance().prop)
            "close" -> getInstance().CloseScanner()
            "release" -> getInstance().ReleaseScanner()
            else -> result.notImplemented()
        }
    }

    private fun init(){
        init(ctx)
        getInstance().addScannerManagerListener(object : ScannerManagerListener {
            override fun Error(p0: Int, p1: String?) {
                mEventSink?.error("Error", p1, p0)
            }

            override fun decodeResult(p0: Int, p1: String?) {
                Handler(Looper.getMainLooper()).post {
                    mEventSink?.success(p1)
                }
            }
        })
    }



    private fun scan(result: MethodChannel.Result) {
        val initScanner = getInstance().initScanner()
        if(initScanner == BCR_ERROR) {
            result.error("Error", "Scanner init error", initScanner)
        }
        val openScannerStatus = getInstance().OpenScanner()
        if (openScannerStatus == BCR_ERROR) {
            result.error("Error", "Scanner open error", openScannerStatus)
        }

    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        this.mEventSink = events
    }

    override fun onCancel(arguments: Any?) {
        this.mEventSink = null
    }
}

