package br.com.edsilfer.android.starwarswiki.model

import io.realm.RealmObject

/**
 * Created by ferna on 2/21/2017.
 */

open class MovieUrl(
        open var url: String = ""
) : RealmObject() {
}
