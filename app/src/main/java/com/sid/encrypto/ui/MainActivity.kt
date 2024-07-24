package com.sid.encrypto.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sid.encrypto.R
import com.sid.encrypto.databinding.ActivityMainBinding
import com.sid.encrypto.ui.fragments.generate_password.GeneratePasswordFragment
import com.sid.encrypto.ui.fragments.settings.SettingsFragment
import com.sid.encrypto.ui.fragments.account.HomeFragment
import com.sid.encrypto.ui.fragments.card.CardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.tasks.Task

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var task: Task<AppUpdateInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())
        //Hides action bar
        supportActionBar?.hide()

        appUpdateManager = AppUpdateManagerFactory.create(this)
        task = appUpdateManager.appUpdateInfo

        bottomNav = binding.bottomNav
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    loadFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.cardFragment -> {
                    loadFragment(CardFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.passwordFragment -> {
                    loadFragment(GeneratePasswordFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.settingsFragment -> {
                    loadFragment(SettingsFragment())
                    return@setOnItemSelectedListener true
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, fragment)
        transaction.commit()
    }
}