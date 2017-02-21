package br.com.edsilfer.android.searchimages.infrastructure

import br.com.edsilfer.android.searchimages.R

/**
 * Created by ferna on 2/20/2017.
 */
object ConfigurationManager {

    private var mAPIKey: String? = ""
    private var mApplicationKey: String? = ""


    fun setAPIKey(key: String) {
        mAPIKey = key
    }

    fun setApplicationKey(key: String) {
        mApplicationKey = key
    }

    fun getApplicationKey(): String {
        if (!isNull(mApplicationKey)) {
            return mApplicationKey!!
        }
        return ""
    }

    fun getAPIKey(): String {
        if (!isNull(mAPIKey)) {
            return mAPIKey!!
        }
        return ""
    }

    private fun isNull(value: String?): Boolean {
        if (value.isNullOrBlank()) {
            throw IllegalStateException("Neither API key or Application key can be null or blank")
        }
        return false
    }
}