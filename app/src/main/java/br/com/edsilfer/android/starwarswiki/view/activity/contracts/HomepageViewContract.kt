package br.com.edsilfer.android.starwarswiki.view.activity.contracts

import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.tyllt.view.contracts.BaseView

/**
 * Created by ferna on 2/18/2017.
 */
interface HomepageViewContract : BaseView {

    fun showErrorMessage(message : Int)

    fun loadCachedCharacter ()

    fun addCharacter(character : Character)

}