package com.weaponoid.uiguru.views

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crowdfire.cfalertdialog.CFAlertDialog
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.weaponoid.uiguru.R
import com.weaponoid.uiguru.adapters.ListAdapter
import com.weaponoid.uiguru.util.failedToast
import com.weaponoid.uiguru.viewmodel.SketchViewModel
import kotlinx.android.synthetic.main.sketch_fragment.*

class Sketch : Fragment(), Toolbar.OnMenuItemClickListener {

    private var uiListAdapter = ListAdapter(arrayListOf(), "sketch")
    private var selectedCategory: Int = -1
    private lateinit var viewModel: SketchViewModel

    private lateinit var skeletonScreen: RecyclerViewSkeletonScreen

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        if (isLoading) {
            skeletonScreen.show()
        } else {
            sketchRefreshLayout.isRefreshing = false
            skeletonScreen.hide()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.sketch_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onActivityCreated(savedInstanceState)
        sketchToolbar.inflateMenu(R.menu.top_menu)
        val navHostFragment = NavHostFragment.findNavController(this)
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.sketch
            )
        )
        NavigationUI.setupWithNavController(sketchToolbar, navHostFragment, appBarConfiguration)
        sketchList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = uiListAdapter


        }
        viewModel = ViewModelProvider(this).get(SketchViewModel::class.java)

        if (viewModel.listState != null) {
            sketchList.layoutManager?.onRestoreInstanceState(viewModel.listState)
            viewModel.listState = null
        } else {
            viewModel.getUIList(requireContext(), uiListAdapter)
        }

        viewModel.loading.observe(viewLifecycleOwner, loadingLiveDataObserver)

        sketchToolbar.setOnMenuItemClickListener(this)

        skeletonScreen = Skeleton.bind(sketchList)
            .adapter(uiListAdapter)
            .load(R.layout.skeleton_screen)
            .count(2)
            .show()

        uiListAdapter.loadMore.observe(viewLifecycleOwner, Observer {

            if (it) {
                viewModel.lazyLoading(requireContext(), uiListAdapter)
            }

        })

        sketchRefreshLayout.setOnRefreshListener {
            sketchRefreshLayout.isRefreshing = true
            context?.let { viewModel.refresh(it, uiListAdapter) }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.listState = sketchList.layoutManager?.onSaveInstanceState()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_search -> {
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String): Boolean {
                        return false
                    }

                    override fun onQueryTextSubmit(query: String): Boolean {
                        item.collapseActionView()
                        val action = SketchDirections.sketchToSearch(query, "sketch")
                        Navigation.findNavController(view!!).navigate(action)

                        return false
                    }
                })
            }

            R.id.action_filter -> {
                activity?.let {

                    val listItems = arrayOf(
                        getString(R.string.all),
                        getString(R.string.mobile),
                        getString(R.string.website),
                        getString(R.string.misc)
                    )
                    val builder = CFAlertDialog.Builder(it, R.style.AlertDialogStyle)
                        .setTitle(getString(R.string.selectcategory))
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setDialogBackgroundColor(Color.parseColor("#212121"))
                        .setTextColor(Color.parseColor("#f2f2f2"))
                        .addButton(
                            getString(R.string.apply),
                            Color.parseColor("#03DAC5"),
                            Color.parseColor("#212121"),
                            CFAlertDialog.CFAlertActionStyle.POSITIVE,
                            CFAlertDialog.CFAlertActionAlignment.END
                        ) { dialogInterface: DialogInterface, _: Int ->
                            when (selectedCategory) {
                                -1 -> context?.failedToast(getString(R.string.categoryerror))
                                0 -> {
                                    viewModel.filter("All", requireContext(), uiListAdapter)
                                    dialogInterface.dismiss()
                                }
                                1 -> {
                                    viewModel.filter("mobile", requireContext(), uiListAdapter)
                                    dialogInterface.dismiss()
                                }
                                2 -> {
                                    viewModel.filter("website", requireContext(), uiListAdapter)
                                    dialogInterface.dismiss()
                                }
                                3 -> {
                                    viewModel.filter("misc", requireContext(), uiListAdapter)
                                    dialogInterface.dismiss()
                                }
                            }

                        }

                        .setSingleChoiceItems(listItems, selectedCategory) { _, i ->
                            selectedCategory = i
                            // println(i)
                        }

                    builder.setCornerRadius(20.0F)


                    builder.show()


                }
            }

        }

        return true
    }


}


