package com.sid.encrypto.ui.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.sid.encrypto.R
import com.sid.encrypto.databinding.FragmentSecurityBinding
import com.sid.encrypto.ui.auth.master_key.UpdateMasterKeyFragment
import com.sid.encrypto.util.Util
import com.google.android.material.bottomnavigation.BottomNavigationView


class SecurityFragment : Fragment() {

    private var _binding: FragmentSecurityBinding? = null
    private val binding get() = _binding!!
    lateinit var pref: SharedPreferences
    private var cancellationSignal: CancellationSignal? = null
    private var isFingerPrintEnabled: Boolean = false

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecurityBinding.inflate(inflater, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE

        pref = requireContext().getSharedPreferences(
            "mypref",
            Context.MODE_PRIVATE
        )
        isFingerPrintEnabled = pref.getBoolean("switchState", false)
        Util.log("OnCreateState: $isFingerPrintEnabled")
        binding.FingerPrintSwitch.isChecked = isFingerPrintEnabled

        binding.updateMasterKeyLayout.setOnClickListener {
            transaction()
        }

        binding.back.setOnClickListener {
            val frag = SettingsFragment()
            val trans = fragmentManager?.beginTransaction()
            trans?.replace(R.id.fragment,frag)?.commit()
        }

        binding.FingerPrintSwitch.apply {

            setOnTouchListener { _, event ->
                event.actionMasked == MotionEvent.ACTION_MOVE
            }

            setOnClickListener {
                isFingerPrintEnabled = isFingerPrintEnabled.not()

                val editor: SharedPreferences.Editor = pref.edit()
                editor.putBoolean("switchState", isFingerPrintEnabled)
                editor.apply()
                Util.log("test123: ${isFingerPrintEnabled.not()}")

                if (isFingerPrintEnabled) {
                    val biometricPrompt = BiometricPrompt.Builder(requireContext())
                        .setTitle("Secure Encrypto")
                        .setSubtitle("Give your Fingerprint to ensure it's security")
                        .setDescription("Enter your fingerprint to proceed")
                        .setNegativeButton("Cancel", requireActivity().mainExecutor) { _, _ ->
                            Toast.makeText(
                                requireContext(),
                                "Fingerprint not given",
                                Toast.LENGTH_SHORT
                            ).show()
                            isFingerPrintEnabled = false // turn off switch
                            binding.FingerPrintSwitch.isChecked = false // update switch state
                        }.build()

                    biometricPrompt.authenticate(
                        getCancellationSignal(),
                        requireActivity().mainExecutor,
                        authenticationCallback
                    )
                } else {
                    binding.FingerPrintSwitch.isChecked = false // update switch state
                }
            }
            return binding.root
        }
    }

    private fun transaction() {
        val frag = UpdateMasterKeyFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.commit()
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(
                requireContext(),
                "Authentication cancelled by the user",
                Toast.LENGTH_SHORT
            ).show()
        }
        return cancellationSignal as CancellationSignal
    }

    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(
                    requireContext(),
                    "Authentication error",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
            }
        }
}