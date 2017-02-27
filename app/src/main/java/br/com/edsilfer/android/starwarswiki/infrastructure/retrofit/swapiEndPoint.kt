package br.com.edsilfer.android.starwarswiki.infrastructure.retrofit

import br.com.edsilfer.android.starwarswiki.model.dictionary.CharacterDictionary
import br.com.edsilfer.android.starwarswiki.model.dictionary.MovieDictionary
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by ferna on 2/19/2017.
 */
interface SWAPIEndPoint {

    @GET
    fun readPerson(@Url url: String): Observable<CharacterDictionary>

    @GET
    fun readMovie(@Url url: String): Observable<MovieDictionary>

}