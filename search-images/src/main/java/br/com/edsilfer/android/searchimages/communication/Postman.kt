package br.com.edsilfer.android.searchimages.communication

import br.com.edsilfer.android.searchimages.infrastructure.ConfigurationManager
import br.com.edsilfer.android.searchimages.model.SearchResult
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ferna on 2/20/2017.
 */
class Postman {

    companion object {
        private const val ARG_FILE_TYPE = "jpeg"
    }

    fun searchImage(
            query: String,
            onRequestSuccess: (urls: MutableList<String>) -> Unit = {},
            onRequestFailed: (message: String?) -> Unit
    ) {
        getGCSAPIEndPoint().searchImage(
                query,
                ConfigurationManager.getApplicationKey(),
                ARG_FILE_TYPE,
                ConfigurationManager.getAPIKey()
        ).enqueue(object : Callback<SearchResult> {
            override fun onFailure(call: Call<SearchResult>?, t: Throwable?) = handleErrorCallback(onRequestFailed, t)
            override fun onResponse(call: Call<SearchResult>?, response: Response<SearchResult>?) = handleSuccessCallback(onRequestFailed, onRequestSuccess, response)
        })
    }

    private fun handleErrorCallback(onRequestFailed: (String?) -> Unit, t: Throwable?) = onRequestFailed(t?.message)

    private fun handleSuccessCallback(onRequestFailed: (String?) -> Unit, onRequestSuccess: (MutableList<String>) -> Unit, response: Response<SearchResult>?) {
        val result = mutableListOf<String>()
        if (response != null && response.isSuccessful) {
            val searchResult = response.body()
            for ((pagemap) in searchResult.items) {
                pagemap.metatags.mapTo(result) { it.imageUrl }
            }
            onRequestSuccess(result)
        } else {
            onRequestFailed("Request failed because response body is null or success flag is false")
        }
    }

    private fun getGCSAPIEndPoint(): GCSAPIEndPoint {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .baseUrl("https://www.googleapis.com/customsearch/")
                .build()
                .create(GCSAPIEndPoint::class.java)
    }
}