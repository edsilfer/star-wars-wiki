package br.com.edsilfer.android.starwarswiki.infrastructure

import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.CallbackManager
import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.SWAPIEndPoint
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ferna on 2/18/2017.
 */

class Postman {

    fun readUrl(url: String) {
        getStarWarsAPIEndPoint().readPerson(url).enqueue(CallbackManager.CMCharacter())
    }

    private fun getStarWarsAPIEndPoint(): SWAPIEndPoint {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .baseUrl("http://swapi.co/api/")
                .build()
                .create(SWAPIEndPoint::class.java)
    }


}
