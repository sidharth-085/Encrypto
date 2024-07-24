package com.sid.encrypto.data.repository

import androidx.lifecycle.LiveData
import com.sid.encrypto.data.model.Key
import com.sid.encrypto.data.room.KeyDao

class KeyRepository(private val keyDao: KeyDao) {

    fun getMasterKey(): LiveData<List<Key>> = keyDao.getMasterKey()

    suspend fun setMasterKey(key: Key) {
        keyDao.setMasterKey(key)
    }
}