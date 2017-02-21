package br.com.edsilfer.android.starwarswiki.commons

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.starwarswiki.view.activity.HomepageActivity
import br.com.edsilfer.android.starwarswiki.view.activity.QRCodeScannerActivity
import android.support.v4.content.ContextCompat.startActivity


/**
 * Created by ferna on 2/18/2017.
 */

object Router {

    val REQUEST_QRCODE_READER = 987
    val ARG_GITHUB_REPOSITORY = "https://github.com/edsilfer/star-wars-wiki"

    fun launchHomepageActivity(context: Context) {
        val intent = Intent(context, HomepageActivity::class.java)
        context.startActivity(intent)
    }

    fun launchQRCodeScanner(context: AppCompatActivity) {
        val intent = Intent(context, QRCodeScannerActivity::class.java)
        context.startActivityForResult(intent, REQUEST_QRCODE_READER)
    }

    fun launchGitHubLink(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ARG_GITHUB_REPOSITORY))
        context.startActivity(intent)
    }

}
