package br.com.edsilfer.android.starwarswiki.model

import io.realm.RealmObject

/**
 * Created by ferna on 2/21/2017.
 */
open class RealmString(
        open var string: String = ""
) : RealmObject() {

}