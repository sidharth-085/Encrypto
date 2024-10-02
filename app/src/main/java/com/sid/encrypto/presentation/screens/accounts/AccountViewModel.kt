package com.sid.encrypto.presentation.screens.accounts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sid.encrypto.data.local.account.AccountDatabase
import com.sid.encrypto.domain.model.Account
import com.sid.encrypto.data.repository.AccountRepositoryImpl
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Account>>
    private val repository : AccountRepositoryImpl

    init {
        val accountDao = AccountDatabase.getDatabase(application).accountDao()
        repository = AccountRepositoryImpl(accountDao)
        readAllData = repository.readAllAccount
    }

    fun addAccount(account: Account){
        viewModelScope.launch{
            repository.addAccount(account)
        }
    }

    fun editAccount(account: Account){
        viewModelScope.launch{
            repository.editAccount(account)
        }
    }

    fun deleteAccount(account: Account){
        viewModelScope.launch {
            repository.deleteAccount(account)
        }
    }
    fun updateBookmark(account: Account) {
        viewModelScope.launch {
            repository.updateFavourite(account)
        }
    }
}