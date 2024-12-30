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
    private val job = SupervisorJob()  
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private var listenerRegistered = false  
    private var serviceBound = false 

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "init" -> scope.launch { init(result) }
            "scan" -> scope.launch { scan(result) }
            "release" -> scope.launch { release(result) }
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
                    deferredResult.complete(Unit)
                    result.success("Initialization successful")
                }
                serviceBound = true  // Mark service as bound
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    deferredResult.completeExceptionally(e)
                    result.error("Error", "Initialization failed", e.message)
                }
            }
        }

        deferredResult.await()
    }

    private fun checkInstance(): Boolean {
        return getInstance() != null
    }

    private fun listener() {
        if (!listenerRegistered) {  // Prevent duplicate listener registrations
            getInstance()?.addScannerManagerListener(object : ScannerManagerListener {
                override fun Error(errorCode: Int, message: String?) {
                    scope.launch { scanCallback?.invoke(errorCode, message, "ERROR") }
                }

                override fun decodeResult(resultCode: Int, data: String?) {
                    scope.launch { scanCallback?.invoke(resultCode, data, "SUCCESS") }
                }
            })
            listenerRegistered = true
        }
    }

    private suspend fun release(result: MethodChannel.Result) {
        val deferredResult = CompletableDeferred<Unit>()

    // Use a scoped coroutine to ensure proper execution context
    withContext(Dispatchers.Main) {
        val instance = getInstance()
        if (instance == null) {
            result.error("Error", "No instance", "Instance is null")
            deferredResult.completeExceptionally(Exception("No instance"))
            return@withContext
        }

        try {
            if (serviceBound) { // Check if the service is still bound
                listenerRegistered = false

                // Ensure the ReleaseScanner() call is made on the Default dispatcher
                withContext(Dispatchers.Default) {
                    instance.ReleaseScanner()
                }

                // Mark service as unbound and complete the result
                serviceBound = false  
                deferredResult.complete(Unit)
                result.success("Scanner released successfully")
            } else {
                result.success("Service already unbound")
            }
        } catch (e: Exception) {
            deferredResult.completeExceptionally(e)
            result.error("Error", "Failed to release scanner", e.message)
        }
    }

    // Await completion of the deferred result
    deferredResult.await()
    }

    private suspend fun scan(result: MethodChannel.Result) {
        val deferredResult = CompletableDeferred<String?>()

        val instance = getInstance()
        if (instance == null) {
            withContext(Dispatchers.Main) {
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

        deferredResult.await()
    }
}
