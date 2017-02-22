package br.com.edsilfer.android.starwarswiki.model.dictionary

import br.com.edsilfer.android.starwarswiki.commons.util.SUID
import com.google.gson.Gson
import java.util.*

/**
 * Created by ferna on 2/21/2017.
 */
class CharacterDictionary {
    var id: Long = SUID.id()
    var name: String = ""
    var height: Long = 0.toLong()
    var mass: Long = 0.toLong()
    var hair_color: String = ""
    var skin_color: String = ""
    var eye_color: String = ""
    var birth_year: String = ""
    var gender: String = ""
    var created: Date = Date()
    var edited: Date = Date()
    var url: String = ""
    var image_url: String = ""
    var films = mutableListOf<String>()

    override fun toString(): String {
        return Gson().toJson(this)
    }
}