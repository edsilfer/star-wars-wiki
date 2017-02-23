package br.com.edsilfer.android.starwarswiki.infrastructure.retrofit

/**
 * Created by ferna on 2/22/2017.
 */
object EndPointFactory {

    enum class Type {
        STAR_WARS_API,
        GOOGLE_CUSTOM_SEARCH_API,
        THE_MOVIE_DB_API
    }

    fun getEndPoint (type : Type) {
        when (type) {
            Type.GOOGLE_CUSTOM_SEARCH_API -> {}
            Type.STAR_WARS_API -> {}
            Type.THE_MOVIE_DB_API -> {}
        }
    }

}