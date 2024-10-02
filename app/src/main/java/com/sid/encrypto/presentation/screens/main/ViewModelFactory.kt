package com.sid.encrypto.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.sid.encrypto.presentation.screens.auth.KeyViewModel


class ViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T =
        with(modelClass) {
            val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
            when {
                isAssignableFrom(KeyViewModel::class.java) -> {
                    KeyViewModel(application)
                }
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            }
        } as T
}