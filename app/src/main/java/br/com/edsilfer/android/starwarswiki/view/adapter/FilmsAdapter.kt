package br.com.edsilfer.android.starwarswiki.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.edsilfer.android.starwarswiki.model.Film
import br.com.edsilfer.android.starwarswiki.view.fragments.FilmsFragment
import io.realm.RealmList

/**
 * Adapter for film details ViewPager
 */
class FilmsAdapter(fragmentManager: FragmentManager, val mMovies: RealmList<Film>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return FilmsFragment.newInstance(mMovies[position].id)
    }

    override fun getCount(): Int {
        return mMovies.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "SECTION $position"
    }
}