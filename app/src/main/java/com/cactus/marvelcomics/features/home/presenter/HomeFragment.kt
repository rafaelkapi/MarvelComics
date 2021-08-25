package com.cactus.marvelcomics.features.home.presenter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.cactus.marvelcomics.R
import com.cactus.marvelcomics.features.home.domain.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPagerWithTabLayout()


    }


    private fun setUpViewPagerWithTabLayout() {
        val pagerAdapter = ViewPagerAdapter(this)
        viewpager.adapter = pagerAdapter
        addTabLayoutMediator()
    }


    private fun addTabLayoutMediator() {
        val listTabs = listOf("Characters","Favorites")
        TabLayoutMediator(
            tabLayout, viewpager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = listTabs[position]
        }.attach()
    }




}