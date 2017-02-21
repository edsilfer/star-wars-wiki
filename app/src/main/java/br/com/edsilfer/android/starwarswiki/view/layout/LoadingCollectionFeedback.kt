package br.com.edsilfer.android.starwarswiki.view.layout

import android.annotation.TargetApi
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.edsilfer.android.starwarswiki.R
import kotlinx.android.synthetic.main.rsc_util_loading_collection_feedback.view.*

class LoadingCollectionFeedback : RelativeLayout {
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    fun init(attrs: AttributeSet?) {
        val rootView = View.inflate(context, R.layout.rsc_util_loading_collection_feedback, this)
        val image = rootView.findViewById(R.id.empty_collection_image) as ImageView
        val label = rootView.findViewById(R.id.empty_collection_text) as TextView

        val disclaimer = getDisclaimer(attrs!!)
        if (!disclaimer.isNullOrEmpty()) {
            label.text = disclaimer
        } else {
            label.text = String.format(context.getString(R.string.str_commons_utils_empty_collection), getCollectionName(attrs))
        }

        image.setImageResource(getImage(attrs!!))
    }

    private fun getImage(attrs: AttributeSet): Int {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LoadingCollectionFeedback,
                0, 0)

        try {
            return a.getResourceId(R.styleable.LoadingCollectionFeedback_image, -1)
        } finally {
            a.recycle()
        }
    }

    private fun getCollectionName(attrs: AttributeSet): String {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LoadingCollectionFeedback,
                0, 0)

        try {
            return a.getString(R.styleable.LoadingCollectionFeedback_collectionName)
        } finally {
            a.recycle()
        }
    }

    private fun getDisclaimer(attrs: AttributeSet): String {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.LoadingCollectionFeedback,
                0, 0)

        try {
            return a.getString(R.styleable.LoadingCollectionFeedback_disclaimer)
        } catch (e: Exception) {
            return ""
        } finally {
            a.recycle()
        }
    }

    /*
     PUBLIC INTERFACE
     */
    fun showFeedback(recyclerView: RecyclerView, collection: Collection<*>?) {
        if (collection == null || collection.isEmpty()) {
            recyclerView.visibility = RecyclerView.GONE
            loading.visibility = LinearLayout.GONE
            empty_collection.visibility = LinearLayout.VISIBLE
        } else if (!collection.isEmpty()) {
            recyclerView.visibility = RecyclerView.VISIBLE
            loading.visibility = LinearLayout.GONE
            empty_collection.visibility = LinearLayout.GONE
        } else {
            hideAll()
            recyclerView.visibility = RecyclerView.VISIBLE
        }
    }

    fun showLoading() {
        loading.visibility = LinearLayout.VISIBLE
        empty_collection.visibility = LinearLayout.GONE
    }

    fun hideAll() {
        wrapper.visibility = GONE
    }
}


