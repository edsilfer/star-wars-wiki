package br.com.edsilfer.android.starwarswiki.view.dialogs

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.edsilfer.android.starwarswiki.R


/**
 * Dialog that displays a fancy loading full screen
 */
class FancyLoadingDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fancy_loading, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        return dialog
    }

    companion object {
        /**
         * Displays a fancy full screen loading dialog
         */
        fun showDialog(activity: AppCompatActivity): FancyLoadingDialog {
            val dialog = FancyLoadingDialog()
            val fragmentManager = activity.supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            transaction.add(android.R.id.content, dialog).addToBackStack(null).commit()
            return dialog
        }
    }
}