package br.com.edsilfer.android.starwarswiki.infrastructure.retrofit

import br.com.edsilfer.android.searchimages.communication.GCSAPIEndPoint
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.infrastructure.App
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class responsible for manage the creation of all end points used in the application
 */
object EndPointFactory {

    enum class Type {
        STAR_WARS_API,
        GOOGLE_CUSTOM_SEARCH_API,
        THE_MOVIE_DB_API
    }

    /**
     * Returns an end point based on the input parameter
     */
    fun getEndPoint(type: Type): Any {
        when (type) {
            Type.GOOGLE_CUSTOM_SEARCH_API -> return getGCSEndPoint()
            Type.STAR_WARS_API -> return getSWAPIEndPoint()
            Type.THE_MOVIE_DB_API -> return getTMDBEndPoint()
        }
    }

    private fun getSWAPIEndPoint(): SWAPIEndPoint {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(App.getContext().getString(R.string.str_communication_base_url_swapi))
                .build()
                .create(SWAPIEndPoint::class.java)
    }

    private fun getTMDBEndPoint(): TMDBEndPoint {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(App.getContext().getString(R.string.str_communication_base_url_tmdb))
                .build()
                .create(TMDBEndPoint::class.java)
    }

    private fun getGCSEndPoint(): GCSAPIEndPoint {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(App.getContext().getString(R.string.str_communication_base_url_gcs))
                .build()
                .create(GCSAPIEndPoint::class.java)
    }

}