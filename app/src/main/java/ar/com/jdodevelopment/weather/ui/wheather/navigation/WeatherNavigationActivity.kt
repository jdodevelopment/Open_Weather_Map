package ar.com.jdodevelopment.weather.ui.wheather.navigation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ar.com.jdodevelopment.weather.R
import ar.com.jdodevelopment.weather.databinding.WeatherNavigationActivityBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherNavigationActivity : AppCompatActivity() {


    val viewModel: WeatherNavigationViewModel by viewModels()
    private lateinit var binding: WeatherNavigationActivityBinding

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WeatherNavigationActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        initToolbar()
        initNavigationView()
    }


    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initNavigationView() {
        navController = findNavController(R.id.nav_host)
    }


    override fun onBackPressed() {
        val canNavigateUp = navController.navigateUp()
        if (!canNavigateUp) {
            openExitDialog()
        }
    }

    private fun openExitDialog() {
        val title = getString(R.string.exit)
        val message = getString(R.string.confirm_exit)

        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setNegativeButton(R.string.cancel) { dialogInterface, _ -> dialogInterface.dismiss() }
            .setPositiveButton(R.string.accept) { _, _ -> finish() }
            .show()
    }

}