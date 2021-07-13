package com.cactus.marvelcomics.ui.character.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cactus.marvelcomics.R
import com.cactus.marvelcomics.common.base.BaseFragment
import com.cactus.marvelcomics.common.hide
import com.cactus.marvelcomics.common.show
import com.cactus.marvelcomics.data.OperationResult
import com.cactus.marvelcomics.data.OperationResult.Loading
import com.cactus.marvelcomics.data.OperationResult.Success
import com.cactus.marvelcomics.data.network.model.MarvelCharacter
import com.cactus.marvelcomics.databinding.FragmentCharactersBinding
import com.cactus.marvelcomics.ui.character.domain.listCharacters.CharactersAdapter
import com.google.android.material.snackbar.Snackbar


class CharactersFragment : BaseFragment() {

    private val viewModel by appViewModel<CharactersViewModel>()
    private lateinit var binding: FragmentCharactersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

        viewModel.operationResultLiveData.observe(this, Observer {

            when (it) {
                is Success<*> -> {
                    binding.progressBar.hide()

                    viewModel.charactersLiveData.value =
                        viewModel.charactersLiveData.value!!.apply {
                            addAll(it.data as MutableList<MarvelCharacter>)
                        }
//                    (binding.rvCharacters.adapter as CharactersAdapter).list = it.data as List<MarvelCharacter>
                    Log.d("Teste livedata", "Operation Success")
                }
                is Loading -> {
                    binding.progressBar.show()
                    Log.d("Teste livedata", "Operation Loading")
                }
                is OperationResult.Error -> {
                    binding.progressBar.hide()
                    Snackbar.make(
                        binding.containerCharacters,
                        "Ocorreu um erro na conexão \uD83D\uDE15 ",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.d("Teste livedata", "Operation Error" + ":   ${it.error}")
                }
            }
        })


        viewModel.charactersLiveData.observe(this, Observer {
            (binding.rvCharacters.adapter as CharactersAdapter).list = it
        })
    }


    fun setupRecyclerViewCharacters() {

        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CharactersAdapter(
                { item -> viewModel.onClickCharacter(item) },
                { item -> viewModel.setFavorite(item) }
            )
        }

        binding.rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.onLastItemVisible()
                }
            }
        })
    }


}
