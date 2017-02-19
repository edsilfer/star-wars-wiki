package br.com.edsilfer.android.starwarswiki.infrastructure.retrofit

import android.util.Log
import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.edsilfer.android.starwarswiki.model.ResponseWrapper
import br.com.edsilfer.android.starwarswiki.model.enum.EventCatalog
import br.com.edsilfer.kotlin_support.service.NotificationCenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ferna on 2/19/2017.
 */

object CallbackManager {
    class CMCharacter : Callback<Character?> {
        val TAG = CMCharacter::class.simpleName

        override fun onFailure(call: Call<Character?>?, t: Throwable?) {
            NotificationCenter.notify(EventCatalog.e001, ResponseWrapper(false, null))
            Log.e(TAG, "Request failed. Cause: ${t!!.message}")
        }

        override fun onResponse(call: Call<Character?>?, response: Response<Character?>?) {
            if (response != null && response.isSuccessful) {
                val character = response.body()
                if (character != null) {
                    NotificationCenter.notify(EventCatalog.e001, ResponseWrapper(true, character))
                } else {
                    Log.e(TAG, "Character is null")
                }
            } else {
                Log.e(TAG, "Response is null or unsuccessful")
            }
        }
    }
}

