package com.mendelin.catpedia.data_access_layer.networking

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

typealias RetrofitError = (String?) -> Unit
typealias RetrofitSuccess = () -> Unit

class RetrofitCall<T>(val data: MutableLiveData<T>?, val onError: RetrofitError? = null, val onSuccess: RetrofitSuccess? = null) : Callback<ResponseObject<T>> {
    override fun onFailure(call: Call<ResponseObject<T>>, t: Throwable) {
        onError?.invoke(t.localizedMessage)
        data?.value = null
    }

    override fun onResponse(call: Call<ResponseObject<T>>, response: Response<ResponseObject<T>>) {
        if (response.isSuccessful) {
            if (response.body()?.status == true) {
                onSuccess?.invoke()
                data?.value = response.body()?.data
            } else {
                onError?.invoke(response.body()?.message)
                data?.value = null
            }
        } else {
            onError?.invoke(response.message())
            data?.value = null
        }

        Timber.e("Response: ${response.message()}")
        Timber.e(response.toString())
    }
}