package br.com.edsilfer.android.starwarswiki.presenter

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import br.com.edsilfer.android.starwarswiki.R
import com.squareup.picasso.Picasso


/**
 * Created by Edgar Fernandes on 03/01/2017
 */

class WidgetProvider : AppWidgetProvider() {

    private val TAG = WidgetProvider::class.simpleName

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (id in appWidgetIds) {
            val rootView = RemoteViews(context.packageName, R.layout.widget_star_wars_wiki)
            rootView.setTextViewText(R.id.header, "Luke Skywalker")
            Picasso.with(context)
                    .load("http://im.ziffdavisinternational.com/ign_br/screenshot/default/lukeskywalkerlightsaber_x3zw.jpg")
                    .into(rootView, R.id.thumbnail, intArrayOf(id))
        }
    }

}
