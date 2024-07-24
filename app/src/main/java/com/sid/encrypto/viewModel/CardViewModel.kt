package com.sid.encrypto.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sid.encrypto.data.model.Card
import com.sid.encrypto.data.repository.CardRepository
import com.sid.encrypto.data.room.CardDatabase
import kotlinx.coroutines.launch

class CardViewModel(application: Application) : AndroidViewModel(application) {

    val readAllCardData: LiveData<List<Card>>
    private val cardRepository: CardRepository

    init {
        val cardDao = CardDatabase.getCardDatabase(application).cardDao()
        cardRepository = CardRepository(cardDao)
        readAllCardData = cardRepository.readAllCard
    }

    fun addCard(card: Card){
        viewModelScope.launch {
            cardRepository.addCard(card)
        }
    }

    fun editCard(card: Card){
        viewModelScope.launch {
            cardRepository.editCard(card)
        }
    }

    fun deleteCard(card: Card){
        viewModelScope.launch {
            cardRepository.deleteCard(card)
        }
    }
}