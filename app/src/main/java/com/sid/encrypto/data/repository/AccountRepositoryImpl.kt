package com.sid.encrypto.data.repository

import androidx.lifecycle.LiveData
import com.sid.encrypto.data.local.account.AccountDao
import com.sid.encrypto.domain.model.Account
import com.sid.encrypto.domain.repository.AccountRepository


class AccountRepositoryImpl(
    private val accountDao: AccountDao
): AccountRepository {
    val readAllAccount: LiveData<List<Account>> = accountDao.readAllAccount()

    override suspend fun addAccount(account: Account) {
        accountDao.addAccount(account)
    }

    override suspend fun editAccount(account: Account) {
        accountDao.editAccount(account)
    }

    override suspend fun deleteAccount(account: Account) {
        accountDao.deleteAccount(account)
    }

    override suspend fun updateFavourite(account: Account) {
        val id  = account.id
        val test:Boolean = accountDao.updateFavourite(id)
        if (test) accountDao.isNotSelected(id)
        else accountDao.isSelected(id)
    }
}