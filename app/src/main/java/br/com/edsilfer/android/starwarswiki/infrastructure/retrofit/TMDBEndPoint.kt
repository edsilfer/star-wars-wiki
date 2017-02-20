package br.com.edsilfer.android.starwarswiki.infrastructure.retrofit

import br.com.edsilfer.android.starwarswiki.model.Character
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by ferna on 2/19/2017.
 */
interface TMDBEndPoint {

    @GET
    fun readMovie(@Url url: String): Call<Character>

}