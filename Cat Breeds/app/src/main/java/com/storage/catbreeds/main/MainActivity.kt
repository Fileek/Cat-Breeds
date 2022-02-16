package com.storage.catbreeds.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.storage.catbreeds.R
import com.storage.catbreeds.databinding.ActivityMainBinding
import com.storage.catbreeds.viewmodel.CatViewModel
import com.storage.catbreeds.viewmodel.CatViewModelFactory

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val catViewModel: CatViewModel by viewModels {
        CatViewModelFactory((application as CatsApplication).repository)
    }
    private var appBarConfiguration: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        catViewModel.allCats.observe(this, { })

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.toolbar.apply {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                // hide action buttons, when user is not on the main screen
                if (destination.label != getString(R.string.main_fragment_label)) menu.clear()
                else inflateMenu(R.menu.action_menu)
            }
            with(AppBarConfiguration(navController.graph)) {
                appBarConfiguration = this
                setupWithNavController(navController, this)
            }
            setOnMenuItemClickListener {
                it.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return appBarConfiguration?.let { navController.navigateUp(it) } == true ||
            super.onSupportNavigateUp()
    }
}
