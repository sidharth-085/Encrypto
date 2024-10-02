package com.sid.encrypto.data.local.card

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sid.encrypto.domain.model.Card

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCard(card: Card)

    @Update
    suspend fun editCard(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)

    @Query("SELECT * FROM card_table ORDER BY id ASC")
    fun readAllCard(): LiveData<List<Card>>
}