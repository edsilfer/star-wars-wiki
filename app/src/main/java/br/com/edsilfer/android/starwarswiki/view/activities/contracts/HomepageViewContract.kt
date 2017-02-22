package br.com.edsilfer.android.starwarswiki.view.activities.contracts

import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.tyllt.view.contracts.BaseView

/**
 * Created by ferna on 2/18/2017.
 */
interface HomepageViewContract : BaseView {

    fun showErrorMessage(message: Int)

    /*
    Load cached character list on recycler view
     */
    fun loadCachedCharacter()

    /*
    Adds character on recycler view
     */
    fun addCharacter(character: Character)

    /*
    Removes character from recycler view
     */
    fun removeCharacter(character: Character)

    fun onCharacterClick(character: Character)

    /*
    Deleter character entry from database
     */
    fun deleteCharacter(character: Character)

    fun showGetUrlPopUp()

}