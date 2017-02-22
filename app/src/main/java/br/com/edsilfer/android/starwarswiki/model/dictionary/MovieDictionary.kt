package br.com.edsilfer.android.starwarswiki.model.dictionary

import br.com.edsilfer.android.starwarswiki.commons.util.SUID
import com.google.gson.Gson

/**
 * Created by ferna on 2/21/2017.
 */
class MovieDictionary {
    var id: Long = SUID.id()
    val edited = ""
    val starships = mutableListOf<String>()
    val species = mutableListOf<String>()
    val episode_id = ""
    val opening_crawl = ""
    val director = ""
    val url = ""
    val planets = mutableListOf<String>()
    val title = ""
    val created = ""
    val producer = ""
    val release_date = ""
    val vehicles = mutableListOf<String>()
    val characters = mutableListOf<String>()

    override fun toString(): String {
        return Gson().toJson(this)
    }
}