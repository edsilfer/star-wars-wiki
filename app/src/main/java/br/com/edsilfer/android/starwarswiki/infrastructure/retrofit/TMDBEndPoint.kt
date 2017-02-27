package br.com.edsilfer.android.starwarswiki.infrastructure.retrofit

import br.com.edsilfer.android.starwarswiki.model.dictionary.TMDBWrapperResponseDictionary
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ferna on 2/19/2017.
 */
interface TMDBEndPoint {

    @GET("search/movie/")
    fun searchMovies(@Query("api_key") key: String, @Query("query") name: String): Observable<TMDBWrapperResponseDictionary>

}