package by.paranoidandroid.cityweather.network.utils

import android.util.Log
import by.paranoidandroid.cityweather.Logger.TAG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Creating coroutines that perform network calls in background.
 */
suspend fun <T> Call<T>.await(): T = suspendCoroutine { block ->
    enqueue(object: Callback<T> {
        override fun onFailure(call: Call<T>, throwable: Throwable) =
                block.resumeWithException(throwable)
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                if (response.body() != null) {
                    block.resume(response.body()!!)
                } else {
                    val ex = IllegalStateException("Response body is null!")
                    Log.e(TAG, "Exception: ", ex)
                    block.resumeWithException(ex)
                }
            } else {
                val ex = IllegalStateException("Http error ${response.code()}")
                Log.e(TAG, "Exception: ", ex)
                block.resumeWithException(ex)
            }
        }
    })
}