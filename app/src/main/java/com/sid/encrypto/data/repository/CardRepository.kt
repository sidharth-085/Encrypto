package com.sid.encrypto.data.repository

import androidx.lifecycle.LiveData
import com.sid.encrypto.data.model.Card
import com.sid.encrypto.data.room.CardDao

class CardRepository(private val cardDao: CardDao) {
    val readAllCard: LiveData<List<Card>> = cardDao.readAllCard()

    suspend fun addCard(card: Card){
        cardDao.addCard(card)
    }

    suspend fun editCard(card: Card){
        cardDao.editCard(card)
    }

    suspend fun deleteCard(card: Card){
        cardDao.deleteCard(card)
    }

}