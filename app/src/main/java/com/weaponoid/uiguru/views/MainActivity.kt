package com.weaponoid.uiguru.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.weaponoid.uiguru.R
import com.weaponoid.uiguru.util.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private lateinit var navController: NavController
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        FirebaseInstanceId.getInstance().instanceId

        val testDeviceIds = listOf("2C9C8CEEF36F65BCD47F1D0DA71B7F53")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)

        val navGraphIds = listOf(
            R.navigation.xd_nav,
            R.navigation.sketch_nav,
            R.navigation.figma_nav,
            R.navigation.settings_nav
        )
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.fragment,
            intent = intent
        )


        controller.observe(this, androidx.lifecycle.Observer { navController ->
            addOnDestinationChangedListener(navController)
        })
        currentNavController = controller
    }


    private fun addOnDestinationChangedListener(navController: NavController) {
        navController.removeOnDestinationChangedListener(this)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.adobeXD,
            R.id.sketch,
            R.id.figma,
            R.id.settings -> bottomNavigationView?.visibility = View.VISIBLE
            else -> bottomNavigationView?.visibility = View.GONE
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }


}
