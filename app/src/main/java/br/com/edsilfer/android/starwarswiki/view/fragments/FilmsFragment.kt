package br.com.edsilfer.android.starwarswiki.view.fragments

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.com.edsilfer.android.starwarswiki.R
import br.com.edsilfer.android.starwarswiki.commons.Router.launchMovieUrl
import br.com.edsilfer.android.starwarswiki.infrastructure.dagger.Injector
import br.com.edsilfer.android.starwarswiki.infrastructure.database.FilmDAO
import br.com.edsilfer.android.starwarswiki.model.Film
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
    private lateinit var mFilm: Film
    private lateinit var mImage: ImageView
    private lateinit var mName: TextView

    init {
        Injector.getInstance().inject(this)
    }

    companion object {
        private val ARG_FILM_ID = "ARG_FILM_ID"
        fun newInstance(filmId: Long): FilmsFragment {
            val fragment = FilmsFragment()
            val args = Bundle()
            args.putLong(ARG_FILM_ID, filmId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.item_film, container, false)
        mFilm = FilmDAO.read(arguments.getLong(ARG_FILM_ID))!!
        mImage = rootView.findViewById(R.id.image) as ImageView
        mName = rootView.findViewById(R.id.name) as TextView

        mImage.setOnClickListener {
            activity.runOnUiThread {
                launchMovieUrl(activity as AppCompatActivity, mFilm.url)
            }
        }

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFilm()
    }

    /*
    VIEW CONTRACT
     */
    private fun loadFilm() {
        activity.runOnUiThread {
            mName.text = mFilm.title
            Picasso.with(context).load(mFilm.image_url).fit().centerCrop().error(R.drawable.ic_image_not_found).into(mImage)
        }
    }
}
