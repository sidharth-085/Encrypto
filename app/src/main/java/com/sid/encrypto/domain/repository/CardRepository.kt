package com.sid.encrypto.domain.repository

import com.sid.encrypto.domain.model.Card

interface CardRepository {
    suspend fun addCard(card: Card)

    suspend fun editCard(card: Card)

    suspend fun deleteCard(card: Card)
}