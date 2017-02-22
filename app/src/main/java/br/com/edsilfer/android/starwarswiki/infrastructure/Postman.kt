package br.com.edsilfer.android.starwarswiki.infrastructure

import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.CallbackManager
import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.SWAPIEndPoint
import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.TMDBEndPoint
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
        getStarWarsAPIEndPoint().readMovie(url).enqueue(CallbackManager.CMMovie())
    }

    fun searchMovie(title: String) {
        getTheMoviesDBEndPoint().searchMovies("aa46f173e962dd2f43e9d2898fa98bcd", title).enqueue(CallbackManager.CMTMDBMovie())
    }

    fun searchImage(query: String) {
        br.com.edsilfer.android.searchimages.communication.Postman().searchImage(
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

    private fun getTheMoviesDBEndPoint(): TMDBEndPoint {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .baseUrl("http://api.themoviedb.org/3/")
                .build()
                .create(TMDBEndPoint::class.java)
    }
}
