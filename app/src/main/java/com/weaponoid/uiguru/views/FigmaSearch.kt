package com.weaponoid.uiguru.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.weaponoid.uiguru.R
import com.weaponoid.uiguru.adapters.SearchAdapter
import com.weaponoid.uiguru.model.UI
import com.weaponoid.uiguru.viewmodel.SearchResultsViewModel
import kotlinx.android.synthetic.main.search_results_fragment.*

class FigmaSearch : Fragment() {

    private lateinit var uiListAdapter: SearchAdapter
    private var searchQuery: String? = null
    private var type: String? = null

    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen

    private val uiListDataObserver = Observer<ArrayList<UI>> { list ->
        list?.let {
            searchList.visibility = View.VISIBLE
            uiListAdapter.updateUiList(it)
        }
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            skeletonScreen.show()
        } else {
            skeletonScreen.hide()
        }

    }

    private lateinit var viewModel: SearchResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_results_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(searchToolbar, navHostFragment)

        arguments?.let {
            searchQuery = FigmaSearchArgs.fromBundle(it).searchQuery
            type = FigmaSearchArgs.fromBundle(it).type
        }
        uiListAdapter = SearchAdapter(arrayListOf(), type!!)

        viewModel = ViewModelProvider(this).get(SearchResultsViewModel::class.java)
        viewModel.uiList.observe(viewLifecycleOwner, uiListDataObserver)
        viewModel.loading.observe(viewLifecycleOwner, loadingLiveDataObserver)


        searchList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = uiListAdapter

        }

        if (viewModel.listState != null) {
            searchList.layoutManager?.onRestoreInstanceState(viewModel.listState)
            viewModel.listState = null
        } else {
            viewModel.getSearchResults(
                query = searchQuery!!,
                type = type!!,
                context = requireContext()
            )
        }

        skeletonScreen = Skeleton.bind(searchList)
            .adapter(uiListAdapter)
            .load(R.layout.skeleton_screen)
            .count(2)
            .show()

    }

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.listState = searchList.layoutManager?.onSaveInstanceState()
    }


}
