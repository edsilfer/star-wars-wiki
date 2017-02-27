package br.com.edsilfer.android.starwarswiki.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.model.Character
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.HomepageViewContract
import br.com.edsilfer.android.starwarswiki.view.viewholder.CharacterViewHolder
import com.afollestad.materialdialogs.MaterialDialog
import com.squareup.picasso.Picasso

/**
 * Adapter that handles character row presentation
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
        /*
        Required check to prevent app crash due to Realm lifecycle
         */
        if (character.isValid) {
            loadCharacterThumbnail(character, holder)

            holder.name.text = character.name
            holder.gender.text = character.gender
            holder.height.text = character.height.toString()
            holder.mass.text = character.mass.toString()
            holder.yob.text = character.birth_year
            holder.url.text = character.url

            setClickListeners(character, holder)
        } else {
            mView.loadCachedCharacters()
        }
    }

    private fun loadCharacterThumbnail(character: Character, holder: CharacterViewHolder) {
        if (!character.image_url.isNullOrBlank()) {
            Picasso.with(mView.getContext()).load(character.image_url).fit().centerCrop().into(holder.image)
        } else {
            Picasso.with(mView.getContext()).load(R.drawable.ic_image_not_found).fit().centerCrop().into(holder.image)
        }
    }

    private fun setClickListeners(character: Character, holder: CharacterViewHolder) {
        holder.wrapper.setOnClickListener {
            mView.onCharacterClick(character)
        }

        holder.wrapper.setOnLongClickListener {
            showRemoveCharacterConfirmationDialog(character)
            true
        }
    }

    private fun showRemoveCharacterConfirmationDialog(character: Character) {
        MaterialDialog
                .Builder(mView.getContext())
                .title(mView.getContext().getString(R.string.str_characters_dialog_title))
                .titleColor(mView.getContext().resources.getColor(R.color.colorPrimaryDark))
                .items(R.array.character_options)
                .itemsCallback {
                    dialog,
                    view,
                    which,
                    text ->
                    when (which) {
                        0 -> mView.deleteCharacter(character)
                    }
                }
                .show()
    }

    /**
     * Add character item to the list performing default animation
     */
    fun addItem(character: br.com.edsilfer.android.starwarswiki.model.Character) {
        mData.add(character)
        notifyItemInserted(itemCount)
    }

    /**
     * Removes character item to the list performing default animation
     */
    fun removeItem(character: br.com.edsilfer.android.starwarswiki.model.Character) {
        if (character.isValid) {
            val index = mData.indexOf(character)
            mData.remove(character)
            notifyItemRemoved(index)
        } else {
            mView.loadCachedCharacters()
        }
    }

}

