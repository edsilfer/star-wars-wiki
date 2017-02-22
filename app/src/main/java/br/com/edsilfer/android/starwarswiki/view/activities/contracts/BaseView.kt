package br.com.tyllt.view.contracts

import android.support.v7.app.AppCompatActivity

/**
 * Created by User on 01/01/2017.
 */
interface BaseView {
    fun getContext(): AppCompatActivity

    fun showLoading() {}

    fun hideLoading() {}
}