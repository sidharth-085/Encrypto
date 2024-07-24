package com.sid.encrypto.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sid.encrypto.R
import com.sid.encrypto.data.model.Card
import com.sid.encrypto.databinding.CardLayoutBinding
import com.sid.encrypto.ui.fragments.card.CardDetailsViewFragment
import com.sid.encrypto.util.Encryption
import java.util.Locale

class CardAdapter(private val context: Context): RecyclerView.Adapter<CardAdapter.CardViewHolder>()
{
    private var cardList = emptyList<Card>()

    class CardViewHolder(val binding : CardLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            CardLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent , false)
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        val currentCard = cardList[position]
        val decryption = Encryption.getDefault("Key", "Salt", ByteArray(16))
        val decryptedNumber = decryption.decryptOrNull(currentCard.cardNumber)
        val maskedCardNumber = "**** **** **** ** " + decryptedNumber.takeLast(2)
        val decryptedMonth = decryption.decryptOrNull(currentCard.expirationMonth)
        val decryptedYear = decryption.decryptOrNull(currentCard.expirationYear)
        val decryptedCVV = decryption.decryptOrNull(currentCard.cvv)

        holder.binding.apply {
            cardNumber.text = maskedCardNumber
            month.text = decryptedMonth
            year.text = decryptedYear
            cardHolderName.text = currentCard.cardHolderName

            root.setOnClickListener {

                // Passing data to Edit fragments
                val bundle = Bundle()

                bundle.putSerializable("cardType", currentCard.cardType)
                bundle.putSerializable("cardNumber", decryptedNumber)
                bundle.putSerializable("cardName", currentCard.cardHolderName)
                bundle.putSerializable("month", decryptedMonth)
                bundle.putSerializable("year", decryptedYear)
                bundle.putSerializable("cvv", decryptedCVV)

                val fragment = CardDetailsViewFragment()
                fragment.arguments = bundle
                val fragmentManager = (context as AppCompatActivity).supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        when(currentCard.cardType.lowercase(Locale.ROOT).trim()){

            "visa" -> holder.binding.apply {
                atmCard.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.visa_bg
                )
                type.setImageResource(R.drawable.ic_visa)
            }
            "mastercard" -> {
                holder.binding.apply {
                    atmCard.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.mastercard_bg
                    )
                    type.setImageResource(R.drawable.ic_mastercard)
                }
            }
            "rupay" -> {
                holder.binding.apply {
                    atmCard.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.rupay_bg
                    )
                    type.setImageResource(R.drawable.rupay_logo)
                }
            }
            "american express" -> {
                holder.binding.apply {
                    atmCard.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.americanexpress_bg
                    )
                    type.setImageResource(R.drawable.amex_logo)
                }
            }
        }
    }

    override fun getItemCount() = cardList.size

    fun setCardData(card: List<Card>){
        cardList = card
        notifyDataSetChanged()
    }
}