package com.cactus.marvelcomics.features.characters.presentation

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cactus.marvelcomics.R
import com.cactus.marvelcomics.common.base.BaseFragment
import com.cactus.marvelcomics.common.hide
import com.cactus.marvelcomics.common.show
import com.cactus.marvelcomics.data.OperationResult
import com.cactus.marvelcomics.data.OperationResult.Loading
import com.cactus.marvelcomics.data.OperationResult.Success
import com.cactus.marvelcomics.data.network.model.Character
import com.cactus.marvelcomics.data.network.model.toMarvelCharacter
import com.cactus.marvelcomics.databinding.FragmentCharactersBinding
import com.cactus.marvelcomics.features.characters.domain.MarvelCharacter
import com.cactus.marvelcomics.features.characters.domain.listCharacters.CharactersAdapter
import com.cactus.marvelcomics.features.home.presenter.HomeFragmentDirections
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.container_characters.view.*
import kotlinx.android.synthetic.main.fragment_characters.*


class CharactersFragment : BaseFragment() {

    private val viewModel by appViewModel<CharactersViewModel>()
    private lateinit var binding: FragmentCharactersBinding

    private var tabCharacterSelected: Boolean = true

    private lateinit var containerViewActivityMain: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.callNavigateDetailsFragment = { navigateDetailsFragment(it) }
        viewModel.showNotificationView = { showNotificationView(it) }
        viewModel.alertDialogExcludeFavorite = { alertDialogExcludeFavorite(it) }
        viewModel.reloadRecyclerView = { reloadRecyclerView() }
        viewModel.changeLayout = { changeLayoutRecyclerView() }
        viewModel.loadingShow = { progressBar.show() }
        viewModel.loadingHide = { progressBar.hide() }
        setupObservers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViewCharacters()
        pullToRefresh()
    }


    private fun navigateDetailsFragment(character: MarvelCharacter) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(character)
        findNavController().navigate(action)
    }

    private fun setupObservers() {

        viewModel.operationResultLiveData.observe(this, { it ->

            when (it) {
                is Success<*> -> {
                    binding.progressBar.hide()

                    val list = it.data as MutableList<Character>
                    viewModel.charactersLiveData.value =
                        viewModel.charactersLiveData.value!!.apply {
                            addAll(list.map { it.toMarvelCharacter() })
                        }
                }
                is Loading -> {
                    binding.progressBar.show()
                }
                is OperationResult.Error -> {
                    binding.progressBar.hide()
                    showNetworkError()
                }
            }
        })

        viewModel.charactersLiveData.observe(this, {
            (binding.containerCharacters.rv_characters.adapter as CharactersAdapter).list = it
        })
    }

    private fun showNetworkError() {
        Snackbar.make(
            containerViewActivityMain,
            R.string.error_conection,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showNotificationView(text: String) {
        Snackbar.make(
            containerViewActivityMain,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }


    private fun setupRecyclerViewCharacters() {

        binding.containerCharacters.rv_characters.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CharactersAdapter(
                { item -> viewModel.onClickCharacter(item) },
                { item -> viewModel.setFavorite(item) },
                viewModel.favoritesIdLiveData
            )
        }

        binding.containerCharacters.rv_characters.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(@NonNull recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && tabCharacterSelected) {
                    viewModel.onLastItemVisible()
                }
            }
        })
    }


    private fun reloadRecyclerView() {
        (binding.containerCharacters.rv_characters.adapter as CharactersAdapter).list =
            viewModel.charactersLiveData.value ?: listOf()
    }


    private fun alertDialogExcludeFavorite(callDeleteDb: () -> Unit) {
        val builder = AlertDialog.Builder(context)
            .setMessage(R.string.confirm_deletion)
            .setPositiveButton(R.string.yes) { _, _ ->
                callDeleteDb.invoke()
            }
            .setNegativeButton(R.string.no) { _, _ -> }
            .show()

        builder.apply {
            getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.LTGRAY)
            getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.LTGRAY)
        }
    }


    private fun pullToRefresh() {
        binding.containerCharacters.swiperefresh.apply {
            setOnRefreshListener {
                if (viewModel.charactersLiveData.value?.isEmpty() == true) {
                    viewModel.swiperRefreshListCharacter()
                } else {
                    viewModel.charactersLiveData.apply {
                        value = viewModel.charactersLiveData.value
                    }
                }
                isRefreshing = false
            }
        }
    }


    private fun changeLayoutRecyclerView() {
        var position = 0
        var newLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        val lm = binding.containerCharacters.rv_characters.layoutManager


        when (lm?.javaClass?.canonicalName) {
            LinearLayoutManager::class.java.canonicalName -> {
                position = (lm as LinearLayoutManager).findFirstVisibleItemPosition()
                newLayoutManager = GridLayoutManager(activity, 2)
            }

            GridLayoutManager::class.java.canonicalName -> {
                position = (lm as GridLayoutManager).findFirstVisibleItemPosition()
                newLayoutManager = LinearLayoutManager(activity)
            }
        }

        binding.containerCharacters.rv_characters.apply {
            layoutManager = newLayoutManager
            scrollToPosition(position)
        }
    }


    override fun onStart() {
        super.onStart()
        reloadRecyclerView()
    }


    override fun onResume() {
        super.onResume()
        containerViewActivityMain = activity?.findViewById(R.id.navHosFragment)!!
    }
}
