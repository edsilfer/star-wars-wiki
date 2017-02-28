package br.com.edsilfer.android.starwarswiki.infrastructure

import android.content.Context
import br.com.edsilfer.android.searchimages.communication.GCSAPIEndPoint
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.commons.util.Utils.readProperty
import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.EndPointFactory
import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.SWAPIEndPoint
import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.TMDBEndPoint
import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.edsilfer.android.starwarswiki.model.Film
import br.com.edsilfer.android.starwarswiki.model.ResponseWrapper
import br.com.edsilfer.android.starwarswiki.model.SimpleObserver
import br.com.edsilfer.android.starwarswiki.model.dictionary.CharacterDictionary
import br.com.edsilfer.android.starwarswiki.model.dictionary.MovieDictionary
import br.com.edsilfer.android.starwarswiki.model.dictionary.SearchResult
import br.com.edsilfer.android.starwarswiki.model.dictionary.TMDBWrapperResponseDictionary
import br.com.edsilfer.android.starwarswiki.model.enum.EventCatalog
import br.com.edsilfer.kotlin_support.service.NotificationCenter.notify
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 * Class responsible to receive all network requests, assemble them and dispatch success/erros callbacks to the listeners
 */
open class Postman {

    private val TAG = Postman::class.simpleName

    private val ARG_GCS_APPLICATION_KEY = "gcs.application.key"
    private val ARG_GCS_API_KEY = "gcs.api.key"
    private val ARG_TMDB_API_KEY = "tmdb.api.key"

    private val ARG_GSC_FILE_TYPE = "jpg"

    open fun searchCharacter(context: Context, url: String) {
        var result: Character? = null
        (EndPointFactory.getEndPoint(context, EndPointFactory.Type.STAR_WARS_API) as SWAPIEndPoint)
                .readPerson(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleObserver<CharacterDictionary>() {
                    override fun onSuccess(character: CharacterDictionary) {
                        result = Character.parseDictionary(character)
                        var count = 0
                        for (filmUrl in character.films) {
                            searchMovieInfo(context, result!!, filmUrl, count++ == character.films.size - 1)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        notify(EventCatalog.e001, ResponseWrapper(false, null))
                    }
                })
    }

    private fun searchCharacterImage(context: Context, result: Character) {
        (EndPointFactory.getEndPoint(context, EndPointFactory.Type.GOOGLE_CUSTOM_SEARCH_API) as GCSAPIEndPoint)
                .searchImage(
                        "star wars ${result.name}",
                        readProperty(context, ARG_GCS_APPLICATION_KEY),
                        ARG_GSC_FILE_TYPE,
                        readProperty(context, ARG_GCS_API_KEY)
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleObserver<SearchResult>() {
                    /*
                     ON ERROR IS NOT OVERRIDDEN ON PURPOSE CAUSE IF NO IMAGE IS FOUND WE STILL HAVE ENOUGH INFORMATION TO DISPLAY ON CHARACTER ROW
                     */
                    override fun onSuccess(response: SearchResult) {
                        if (response.items.isNotEmpty()) {
                            val random = Random().nextInt(response.items.size)
                            result.image_url = response.items[random].pagemap.metatags[0].imageUrl
                        }
                    }

                    override fun onComplete() {
                        notify(EventCatalog.e001, ResponseWrapper(true, result))
                    }
                })
    }

    private fun searchMovieInfo(context: Context, result: Character, url: String, isLastRequest: Boolean) {
        (EndPointFactory.getEndPoint(context, EndPointFactory.Type.STAR_WARS_API) as SWAPIEndPoint)
                .readMovie(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleObserver<MovieDictionary>() {
                    /*
                     ON ERROR IS NOT OVERRIDDEN ON PURPOSE CAUSE IF NO MOVIE INFO IS FOUND WE STILL HAVE ENOUGH INFORMATION TO DISPLAY ON CHARACTER ROW
                     */

                    override fun onSuccess(response: MovieDictionary) {
                        searchMoviePoster(context, result, response.title, isLastRequest)
                    }
                })
    }

    private fun searchMoviePoster(context: Context, result: Character, title: String, isLastRequest: Boolean) {
        (EndPointFactory.getEndPoint(context, EndPointFactory.Type.THE_MOVIE_DB_API) as TMDBEndPoint)
                .searchMovies(readProperty(context, ARG_TMDB_API_KEY), title)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleObserver<TMDBWrapperResponseDictionary>() {
                    /*
                     ON ERROR IS NOT OVERRIDDEN ON PURPOSE CAUSE IF NO IMAGE FOR FILM IS FOUND WE STILL HAVE ENOUGH INFORMATION TO DISPLAY ON CHARACTER ROW
                     */

                    override fun onSuccess(response: TMDBWrapperResponseDictionary) {
                        for (m in response.results) {
                            val posterUrl = context.getString(R.string.str_communication_film_poster_url, m.backdrop_path)
                            result.films.add(Film(m.id, posterUrl, m.title))
                            break
                        }
                    }

                    override fun onComplete() {
                        if (isLastRequest) {
                            searchCharacterImage(context, result)
                        }
                    }
                })
    }
}
