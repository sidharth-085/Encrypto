package com.sid.encrypto.data.repository

import androidx.lifecycle.LiveData
import com.sid.encrypto.domain.model.Key
import com.sid.encrypto.data.local.key.KeyDao
import com.sid.encrypto.domain.repository.KeyRepository

class KeyRepositoryImpl(
    private val keyDao: KeyDao
): KeyRepository {

    fun getMasterKey(): LiveData<List<Key>> = keyDao.getMasterKey()

    override suspend fun setMasterKey(key: Key) {
        keyDao.setMasterKey(key)
    }
}