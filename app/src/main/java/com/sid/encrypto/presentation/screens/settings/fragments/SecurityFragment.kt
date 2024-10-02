package com.sid.encrypto.presentation.screens.settings.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sid.encrypto.R
import com.sid.encrypto.databinding.FragmentSecurityBinding
import com.sid.encrypto.presentation.screens.auth.fragments.UpdateMasterKeyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.sid.encrypto.presentation.util.BiometricPromptManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SecurityFragment : Fragment() {

    private var _binding: FragmentSecurityBinding? = null
    private val binding get() = _binding!!
    private lateinit var pref: SharedPreferences
    private var isFingerPrintEnabled: Boolean = false

    private val promptManager by lazy {
        (requireActivity() as? AppCompatActivity)?.let {
            BiometricPromptManager(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecurityBinding.inflate(inflater, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        binding.updateMasterKeyLayout.setOnClickListener {
            transaction()
        }

        pref = requireContext().getSharedPreferences(
            "mypref",
            Context.MODE_PRIVATE
        )

        setAuthStates(pref.getBoolean("switchState", false))

        binding.authSwitch.apply {
            setOnTouchListener { view, event ->
                event.actionMasked == MotionEvent.ACTION_MOVE
            }

            setOnClickListener {
                if (!isFingerPrintEnabled) {
                    if (promptManager!!.isBiometricAvailable()) {
                        turnOnAuthentication()
                    }
                    else {
                        showSnackBar("Set Fingerprint to enable this feature.")
                        setAuthStates(false)
                    }
                }
                else {
                    showSnackBar("Authentication is Disabled")
                    setSharedPreferences(false)
                    setAuthStates(false)
                }
            }
        }

        binding.back.setOnClickListener {
            val frag = SettingsFragment()
            val trans = fragmentManager?.beginTransaction()
            trans?.replace(R.id.fragment,frag)?.commit()
        }

        return binding.root
    }

    private fun turnOnAuthentication() {
        promptManager?.showBiometricPrompt(
            "Secure Encrypto",
            "Verify your identity using biometrics for secure access."
        )

        lifecycleScope.launch {
            promptManager?.promptResults?.collectLatest { result ->
                when(result) {
                    is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                        setAuthStates(false)
                    }
                    BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                        setAuthStates(false)
                    }
                    BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                        showSnackBar("Set Fingerprint to enable this feature.")
                        setAuthStates(false)
                    }
                    BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                        setSharedPreferences(true)
                        showSnackBar("Authentication Enabled")
                        setAuthStates(true)
                    }
                    BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                        showSnackBar("Feature Unavailable")
                        setAuthStates(false)
                    }
                    BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                        showSnackBar("Hardware Unavailable")
                        setAuthStates(false)
                    }
                }
            }
        }
    }

    private fun transaction() {
        val frag = UpdateMasterKeyFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.commit()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun setSharedPreferences(state: Boolean) {
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putBoolean("switchState", state)
        editor.apply()
    }

    private fun setAuthStates(state: Boolean) {
        isFingerPrintEnabled = state
        binding.authSwitch.isChecked = state
    }
}