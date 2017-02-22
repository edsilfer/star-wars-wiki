package br.com.edsilfer.android.starwarswiki.view.fragments

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.infrastructure.dagger.Injector
import br.com.edsilfer.android.starwarswiki.presenter.contracts.BasePresenter
import br.com.edsilfer.android.starwarswiki.presenter.contracts.FilmsFragmentPresenterContract
import br.com.edsilfer.android.starwarswiki.view.activities.contracts.FilmsFragmentViewContract
import com.squareup.picasso.Picasso
import javax.inject.Inject

/**
 * Created by ferna on 2/21/2017.
 */
class FilmsFragment : BaseFragment(), FilmsFragmentViewContract {

    @Inject
    lateinit var mPresenter: FilmsFragmentPresenterContract

    override fun getPresenter() = mPresenter as BasePresenter
    override fun getContext() = activity as AppCompatActivity
    private lateinit var mUrl: String
    private lateinit var mImage: ImageView
    private lateinit var mName: TextView

    init {
        Injector.getInstance().inject(this)
    }

    companion object {
        private val ARG_URL = "ARG_URL"
        fun newInstance(url: String): FilmsFragment {
            val fragment = FilmsFragment()
            val args = Bundle()
            args.putSerializable(ARG_URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.item_film, container, false)
        mUrl = arguments.getString(ARG_URL)
        mImage = rootView.findViewById(R.id.image) as ImageView
        mName = rootView.findViewById(R.id.name) as TextView
        return rootView
    }

    /*
    VIEW CONTRACT
     */
    override fun getFilmUrl() = mUrl

    override fun loadFilm(url: String, name: String) {
        activity.runOnUiThread {
            mName.text = name
            Picasso.with(context).load(url).fit().centerCrop().into(mImage)
        }
    }
}
