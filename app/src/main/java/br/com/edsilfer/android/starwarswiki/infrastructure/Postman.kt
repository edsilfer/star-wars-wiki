package br.com.edsilfer.android.starwarswiki.infrastructure

import br.com.edsilfer.android.searchimages.communication.GCSAPIEndPoint
import br.com.edsilfer.android.starwarswiki.commons.util.Utils
import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.SWAPIEndPoint
import br.com.edsilfer.android.starwarswiki.infrastructure.retrofit.TMDBEndPoint
import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.edsilfer.android.starwarswiki.model.Film
import br.com.edsilfer.android.starwarswiki.model.ResponseWrapper
import br.com.edsilfer.android.starwarswiki.model.dictionary.CharacterDictionary
import br.com.edsilfer.android.starwarswiki.model.dictionary.MovieDictionary
import br.com.edsilfer.android.starwarswiki.model.dictionary.SearchResult
import br.com.edsilfer.android.starwarswiki.model.dictionary.TMDBWrapperResponseDictionary
import br.com.edsilfer.android.starwarswiki.model.enum.EventCatalog
import br.com.edsilfer.kotlin_support.service.NotificationCenter.notify
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


/**
 * Created by ferna on 2/18/2017.
 */

class Postman {

    private val TAG = Postman::class.simpleName
    private val ARG_APPLICATION_KEY = "gcs.application.key"
    private val ARG_API_ID = "gcs.api.key"

    fun searchCharacter(url: String) {
        var result: Character? = null
        getSWAPIEndPoint()
                .readPerson(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleObserver<CharacterDictionary>() {
                    override fun onSuccess(character: CharacterDictionary) {
                        result = Character.parseDictionary(character)
                        var count = 0
                        for (filmUrl in character.films) {
                            searchMovieInfo(result!!, filmUrl, count++ == character.films.size - 1)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        notify(EventCatalog.e001, ResponseWrapper(false, null))
                    }
                })
    }

    private fun searchCharacterImage(result: Character) {
        getGCSEndPoint()
                .searchImage(
                        "star wars ${result.name}",
                        Utils.readProperty(ARG_APPLICATION_KEY),
                        "jpg",
                        Utils.readProperty(ARG_API_ID)
                )
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleObserver<SearchResult>() {
                    override fun onSuccess(response: SearchResult) {
                        if (response.items != null && response.items.isNotEmpty()) {
                            val random = Random().nextInt(response.items.size)
                            result.image_url = response.items[random].pagemap.metatags[0].imageUrl
                        }
                    }

                    override fun onError(e: Throwable?) {
                        notify(EventCatalog.e001, ResponseWrapper(false, null))
                    }

                    override fun onComplete() {
                        notify(EventCatalog.e001, ResponseWrapper(true, result))
                    }
                })
    }

    private fun searchMovieInfo(result: Character, url: String, isLastRequest: Boolean) {
        getSWAPIEndPoint()
                .readMovie(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleObserver<MovieDictionary>() {
                    override fun onError(e: Throwable?) {
                    }

                    override fun onSuccess(response: MovieDictionary) {
                        searchMoviePoster(result, response.title, isLastRequest)
                    }
                })
    }

    private fun searchMoviePoster(result: Character, title: String, isLastRequest: Boolean) {
        getTMDBEndPoint()
                .searchMovies("aa46f173e962dd2f43e9d2898fa98bcd", title)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SimpleObserver<TMDBWrapperResponseDictionary>() {
                    override fun onError(e: Throwable?) {
                    }

                    override fun onSuccess(response: TMDBWrapperResponseDictionary) {
                        for (m in response.results) {
                            val posterUrl = "http://image.tmdb.org/t/p/original${m.backdrop_path}"
                            result.films.add(Film(m.id, posterUrl, m.title))
                            break
                        }
                    }

                    override fun onComplete() {
                        if (isLastRequest) {
                            searchCharacterImage(result)
                        }
                    }
                })
    }

    private fun getSWAPIEndPoint(): SWAPIEndPoint {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://swapi.co/api/")
                .build()
                .create(SWAPIEndPoint::class.java)
    }

    private fun getTMDBEndPoint(): TMDBEndPoint {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://api.themoviedb.org/3/")
                .build()
                .create(TMDBEndPoint::class.java)
    }

    private fun getGCSEndPoint(): GCSAPIEndPoint {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.googleapis.com/customsearch/")
                .build()
                .create(GCSAPIEndPoint::class.java)
    }

    abstract class SimpleObserver<T> : Observer<T> {
        override fun onSubscribe(d: Disposable?) {
        }

        override fun onComplete() {
        }

        override fun onNext(value: T) {
            onSuccess(value)
        }

        abstract fun onSuccess(response: T)
    }
}
