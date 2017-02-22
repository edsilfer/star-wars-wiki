package br.com.edsilfer.android.starwarswiki.presenter.contracts

import android.view.View
import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.tyllt.infrastructure.database.CharacterDAO

/**
 * Created by ferna on 2/18/2017.
 */
interface HomepagePresenterContract {

    fun searchByQrcode(view: View)

    fun searchByUrl(view: View)

    fun onQRCodeRead(url: String)

    fun onCameraPermissionGranted()

    fun onForkMeClick()

    fun onCharacterClick(character: br.com.edsilfer.android.starwarswiki.model.Character)

    fun deleteCharacter(character: Character)

    fun onUrlType (url : String)
}