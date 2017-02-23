package br.com.edsilfer.android.starwarswiki.infrastructure.database

import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.tyllt.infrastructure.database.GenericDAO

/**
 * Created by Edgar Fernandes on 02/03/2017
 */
object CharacterDAO : GenericDAO<Character>(Character::class.java, "id") {
}