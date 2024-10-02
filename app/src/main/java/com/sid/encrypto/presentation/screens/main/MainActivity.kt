package com.sid.encrypto.presentation.screens.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sid.encrypto.R
import com.sid.encrypto.databinding.ActivityMainBinding
import com.sid.encrypto.presentation.screens.generate_password.fragments.GeneratePasswordFragment
import com.sid.encrypto.presentation.screens.settings.fragments.SettingsFragment
import com.sid.encrypto.presentation.screens.accounts.fragments.HomeFragment
import com.sid.encrypto.presentation.screens.card.fragments.CardFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(HomeFragment())
        //Hides action bar
        supportActionBar?.hide()

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