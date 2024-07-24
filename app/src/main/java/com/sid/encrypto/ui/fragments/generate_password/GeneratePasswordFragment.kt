package com.sid.encrypto.ui.fragments.generate_password

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.sid.encrypto.databinding.FragmentGeneratePasswordBinding
import com.sid.encrypto.util.Util
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.Slider
import nu.aaro.gustav.passwordstrengthmeter.PasswordStrengthCalculator
import java.util.*


class GeneratePasswordFragment : Fragment() {

    private var _binding: FragmentGeneratePasswordBinding? = null
    private val binding get() = _binding!!
    var passwordLength = 13

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneratePasswordBinding.inflate(inflater, container, false)
        setUpViews()

        return binding.root
    }

    private fun setUpViews() {
        binding.apply {
            passwordInputMeter.setPasswordStrengthCalculator(object : PasswordStrengthCalculator{
                override fun calculatePasswordSecurityLevel(password: String?): Int {
                    return if (password!=null){
                        getPasswordScope(password)
                    }else{
                        0
                    }
                }
                override fun getMinimumLength(): Int {
                    return 1
                }
                override fun passwordAccepted(level: Int): Boolean {
                    return true
                }
                override fun onPasswordAccepted(password: String?) {}
            })
        }

        val generatedPassword = generatePassword(passwordLength,
            includeUpperCaseLetters = binding.cbUppercase.isChecked,
            includeLowerCaseLetters = binding.cbLowercase.isChecked,
            includeSymbols = binding.cbSymbols.isChecked,
            includeNumbers = binding.cbNumbers.isChecked)

        binding.apply {
            passwordInputMeter.setEditText(binding.etGeneratedPassword)
            etGeneratedPassword.setText(generatedPassword)
            sliderPasswordStrength.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
                override fun onStartTrackingTouch(slider: Slider) {

                }

                override fun onStopTrackingTouch(slider: Slider) {
                    passwordLength = slider.value.toInt()
                }
            })
            sliderPasswordStrength.addOnChangeListener(object : Slider.OnChangeListener{
                override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
                    slider.setLabelFormatter(LabelFormatter {
                        return@LabelFormatter value.toInt().toString()
                    })
                }
            })
            btnGeneratePassword.setOnClickListener {

                val generatedPassword = generatePassword(passwordLength,
                    includeUpperCaseLetters = binding.cbUppercase.isChecked,
                    includeLowerCaseLetters = binding.cbLowercase.isChecked,
                    includeSymbols = binding.cbSymbols.isChecked,
                    includeNumbers = binding.cbNumbers.isChecked)

                if (generatedPassword.isBlank()){
                    if (passwordLength==0){
                        Toast.makeText(requireContext(),"Password length cannot be zero", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(),"Please check at least one item", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    binding.etGeneratedPassword.setText(generatedPassword)
                }
            }
            btnCopyPassword.setOnClickListener {
                val clipboard: ClipboardManager? = ContextCompat.getSystemService(
                    requireContext(),
                    ClipboardManager::class.java
                )
                val clip = ClipData.newPlainText("Generated Password", binding.etGeneratedPassword.text.toString())
                clipboard?.setPrimaryClip(clip)
                Toast.makeText(requireContext(),"Password Copied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun<E> MutableList<E>.mRandom(): E? = if (size>0) get(Random().nextInt(size)) else null

    private fun generatePassword(length: Int, includeUpperCaseLetters: Boolean,includeLowerCaseLetters : Boolean,
                                includeSymbols : Boolean, includeNumbers : Boolean): String {

        val password = StringBuilder(length)
        val list = mutableListOf<Char>()

        if (includeUpperCaseLetters)
            list.addAll('A'..'Z')
        if (includeLowerCaseLetters)
            list.addAll('a'..'z')
        if (includeNumbers)
            list.addAll('0'..'9')
        if (includeSymbols)
            list.addAll(Util.SYMBOLS.toList())

        for (i in 1..length) {
            password.append(list.mRandom())
        }

        return password.toString()
    }

    private fun getPasswordScope(password: String): Int {
        return when {
            password.isBlank() -> 0
            password.length in 1..3 -> 1
            password.length in 4..6 -> 2
            password.length in 7..9 -> 3
            password.length in 10..12 -> 4
            else -> 5
        }
    }

}