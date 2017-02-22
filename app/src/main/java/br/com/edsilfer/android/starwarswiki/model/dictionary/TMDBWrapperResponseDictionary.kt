package br.com.edsilfer.android.starwarswiki.model.dictionary

import com.google.gson.Gson

/**
 * Created by ferna on 2/22/2017.
 */
class TMDBWrapperResponseDictionary {
    val results = mutableListOf<TMDBResponseDictionary>()
    val page = ""
    val total_pages = ""
    val total_results = ""

    override fun toString(): String {
        return Gson().toJson(this)
    }
}