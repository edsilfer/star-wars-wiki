package br.com.edsilfer.android.starwarswiki.commons.util

import br.com.edsilfer.android.starwarswiki.infrastructure.App.Companion.getContext
import java.util.*


/**
 * Created by ferna on 2/20/2017.
 */

object Utils {

    private const val ARG_PROPERTIES_CONFIGURATION_FILE = "config.properties"
    private val mCachedProperties = mutableMapOf<String, String>()

    fun readProperty(key: String): String {
        if (!mCachedProperties.containsKey(key)) {
            val properties = Properties()
            val inputStream = getContext().assets.open(ARG_PROPERTIES_CONFIGURATION_FILE)
            properties.load(inputStream);
            val value = properties.getProperty(key)
            mCachedProperties.put(key, value)
            return value
        } else {
            return mCachedProperties[key]!!
        }
    }

}
