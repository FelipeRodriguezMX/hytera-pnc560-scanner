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

class ScanManagerHandler( context : Context) : MethodChannel.MethodCallHandler  {
    private var scanCallback: ((Int, String?, String) -> Unit)? = null
    private var ctx: Context? = context
    private var instance:ScannerManager? = null

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
//            "init"-> init()
            "scan" -> scan(result)
            "release" -> release(result)
            "hasInstances" -> result.success(getInstance() != null)
            else -> result.notImplemented()
        }
    }

    private fun init(){
       if(instance == null){
           init(ctx)
           instance = getInstance()
       }
        instance?.addScannerManagerListener(object : ScannerManagerListener {
            override fun Error(p0: Int, p1: String?) {
                scanCallback?.invoke(p0, p1, "ERROR")
            }
            override fun decodeResult(p0: Int, p1: String?) {
                scanCallback?.invoke(p0, p1, "SUCCESS")
            }
        })
    }

    private fun release(result: MethodChannel.Result){
        if(instance == null){
            result.error("Error", "Could not release instance", "Instance is null")
            return
        }
        instance?.ReleaseScanner()
    }

    private fun scan(result: MethodChannel.Result) {
        init()
        val initScanner = instance?.initScanner()
        if(initScanner != BCR_SUCCESS) {
            result.error("Error", "Scanner init error", initScanner)
            return
        }
        val openScannerStatus = instance?.OpenScanner()
        if (openScannerStatus != BCR_SUCCESS) {
            result.error("Error", "Scanner open error", openScannerStatus)
            return
        }
        scanCallback = { errorCode, scannedValue, status ->
            if(status == "SUCCESS"){
                result.success(scannedValue)
            }else{
                result.error("Error", "Scanner listener", errorCode,)
            }
        }

    }
}

