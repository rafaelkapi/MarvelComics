package com.cactus.marvelcomics.features.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cactus.marvelcomics.R
import com.cactus.marvelcomics.common.base.BaseFragment
import com.cactus.marvelcomics.common.hide
import com.cactus.marvelcomics.databinding.FragmentDetailsBinding
import com.cactus.marvelcomics.features.details.domain.listComics.ComicsAdapter
import com.cactus.marvelcomics.features.details.domain.listSeries.SeriesAdapter
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : BaseFragment() {

    private val viewModel by appViewModel<DetailsViewModel>()
    private lateinit var binding: FragmentDetailsBinding

    private val args by navArgs<DetailsFragmentArgs>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.character.value = args.character
        setupObservers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackArrow()
        setupRecyclerViews()
    }


    private fun setupObservers() {

        viewModel.comicsLiveData.observe(this, {
            (binding.rvComics.adapter as ComicsAdapter).list = it
            binding.shimmerComics.hide()
        })

        viewModel.seriesLiveData.observe(this, {
            (binding.rvSeries.adapter as SeriesAdapter).list = it
            binding.shimmerSeries.hide()
        })

    }

    private fun setBackArrow() {
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, _, _ ->
            binding.toolbar.title = ""
        }
    }


    fun setupRecyclerViews() {

        binding.rvComics.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ComicsAdapter()

        }

        binding.rvSeries.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = SeriesAdapter()

        }
    }

}
