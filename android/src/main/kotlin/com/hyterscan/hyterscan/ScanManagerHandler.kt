package com.hyterscan.hyterscan

import android.content.Context
import android.util.Log
import com.sim.scanner.ScannerManager.*
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.*
import kotlinx.coroutines.CompletableDeferred

class ScanManagerHandler(context: Context) : MethodChannel.MethodCallHandler {
    private var scanCallback: ((Int, String?, String) -> Unit)? = null
    private var ctx: Context? = context
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "init" -> scope.launch {
                init(result)
            }
            "scan" -> scope.launch {
                scan(result)
            }
            "release" -> scope.launch {
                release(result)
            }
            "hasInstances" -> result.success(checkInstance())
            else -> result.notImplemented()
        }

    }

    fun cancel() {
        job.cancel()
    }

    private suspend fun init(result: MethodChannel.Result) {
        val deferredResult = CompletableDeferred<Unit>()

        scope.launch {
            try {
                withContext(Dispatchers.Default) {
                    init(ctx)
                    listener()
                }
                withContext(Dispatchers.Main) {
                    deferredResult.complete(Unit)  // Completes successfully
                    result.success("Initialization successful")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    deferredResult.completeExceptionally(e)  // Completes with an exception
                    result.error("Error", "Initialization failed", e.message)
                }
            }
        }

        // Await the deferred result (will be completed when the init operation is finished)
        deferredResult.await()
    }


    private fun checkInstance(): Boolean {
        return getInstance() != null
    }

    private fun listener() {
        getInstance()?.addScannerManagerListener(object : ScannerManagerListener {
            override fun Error(p0: Int, p1: String?) {
                scope.launch {
                    scanCallback?.invoke(p0, p1, "ERROR")
                }
            }

            override fun decodeResult(p0: Int, p1: String?) {
                scope.launch {
                    scanCallback?.invoke(p0, p1, "SUCCESS")
                }
            }
        })
    }

    private suspend fun release(result: MethodChannel.Result) {
        val deferredResult = CompletableDeferred<Unit>()

        val instance = getInstance()
        if (instance == null) {
            withContext(Dispatchers.Main) {
                Log.d("Scanner", "ScanManager not registered")
                deferredResult.completeExceptionally(Exception("No instance"))
                result.error("Error", "No instance", "Instance is null")
            }
            return
        }

        try {
            withContext(Dispatchers.Default) {
                instance.ReleaseScanner()
            }
            withContext(Dispatchers.Main) {
                deferredResult.complete(Unit)  // Completes successfully with no result
                result.success("Scanner released successfully")
            }
        } catch (e: Exception) {
            Log.d("Scanner", "Failed to release scanner: ${e.message}")
            withContext(Dispatchers.Main) {
                deferredResult.completeExceptionally(e)  // Completes with an exception
                result.error("Error", "Failed to release scanner", e.message)
            }
        }

        // Await the deferred result (will be completed when the release operation is finished)
        deferredResult.await()
    }

    private suspend fun scan(result: MethodChannel.Result) {
        val deferredResult = CompletableDeferred<String?>()

        val instance = getInstance()
        if (instance == null) {
            withContext(Dispatchers.Main) {
                Log.d("Scanner", "ScanManager not registered")
                deferredResult.completeExceptionally(Exception("No instance"))
                result.error("Error", "No instance", "Instance is null")
            }
            return
        }

        val initScanner = withContext(Dispatchers.Default) { instance.initScanner() }
        if (initScanner == BCR_ERROR) {
            withContext(Dispatchers.Main) {
                deferredResult.completeExceptionally(Exception("Scanner init error"))
                result.error("Error", "Scanner init error", initScanner)
            }
            return
        }

        val openScannerStatus = withContext(Dispatchers.Default) { instance.OpenScanner() }
        if (openScannerStatus == BCR_ERROR) {
            withContext(Dispatchers.Main) {
                deferredResult.completeExceptionally(Exception("Scanner open error"))
                result.error("Error", "Scanner open error", openScannerStatus)
            }
            return
        }

        // Set up the callback to complete the deferred result
        scanCallback = { errorCode, scannedValue, status ->
            if (status == "SUCCESS") {
                deferredResult.complete(scannedValue)
                result.success(scannedValue)
            } else {
                val error = Exception("Scanner listener error: $errorCode")
                deferredResult.completeExceptionally(error)
                result.error("Error", "Scanner listener", errorCode)
            }
        }

        // Wait for the result (will be completed by the callback)
        deferredResult.await()
    }



}


