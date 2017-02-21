package br.com.edsilfer.android.starwarswiki.model

import br.com.edsilfer.android.starwarswiki.commons.util.SUID
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import org.json.JSONObject
import java.util.*

/**
 * Created by ferna on 2/18/2017.
 */

open class Character : RealmObject() {

    @PrimaryKey
    open var id: Long = SUID.id()
    open var name: String = ""
    open var height: Long = 0.toLong()
    open var mass: Long = 0.toLong()
    open var hair_color: String = ""
    open var skin_color: String = ""
    open var eye_color: String = ""
    open var birth_year: String = ""
    open var gender: String = ""
    open var created: Date = Date()
    open var edited: Date = Date()
    open var url: String = ""
    open var image_url: String = ""

    @Ignore
    open val films = mutableListOf<String>()

    override fun toString(): String {
        /*
        Because this class extends RealmObject, Gson can't be used on toString overriden
         */
        val json = JSONObject()
        json.put("id", id)
        json.put("name", name)
        json.put("height", height)
        json.put("mass", mass)
        json.put("hair_color", hair_color)
        json.put("skin_color", skin_color)
        json.put("eye_color", eye_color)
        json.put("birth_year", birth_year)
        json.put("gender", gender)
        json.put("created", created)
        json.put("edited", edited)
        json.put("url", url)
        return json.toString()
    }
}
