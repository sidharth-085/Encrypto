package com.sid.encrypto.ui.fragments.settings

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.sid.encrypto.R
import com.sid.encrypto.databinding.FragmentSecurityBinding
import com.sid.encrypto.ui.auth.master_key.UpdateMasterKeyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class SecurityFragment : Fragment() {

    private var _binding: FragmentSecurityBinding? = null
    private val binding get() = _binding!!

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

        binding.back.setOnClickListener {
            val frag = SettingsFragment()
            val trans = fragmentManager?.beginTransaction()
            trans?.replace(R.id.fragment,frag)?.commit()
        }

        return binding.root
    }

    private fun transaction() {
        val frag = UpdateMasterKeyFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.commit()
    }
}