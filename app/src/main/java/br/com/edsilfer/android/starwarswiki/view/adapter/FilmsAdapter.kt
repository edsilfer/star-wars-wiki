package br.com.edsilfer.android.starwarswiki.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import br.com.edsilfer.android.starwarswiki.view.fragments.FilmsFragment

/**
 * Created by ferna on 2/21/2017.
 */

class FilmsAdapter(fragmentManager: FragmentManager, val mMovies: List<String>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return FilmsFragment.newInstance(mMovies[position])
    }

    override fun getCount(): Int {
        return mMovies.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "SECTION $position"
    }
}