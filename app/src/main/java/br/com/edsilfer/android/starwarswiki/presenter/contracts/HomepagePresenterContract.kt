package br.com.edsilfer.android.starwarswiki.presenter.contracts

import android.view.View

/**
 * Created by ferna on 2/18/2017.
 */
interface HomepagePresenterContract {

    fun onAddCharacterClick (view : View)

    fun onQRCodeRead (url : String)

}