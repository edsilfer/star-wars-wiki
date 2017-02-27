package br.com.edsilfer.android.starwarswiki.model

import br.com.edsilfer.android.starwarswiki.commons.util.SUID
import br.com.edsilfer.android.starwarswiki.model.dictionary.CharacterDictionary
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

/**
 * Created by ferna on 2/18/2017.
 */

open class Character : RealmObject() {

    companion object {
        fun parseDictionary(dictionary: CharacterDictionary): Character {
            val result = Character()

            result.id = dictionary.id
            result.name = dictionary.name
            result.height = dictionary.height
            result.mass = dictionary.mass
            result.hair_color = dictionary.hair_color
            result.skin_color = dictionary.skin_color
            result.eye_color = dictionary.eye_color
            result.birth_year = dictionary.birth_year
            result.gender = dictionary.gender
            result.created = dictionary.created
            result.edited = dictionary.edited
            result.url = dictionary.url
            result.image_url = dictionary.image_url

            for (f in dictionary.films) {
                result.films_urls.add(RealmString(f))
            }

            return result
        }
    }

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
    @SerializedName("movies")
    open var films: RealmList<Film> = RealmList()
    /*
    Realm doesn't accept list of primitive types yet, so we need a workaround in order to make it work
     */
    @SerializedName("films")
    open var films_urls: RealmList<RealmString> = RealmList()
    open var latitude: String = ""
    open var longitude: String = ""


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
        json.put("image_url", url)
        json.put("image_url", image_url)
        val _films = JSONArray()
        for (f in films) {
            _films.put(f.toString())
        }
        json.put("films", _films)
        return json.toString()
    }

}
