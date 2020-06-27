package com.weaponoid.uiguru.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.weaponoid.uiguru.R
import com.weaponoid.uiguru.databinding.SettingsFragmentBinding
import com.weaponoid.uiguru.util.UIDetailListener
import com.weaponoid.uiguru.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.settings_fragment.*

class Settings : Fragment(), UIDetailListener {

    private lateinit var dataBinding: SettingsFragmentBinding


    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val navHostFragment = NavHostFragment.findNavController(this)
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.settings
            )
        )
        NavigationUI.setupWithNavController(settingsToolbar, navHostFragment, appBarConfiguration)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        dataBinding.listener = this


    }

    override fun onClick(view: View) {
        when (view.tag) {
            "add" -> {
                val action = SettingsDirections.settingdToAdd()
                Navigation.findNavController(view).navigate(action)
            }
            "rate" -> {
                viewModel.rateApp(context)
            }

            "about" -> {
                viewModel.about(context)
            }

            "contact" -> {
                viewModel.contact(requireActivity())
            }

            "privacy" -> {
                viewModel.privacyPolicy(context)
            }
        }
    }

}
