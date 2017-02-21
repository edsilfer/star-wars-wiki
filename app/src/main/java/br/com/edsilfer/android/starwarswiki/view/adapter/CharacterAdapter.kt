package br.com.edsilfer.android.starwarswiki.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.view.activity.contracts.HomepageViewContract
import br.com.edsilfer.android.starwarswiki.view.viewholder.CharacterViewHolder
import com.squareup.picasso.Picasso

/**
 * Created by ferna on 2/19/2017.
 */

class CharacterAdapter(
        val mView: HomepageViewContract,
        var mData: MutableList<br.com.edsilfer.android.starwarswiki.model.Character>
) : RecyclerView.Adapter<CharacterViewHolder>() {

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = mData[position]
        if (character.isValid) {
            if (!character.image_url.isNullOrBlank()) {
                Picasso.with(mView.getContext()).load(character.image_url).fit().centerCrop().into(holder.image)
            } else {
                Picasso.with(mView.getContext()).load(R.drawable.ic_image_not_found).fit().centerCrop().into(holder.image)
            }

            holder.name.text = character.name
            holder.gender.text = character.gender
            holder.height.text = character.height.toString()
            holder.mass.text = character.mass.toString()
        }
    }

    fun addItem(character: br.com.edsilfer.android.starwarswiki.model.Character) {
        mData.add(character)
        notifyItemInserted(itemCount)
    }

}
