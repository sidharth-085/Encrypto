package com.sid.encrypto.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sid.encrypto.data.model.Key

@Dao
interface KeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setMasterKey(key: Key)

    @Query("SELECT * FROM master_key")
    fun getMasterKey(): LiveData<List<Key>>
}