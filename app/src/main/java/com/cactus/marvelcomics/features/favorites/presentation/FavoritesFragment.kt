package com.cactus.marvelcomics.features.favorites.presentation

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cactus.marvelcomics.R
import com.cactus.marvelcomics.common.base.BaseFragment
import com.cactus.marvelcomics.common.hide
import com.cactus.marvelcomics.common.show
import com.cactus.marvelcomics.databinding.FragmentFavoritesBinding
import com.cactus.marvelcomics.features.characters.domain.MarvelCharacter
import com.cactus.marvelcomics.features.characters.domain.listCharacters.CharactersAdapter
import com.cactus.marvelcomics.features.home.presenter.HomeFragmentDirections
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.container_characters.view.*
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : BaseFragment() {


    private val viewModel by appViewModel<FavoritesViewModel>()
    private lateinit var binding: FragmentFavoritesBinding

    lateinit var containerViewActivityMain: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.callNavigateDetailsFragment = { navigateDetailsFragment(it) }
        viewModel.showNotificationView = { showNotificationView(it) }
        viewModel.alertDialogExcludeFavorite = { alertDialogExcludeFavorite(it) }
        viewModel.changeLayout = { changeLayoutRecyclerView() }
        setupObservers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadingShow = { binding.progressBar.show() }
        viewModel.loadingHide = { binding.progressBar.hide() }
        setupRecyclerViewCharacters()
        pullToRefresh()
    }


    private fun navigateDetailsFragment(character: MarvelCharacter) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(character)
        findNavController().navigate(action)
    }


    private fun setupObservers() {

        viewModel.charactersLiveData.observe(this, {
            (binding.containerCharacters.rv_characters.adapter as CharactersAdapter).list = it
        })
    }


    private fun setupRecyclerViewCharacters() {

        binding.containerCharacters.rv_characters.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CharactersAdapter(
                { item -> viewModel.onClickCharacter(item) },
                { item -> viewModel.setFavorite(item) },
                null
            )
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


    private fun showNotificationView(text: String) {
        Snackbar.make(
            containerViewActivityMain,
            text,
            Snackbar.LENGTH_LONG
        ).show()
    }


    private fun pullToRefresh() {
        binding.containerCharacters.swiperefresh.apply {
            setOnRefreshListener {
                viewModel.swiperRefreshListCharacter()
                isRefreshing = false
            }
        }
    }


    override fun onResume() {
        super.onResume()
        containerViewActivityMain = activity?.findViewById(R.id.navHosFragment)!!

        viewModel.getCharactersFavorites()
        Log.d("Teste", "FavoritesFragment onResume")
    }


}