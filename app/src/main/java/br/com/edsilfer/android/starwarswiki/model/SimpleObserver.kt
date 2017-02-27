package br.com.edsilfer.android.starwarswiki.model

import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by Edgar Fernandes on 02/27/2017
 */

abstract class SimpleObserver<T> : Observer<T> {
    companion object {
        private val TAG = SimpleObserver::class.simpleName!!
    }

    override fun onError(e: Throwable?) {
        Log.e(TAG, "Request failed. Cause: ${e?.message}")
    }

    override fun onSubscribe(d: Disposable?) {
    }

    override fun onComplete() {
    }

    override fun onNext(value: T) {
        onSuccess(value)
    }

    abstract fun onSuccess(response: T)
}
