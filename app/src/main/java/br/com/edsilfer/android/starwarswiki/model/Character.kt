package br.com.edsilfer.android.starwarswiki.model

import br.com.edsilfer.android.starwarswiki.commons.util.SUID
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
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

    @Ignore
    open val films = mutableListOf<String>()
}
