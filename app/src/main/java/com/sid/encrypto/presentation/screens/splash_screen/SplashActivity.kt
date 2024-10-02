package com.sid.encrypto.presentation.screens.splash_screen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sid.encrypto.databinding.ActivitySplashBinding
import com.sid.encrypto.presentation.screens.main.MainActivity
import com.sid.encrypto.presentation.screens.auth.MasterKeyActivity
import com.sid.encrypto.presentation.util.BiometricPromptManager
import com.sid.encrypto.presentation.screens.auth.KeyViewModel
import com.sid.encrypto.presentation.screens.main.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
class SplashActivity : AppCompatActivity() {

    lateinit var pref: SharedPreferences

    private val promptManager by lazy {
        BiometricPromptManager(this)
    }

    lateinit var binding: ActivitySplashBinding
    private val keyViewModel by viewModels<KeyViewModel> { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (isAuthenticationEnabled() && promptManager.isBiometricAvailable()) {
            launchAuthentication()
        }
        else {
            setSharedPreferencesFalse()
            handleMasterKey()
        }
    }

    private fun launchAuthentication() {
        promptManager.showBiometricPrompt(
            "Secure Encrypto",
            "Verify your identity using biometrics for secure access."
        )

        lifecycleScope.launch {
            promptManager.promptResults.collectLatest { result ->
                when(result) {
                    is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                        handleMasterKey()
                    }
                    BiometricPromptManager.BiometricResult.AuthenticationFailed -> {}

                    BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                        setSharedPreferencesFalse()
                        handleMasterKey()
                    }
                    BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                        goToMainActivity()
                    }
                    BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                        handleMasterKey()
                    }
                    BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                        handleMasterKey()
                    }
                }
            }
        }
    }

    private fun setSharedPreferencesFalse() {
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean("switchState", false)
        editor.apply()
    }

    private fun goToMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun isAuthenticationEnabled() : Boolean {
        pref = applicationContext.getSharedPreferences(
            "mypref",
            Context.MODE_PRIVATE
        )
        val isAuthEnabled = pref.getBoolean("switchState", false)
        return isAuthEnabled
    }

    private fun handleMasterKey() {
        keyViewModel.getMasterKey().observe(this) {
            when {
                it.isEmpty() -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000)
                        val intent = Intent(this@SplashActivity, MasterKeyActivity::class.java)
                        intent.putExtra("flow", "createMasterKey")
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                }
                else -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000)
                        val intent = Intent(this@SplashActivity, MasterKeyActivity::class.java)
                        intent.putExtra("flow", "askForMasterKey")
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}