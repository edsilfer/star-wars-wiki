package br.com.tyllt.infrastructure.database

import br.com.edsilfer.android.starwarswiki.model.Character

/**
 * Created by Edgar Fernandes on 02/03/2017
 */
object CharacterDAO : GenericDAO<Character>(Character::class.java, "id") {
}