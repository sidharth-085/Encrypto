package com.sid.encrypto.presentation.screens.card

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sid.encrypto.domain.model.Card
import com.sid.encrypto.data.repository.CardRepositoryImpl
import com.sid.encrypto.data.local.card.CardDatabase
import kotlinx.coroutines.launch

class CardViewModel(application: Application) : AndroidViewModel(application) {

    val readAllCardData: LiveData<List<Card>>
    private val cardRepositoryImpl: CardRepositoryImpl

    init {
        val cardDao = CardDatabase.getCardDatabase(application).cardDao()
        cardRepositoryImpl = CardRepositoryImpl(cardDao)
        readAllCardData = cardRepositoryImpl.readAllCard
    }

    fun addCard(card: Card){
        viewModelScope.launch {
            cardRepositoryImpl.addCard(card)
        }
    }

    fun editCard(card: Card){
        viewModelScope.launch {
            cardRepositoryImpl.editCard(card)
        }
    }

    fun deleteCard(card: Card){
        viewModelScope.launch {
            cardRepositoryImpl.deleteCard(card)
        }
    }
}