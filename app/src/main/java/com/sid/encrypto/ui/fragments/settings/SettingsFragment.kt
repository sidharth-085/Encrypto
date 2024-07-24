package com.sid.encrypto.ui.fragments.settings


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sid.encrypto.R
import com.sid.encrypto.adapter.SettingsItemAdapter
import com.sid.encrypto.data.model.SettingsItem
import com.sid.encrypto.databinding.AboutusModalBinding
import com.sid.encrypto.databinding.FragmentSettingsBinding
import com.sid.encrypto.util.Util.Companion.createBottomSheet
import com.sid.encrypto.util.Util.Companion.setBottomSheet
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsItemList: ArrayList<SettingsItem>
    private lateinit var settingsItemAdapter: SettingsItemAdapter

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE

        handleOperations()
        return binding.root
    }

    private fun handleOperations() {
        setupSettingsOptions()
    }

    private fun setupSettingsOptions() {

        settingsItemList = ArrayList()
        settingsItemList.apply {
            add(SettingsItem(0, R.drawable.lock, "Secure Encrypto"))
            add(SettingsItem(1, R.drawable.feedback, "Request Features"))
            add(SettingsItem(2, R.drawable.info, "About"))
        }
        settingsItemAdapter = SettingsItemAdapter(requireContext(), settingsItemList) {
            when(it) {
                0 -> {
                    transaction()
                }
                1 -> {
                    giveFeedback()
                }
                2 -> {
                    aboutApp()
                }
            }
        }
        binding.settingsRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = settingsItemAdapter
        }

    }

    private fun transaction() {
        val frag = SecurityFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment,frag)?.addToBackStack(null)?.commit()
    }

    private fun giveFeedback() {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(requireContext().resources.getString(R.string.mailTo))
        startActivity(openURL)
    }

    private fun aboutApp() {
        val dialog = AboutusModalBinding.inflate(layoutInflater)
        val bottomSheet = requireContext().createBottomSheet()
        dialog.apply {

            optionsContent.text = getString(R.string.about_app_text)
            optionsContent1.text = getString(R.string.about_app_text_second)

        }
        dialog.root.setBottomSheet(bottomSheet)
    }
}