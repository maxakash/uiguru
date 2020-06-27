package com.weaponoid.uiguru.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.weaponoid.uiguru.R
import com.weaponoid.uiguru.adapters.SliderAdapter
import com.weaponoid.uiguru.databinding.DetailFragmentBinding
import com.weaponoid.uiguru.model.UI
import com.weaponoid.uiguru.util.UIDetailListener
import com.weaponoid.uiguru.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.detail_fragment.*


class DetailFigma : Fragment(), UIDetailListener {


    private lateinit var viewModel: DetailViewModel
    private lateinit var sliderAdapter: SliderAdapter
    private var uiDetail: UI? = null
    private lateinit var dataBinding: DetailFragmentBinding
    private var itemList: ArrayList<Any> = arrayListOf()


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(bar, navHostFragment)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        dataBinding.listener = this

        arguments?.let {
            uiDetail = DetailFigmaArgs.fromBundle(it).uiDetail
        }


        itemList.addAll(uiDetail?.uiImages!!)
        sliderAdapter = context?.let { SliderAdapter(it, itemList) }!!
        context?.let {
            viewModel.loadNativeAd(it, sliderAdapter)
        }
        viewPager2.adapter = sliderAdapter
        dots_indicator.setViewPager2(viewPager2)


    }

    override fun onClick(view: View) {

        when (view.tag) {
            "share" -> activity?.let {
                uiDetail?.downloadUrl?.let { it1 ->
                    viewModel.shareLink(
                        it1,
                        it
                    )
                }
            }
            "info" -> showInfo()
            "download" -> {
                if (uiDetail?.fileName == "") {
                    activity?.let { viewModel.downloadLink(uiDetail?.downloadUrl!!, it) }
                } else {
                    context?.let {
                        viewModel.download(
                            uiDetail?.downloadUrl!!,
                            uiDetail?.fileName!!,
                            it
                        )
                    }
                }


            }
        }

    }


    @SuppressLint("SetTextI18n")
    fun showInfo() {
        activity?.let {
            AlertDialog.Builder(it).apply {
                val dialogView = requireActivity().layoutInflater.inflate(R.layout.ui_info, null)
                val designer = dialogView.findViewById<TextView>(R.id.designer)
                val source = dialogView.findViewById<TextView>(R.id.source)
                designer.setText(getString(R.string.designer) + ": ${uiDetail?.author}")
                source.setText(getString(R.string.source) + ": ${uiDetail?.source}")
                setView(dialogView)
            }.create().show()
        }
    }


}




