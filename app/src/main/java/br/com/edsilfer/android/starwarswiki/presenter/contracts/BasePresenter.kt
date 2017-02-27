package br.com.edsilfer.android.starwarswiki.presenter.contracts

import br.com.edsilfer.android.starwarswiki.commons.NetworkManager
import br.com.edsilfer.android.starwarswiki.model.EventMessage
import br.com.tyllt.view.contracts.BaseView

/**
 * Created by User on 01/01/2017.
 */
abstract class BasePresenter() {
    var mNetworkManager: NetworkManager = NetworkManager()

    abstract fun takeView(_view: BaseView)

    abstract fun hasEvents(): Boolean

    fun setView(view: BaseView) {
        takeView(view)
        if (hasEvents()) {
            mNetworkManager.enableListener()
            mNetworkManager.onNetworkCallback = {
                response ->
                onNetworkCallback(response)
            }
        }
    }

    open fun dropView() {
        mNetworkManager.disableListener()
    }

    open fun onNetworkCallback(response: EventMessage) {
        throw IllegalAccessError("Has event returned true but onNetworkCallback was not overridden")
    }

    fun getPayloadResponsePayload(response: EventMessage) {
        /* if (response.isSuccess) {
             if (response.payload != null) {
                 val responseWrapper = response.payload as ResponseWrapper
                 return responseWrapper
             }
         }*/
    }
}