package br.com.edsilfer.android.starwarswiki.commons

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.infrastructure.App.Companion.getContext
import br.com.edsilfer.android.starwarswiki.view.activities.FilmsActivity
import br.com.edsilfer.android.starwarswiki.view.activities.HomepageActivity
import br.com.edsilfer.android.starwarswiki.view.activities.QRCodeScannerActivity
import br.com.edsilfer.kotlin_support.extensions.showErrorPopUp
import java.util.*


/**
 * Created by ferna on 2/18/2017.
 */

object Router {

    const val REQUEST_QRCODE_READER = 987
    const val ARG_CHARACTER_ID = "ARG_CHARACTER_ID"

    fun launchHomepageActivity(context: Context) {
        val intent = Intent(context, HomepageActivity::class.java)
        context.startActivity(intent)
    }

    fun launchQRCodeScanner(context: AppCompatActivity) {
        val intent = Intent(context, QRCodeScannerActivity::class.java)
        context.startActivityForResult(intent, REQUEST_QRCODE_READER)
    }

    fun launchGitHubLink(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.str_author_github_repository)))
        context.startActivity(intent)
    }

    fun launchMovieUrl(context: AppCompatActivity, movieId: Long) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/movie/$movieId"))
        context.startActivity(intent)
    }

    fun launchFilmsActivity(context: Context, characterId: Long) {
        val intent = Intent(context, FilmsActivity::class.java)
        intent.putExtra(ARG_CHARACTER_ID, characterId)
        context.startActivity(intent)
    }

}
