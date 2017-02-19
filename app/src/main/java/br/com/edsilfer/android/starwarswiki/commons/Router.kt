package br.com.edsilfer.android.starwarswiki.commons

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import br.com.edsilfer.android.starwarswiki.view.HomepageActivity
import br.com.edsilfer.android.starwarswiki.view.QRCodeScannerActivity

/**
 * Created by ferna on 2/18/2017.
 */

object Router {

    val REQUEST_QRCODE_READER = 987

    fun launchHomepageActivity(context: Context) {
        val intent = Intent(context, HomepageActivity::class.java)
        context.startActivity(intent)
    }

    fun launchQRCodeScanner(context: AppCompatActivity) {
        val intent = Intent(context, QRCodeScannerActivity::class.java)
        context.startActivityForResult(intent, REQUEST_QRCODE_READER)
    }

}
