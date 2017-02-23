package br.com.edsilfer.android.starwarswiki.model

import br.com.edsilfer.android.starwarswiki.commons.util.SUID
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.json.JSONObject

/**
 * Created by ferna on 2/22/2017.
 */
open class Film(
        open var url: Long = 0.toLong(),
        open var image_url: String = "",
        open var title: String = ""
) : RealmObject() {

    @PrimaryKey
    open var id: Long = SUID.id()

    override fun toString(): String {
        val json = JSONObject()
        json.put("image_url", image_url)
        json.put("title", title)
        return json.toString()
    }
}
