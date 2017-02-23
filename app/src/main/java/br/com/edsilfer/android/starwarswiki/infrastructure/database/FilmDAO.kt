package br.com.edsilfer.android.starwarswiki.infrastructure.database

import br.com.edsilfer.android.starwarswiki.model.Film
import br.com.tyllt.infrastructure.database.GenericDAO

/**
 * Created by Edgar Fernandes on 02/03/2017
 */
object FilmDAO : GenericDAO<Film>(Film::class.java, "id") {
}