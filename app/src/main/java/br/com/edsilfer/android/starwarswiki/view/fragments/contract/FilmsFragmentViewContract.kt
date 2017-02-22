package br.com.edsilfer.android.starwarswiki.view.activities.contracts

import br.com.tyllt.view.contracts.BaseView

/**
 * Created by ferna on 2/18/2017.
 */
interface FilmsFragmentViewContract : BaseView {

    fun getFilmUrl () : String

    fun loadFilm (url : String, title : String)

}