package com.sid.encrypto.data.repository

import androidx.lifecycle.LiveData
import com.sid.encrypto.domain.model.Card
import com.sid.encrypto.data.local.card.CardDao
import com.sid.encrypto.domain.repository.CardRepository

class CardRepositoryImpl(
    private val cardDao: CardDao
): CardRepository {
    val readAllCard: LiveData<List<Card>> = cardDao.readAllCard()

    override suspend fun addCard(card: Card) {
        cardDao.addCard(card)
    }

    override suspend fun editCard(card: Card) {
        cardDao.editCard(card)
    }

    override suspend fun deleteCard(card: Card){
        cardDao.deleteCard(card)
    }
}