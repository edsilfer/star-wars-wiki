package br.com.edsilfer.android.searchimages.communication

import br.com.edsilfer.android.starwarswiki.model.dictionary.SearchResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ferna on 2/20/2017.
 */
interface GCSAPIEndPoint {

    companion object {
        private const val QUERY_ARG_QUERY = "q"
        private const val QUERY_ARG_APPLICATION_ID = "cx"
        private const val QUERY_ARG_FILE_TYPE = "fileType"
        private const val QUERY_ARG_API_ID = "key"
    }

    @GET("v1")
    fun searchImage(
            @Query(QUERY_ARG_QUERY) query: String,
            @Query(encoded = true, value = QUERY_ARG_APPLICATION_ID) applicationKey: String,
            @Query(QUERY_ARG_FILE_TYPE) fileType: String,
            @Query(QUERY_ARG_API_ID) key: String
    ): Observable<SearchResult>
}