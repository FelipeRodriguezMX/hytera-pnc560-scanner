package com.hyterscan.hyterscan

import android.content.Context
import android.util.Log
import com.sim.scanner.ScannerManager.*
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.*

class ScanManagerHandler( context : Context) : MethodChannel.MethodCallHandler  {
    private var scanCallback: ((Int, String?, String) -> Unit)? = null
    private var ctx: Context? = context

    @OptIn(DelicateCoroutinesApi::class)
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "init"-> init()
            "scan" -> scan(result)
            "release" ->  GlobalScope.launch(Dispatchers.Main) {
                release(result)
            }
            "hasInstances" -> result.success(checkInstance())
            else -> result.notImplemented()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun init(){
           try {
               GlobalScope.launch(Dispatchers.Main) {
                   withContext(Dispatchers.Default) {
                       init(ctx)
                       listener()
                   }
               }
           }catch (e: Exception){
               e.message?.let { Log.d("Scanner", it) }
           }
    }

    private fun  checkInstance(): Boolean {
        if(getInstance() == null){
            return false
        }
        return true
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun listener(){
        getInstance().addScannerManagerListener(object : ScannerManagerListener {
            override fun Error(p0: Int, p1: String?) {
                GlobalScope.launch(Dispatchers.Main) {
                    scanCallback?.invoke(p0, p1, "ERROR")
                }
            }
            override fun decodeResult(p0: Int, p1: String?) {
                GlobalScope.launch(Dispatchers.Main) {
                    scanCallback?.invoke(p0, p1, "SUCCESS")
                }
            }
        })
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun release(result: MethodChannel.Result){
        if(getInstance() == null){
            Log.d("Scanner", "ScanManager not registered")
            result.error("Error", "No instance", "Instance is null")
            return
        }
        try {
            withContext(Dispatchers.Default) {
                getInstance().ReleaseScanner()
            }
            withContext(Dispatchers.Main) {
                result.success("Scanner released successfully")
            }
        } catch (e: Exception) {
            Log.d("Scanner", "Failed to release scanner ${e.message}")
            withContext(Dispatchers.Main) {
                result.error("Error", "Failed to release scanner", e.message)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun scan(result: MethodChannel.Result) {
        GlobalScope.launch(Dispatchers.IO) {
            val instance = getInstance()
            if (instance == null) {
                withContext(Dispatchers.Main) {
                    Log.d("Scanner", "ScanManager not registered")
                    result.error("Error", "No instance", "Instance is null")
                }
                return@launch
            }

            val openScannerStatus = instance.OpenScanner()
            if (openScannerStatus == BCR_ERROR) {
                withContext(Dispatchers.Main) {
                    result.error("Error", "Scanner open error", openScannerStatus)
                }
                return@launch
            }

            GlobalScope.launch(Dispatchers.Main) {
                scanCallback = { errorCode, scannedValue, status ->

                    if (status == "SUCCESS") {
                        result.success(scannedValue)
                    } else {
                        result.error("Error", "Scanner listener", errorCode)
                    }
                }
            }
        }
    }
}

