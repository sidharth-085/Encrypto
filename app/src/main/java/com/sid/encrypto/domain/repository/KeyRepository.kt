package com.sid.encrypto.domain.repository

import com.sid.encrypto.domain.model.Key

interface KeyRepository {
    suspend fun setMasterKey(key: Key)
}