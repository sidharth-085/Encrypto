package com.sid.encrypto.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.sid.encrypto.R
import com.sid.encrypto.databinding.ActivityMasterKeyBinding

class MasterKeyActivity : AppCompatActivity() {

    lateinit var binding: ActivityMasterKeyBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasterKeyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hides action bar
        supportActionBar?.hide()

        val flow = intent.getStringExtra("flow")

        setupNavigation()

        if (flow == "createMasterKey") {
            findNavController(R.id.key_fragment).navigate(R.id.masterKeyFragment)
        }
        if (flow == "askForMasterKey") {
            findNavController(R.id.key_fragment).navigate(R.id.lockMasterKeyFragment)
        }
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.key_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }


}