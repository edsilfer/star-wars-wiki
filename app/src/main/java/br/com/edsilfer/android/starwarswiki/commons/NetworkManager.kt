package br.com.edsilfer.android.starwarswiki.commons

import android.util.Log
import br.com.edsilfer.android.starwarswiki.model.EventMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by ferna on 2/18/2017.
 */

class NetworkManager {

    val TAG = NetworkManager::class.simpleName

    var onNetworkCallback: (response: EventMessage) -> Unit = { }

    fun enableListener() {
        try {
            EventBus.getDefault().register(this)
        } catch (e: Exception) {
            Log.e(TAG, "Unable to register to listen network events because client is already registered")
        }
    }

    fun disableListener() {
        EventBus.getDefault().unregister(this)
    }

    @org.greenrobot.eventbus.Subscribe(threadMode = ThreadMode.ASYNC)
    fun onNetworkResult(response: EventMessage) {
        onNetworkCallback(response)
    }
}
