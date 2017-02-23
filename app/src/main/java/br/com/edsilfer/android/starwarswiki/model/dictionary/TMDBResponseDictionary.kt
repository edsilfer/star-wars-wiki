package br.com.edsilfer.android.starwarswiki.model.dictionary

import com.google.gson.Gson

/**
 * Created by ferna on 2/22/2017.
 */
class TMDBResponseDictionary {
    val vote_average = ""
    val backdrop_path = ""
    val adult = ""
    val id : Long = 0.toLong()
    val title = ""
    val overview = ""
    val original_language = ""
    val genre_ids = mutableListOf<String>()
    val release_date = ""
    val original_title = ""
    val vote_count = ""
    val poster_path = ""
    val video = ""
    val popularity = ""
    val homepage = ""

    override fun toString(): String {
        return Gson().toJson(this)
    }
}