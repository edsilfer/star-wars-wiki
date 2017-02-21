package br.com.edsilfer.android.starwarswiki.infrastructure

import android.util.Log
import br.com.edsilfer.android.starwarswiki.commons.util.Utils
import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.CallbackManager
import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.SWAPIEndPoint
import br.com.edsilfer.android.starwarswiki.model.ResponseWrapper
import br.com.edsilfer.android.starwarswiki.model.enum.EventCatalog
import br.com.edsilfer.kotlin_support.service.NotificationCenter.notify
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ferna on 2/18/2017.
 */

class Postman {

    private val TAG = Postman::class.simpleName

    fun readUrl(url: String) {
        getStarWarsAPIEndPoint().readPerson(url).enqueue(CallbackManager.CMCharacter())
    }

    fun readMovie(url: String) {

    }

    fun searchImage(
            query: String
    ) {
        val postman = br.com.edsilfer.android.searchimages.communication.Postman()

        postman.searchImage(
                query,
                onRequestSuccess = { urls ->
                    val wrapper = ResponseWrapper(true, urls)
                    notify(EventCatalog.e002, wrapper)
                },
                onRequestFailed = { message ->
                    val wrapper = ResponseWrapper(false, message)
                    notify(EventCatalog.e002, wrapper)
                }
        )
    }

    private fun getStarWarsAPIEndPoint(): SWAPIEndPoint {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .baseUrl("http://swapi.co/api/")
                .build()
                .create(SWAPIEndPoint::class.java)
    }

    private fun getTheMoviesDBEndPoint(): SWAPIEndPoint {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .baseUrl("http://swapi.co/api/")
                .build()
                .create(SWAPIEndPoint::class.java)
    }


}
