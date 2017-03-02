package br.com.edsilfer.android.starwarswiki.presenter

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.infrastructure.database.CharacterDAO
import com.squareup.picasso.Picasso


/**
 * Created by Edgar Fernandes on 03/01/2017
 */

class WidgetPresenter : AppWidgetProvider() {

    private val TAG = WidgetPresenter::class.simpleName

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (id in appWidgetIds) {
            val character = CharacterDAO.readLastAddedCharacter()
            if (character != null) {
                Log.i(TAG, "Retrieved last character is: ${character.name}")
                loadCharacterInfo(
                        context,
                        id,
                        character.name,
                        context.getString(R.string.str_widget_last_added_character),
                        character.image_url
                )
            } else {
                Log.i(TAG, "No character has been added yet")
                loadPlaceholder(context, id)
            }
        }
    }

    private fun loadPlaceholder(context: Context, id: Int) {
        loadCharacterInfo(
                context,
                id,
                context.getString(R.string.str_widget_empty_character_header),
                context.getString(R.string.str_widget_empty_character_subheader),
                context.getString(R.string.str_widget_empty_character_thumbnail)
        )
    }

    private fun loadCharacterInfo(context: Context, id: Int, header: String, subheader: String, thumbnail: String) {
        val rootView = RemoteViews(context.packageName, R.layout.widget)
        rootView.setTextViewText(R.id.header, header)
        rootView.setTextViewText(R.id.subheader, subheader)
        Picasso.with(context)
                .load(thumbnail)
                .into(rootView, R.id.thumbnail, intArrayOf(id))
    }

}
