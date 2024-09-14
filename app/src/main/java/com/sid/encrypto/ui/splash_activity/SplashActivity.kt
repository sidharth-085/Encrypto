package com.sid.encrypto.ui.splash_activity

import android.content.Intent
import android.os.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sid.encrypto.databinding.ActivitySplashBinding
import com.sid.encrypto.ui.auth.MasterKeyActivity
import com.sid.encrypto.viewModel.KeyViewModel
import com.sid.encrypto.viewModel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    private val keyViewModel by viewModels<KeyViewModel> { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        handleMasterKey()
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