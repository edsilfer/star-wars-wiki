package br.com.edsilfer.android.starwarswiki.infrastructure.database

import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.tyllt.infrastructure.database.GenericDAO
import io.realm.Realm

/**
 * Created by Edgar Fernandes on 02/03/2017
 */
object CharacterDAO : GenericDAO<Character>(Character::class.java, "id") {

    override fun list(): List<Character> {
        val realm = Realm.getDefaultInstance()
        return realm.where(mType).findAllSorted("creation_date").toList()
    }

    fun readLastAddedCharacter(): Character? {
        val list = list()
        if (list.isNotEmpty()) {
            return list[0]
        }
        return null
    }

}