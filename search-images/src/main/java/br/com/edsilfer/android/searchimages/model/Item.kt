package br.com.edsilfer.android.searchimages.model

import com.google.gson.annotations.SerializedName

/**
 * Created by ferna on 2/20/2017.
 */
data class Item(
        @SerializedName("pagemap")
        val pagemap: PageMap
)