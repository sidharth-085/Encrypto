package com.sid.encrypto.domain.repository

import com.sid.encrypto.domain.model.Account

interface AccountRepository {
    suspend fun addAccount(account: Account)

    suspend fun editAccount(account: Account)

    suspend fun deleteAccount(account: Account)

    suspend fun updateFavourite(account: Account)
}