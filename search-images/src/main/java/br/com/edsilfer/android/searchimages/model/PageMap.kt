package br.com.edsilfer.android.searchimages.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by ferna on 2/20/2017.
 */
data class PageMap(
        @SerializedName("metatags")
        val metatags: List<MetaTags>
) : Serializable