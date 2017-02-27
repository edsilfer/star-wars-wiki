package br.com.edsilfer.android.starwarswiki.infrastructure.retrofit

import android.util.Log
import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.edsilfer.android.starwarswiki.model.ResponseWrapper
import br.com.edsilfer.android.starwarswiki.model.dictionary.CharacterDictionary
import br.com.edsilfer.android.starwarswiki.model.dictionary.MovieDictionary
import br.com.edsilfer.android.starwarswiki.model.dictionary.TMDBWrapperResponseDictionary
import br.com.edsilfer.android.starwarswiki.model.enum.EventCatalog
import br.com.edsilfer.kotlin_support.service.NotificationCenter.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ferna on 2/19/2017.
 */

object CallbackManager {
    class CMCharacter : Callback<CharacterDictionary?> {
        val TAG = CMCharacter::class.simpleName

        override fun onFailure(call: Call<CharacterDictionary?>?, t: Throwable?) {
            notify(EventCatalog.e001, ResponseWrapper(false, null))
            Log.e(TAG, "Request failed. Cause: ${t!!.message}")
        }

        override fun onResponse(call: Call<CharacterDictionary?>?, response: Response<CharacterDictionary?>?) {
            if (response != null && response.isSuccessful) {
                val character = response.body()
                if (character != null) {
                    notify(EventCatalog.e001, ResponseWrapper(true, Character.parseDictionary(character)))
                } else {
                    Log.e(TAG, "Character is null")
                }
            } else {
                Log.e(TAG, "Response is null or unsuccessful")
            }
        }
    }

    class CMMovie : Callback<MovieDictionary?> {
        val TAG = CMMovie::class.simpleName

        override fun onFailure(call: Call<MovieDictionary?>?, t: Throwable?) {
            notify(EventCatalog.e003, ResponseWrapper(false, null))
            Log.e(TAG, "Request failed. Cause: ${t!!.message}")
        }

        override fun onResponse(call: Call<MovieDictionary?>?, response: Response<MovieDictionary?>?) {
            if (response != null && response.isSuccessful) {
                val movie = response.body()
                if (movie != null) {
                    notify(EventCatalog.e003, ResponseWrapper(true, movie))
                } else {
                    Log.e(TAG, "Movie is null")
                }
            } else {
                Log.e(TAG, "Response is null or unsuccessful")
            }
        }
    }

    class CMTMDBMovie : Callback<TMDBWrapperResponseDictionary?> {
        val TAG = CMTMDBMovie::class.simpleName

        override fun onFailure(call: Call<TMDBWrapperResponseDictionary?>?, t: Throwable?) {
            notify(EventCatalog.e003, ResponseWrapper(false, null))
            Log.e(TAG, "Request failed. Cause: ${t!!.message}")
        }

        override fun onResponse(call: Call<TMDBWrapperResponseDictionary?>?, response: Response<TMDBWrapperResponseDictionary?>?) {
            if (response != null && response.isSuccessful) {
                val movie = response.body()
                if (movie != null) {
                    notify(EventCatalog.e004, ResponseWrapper(true, movie))
                } else {
                    Log.e(TAG, "Movie is null")
                }
            } else {
                Log.e(TAG, "Response is null or unsuccessful")
            }
        }
    }
}

