package com.cactus.marvelcomics.ui.character.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.cactus.marvelcomics.R
import com.cactus.marvelcomics.common.base.BaseFragment
import com.cactus.marvelcomics.databinding.FragmentCharactersBinding
import com.cactus.marvelcomics.ui.character.domain.listCharacters.CharactersAdapter


class CharactersFragment : BaseFragment() {

    private val viewModel by viewModels<CharactersViewModel> { viewModelsFactory }
    private lateinit var binding: FragmentCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewCharacters()

    }

    private fun setupObservers() {
        viewModel.stateLiveData.observe(this, Observer {
            when (it) {
                CharacterListState.ShowSuccessView -> {
                }
                CharacterListState.ShowErrorView -> {
                }
                CharacterListState.ShowLoading -> {
                }
                CharacterListState.RemoveLoading -> {
                }
            }
        })
        viewModel.charactersLiveData.observe(this, Observer {
            (binding.rvCharacters.adapter as CharactersAdapter).list = it
        })
    }


    fun setupRecyclerViewCharacters() {

    }




}
