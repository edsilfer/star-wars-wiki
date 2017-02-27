package br.com.edsilfer.android.starwarswiki.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.edsilfer.android.starwarswiki.R

/**
 * View Holder for a CharacterObject
 */
class CharacterViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
    val wrapper = rootView.findViewById(R.id.wrapper) as RelativeLayout
    val image = rootView.findViewById(R.id.image) as ImageView
    val name = rootView.findViewById(R.id.name) as TextView
    val gender = rootView.findViewById(R.id.gender) as TextView
    val height = rootView.findViewById(R.id.height) as TextView
    val mass = rootView.findViewById(R.id.mass) as TextView
    val yob = rootView.findViewById(R.id.yob) as TextView
    val url = rootView.findViewById(R.id.url) as TextView
}

