package com.cactus.marvelcomics.features.home.domain

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cactus.marvelcomics.features.characters.presentation.CharactersFragment
import com.cactus.marvelcomics.features.favorites.presentation.FavoritesFragment
import com.cactus.marvelcomics.features.home.presenter.HomeFragment


class ViewPagerAdapter(fa: HomeFragment) :
    FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return CharactersFragment()
            1 -> return FavoritesFragment()
        }
        return CharactersFragment()
    }

}