package com.sid.encrypto.presentation.screens.card.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.sid.encrypto.R
import com.sid.encrypto.domain.model.Card
import com.sid.encrypto.databinding.FragmentCardDetailsViewBinding
import com.sid.encrypto.databinding.OptionsModalBinding
import com.sid.encrypto.presentation.util.Util.Companion.createBottomSheet
import com.sid.encrypto.presentation.util.Util.Companion.setBottomSheet
import com.sid.encrypto.presentation.screens.card.CardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sid.encrypto.presentation.util.Encryption
import java.util.Locale

class CardDetailsViewFragment : Fragment() {

    private var _binding: FragmentCardDetailsViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var cardViewModel: CardViewModel
    private lateinit var currentCard: Card

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCardDetailsViewBinding.inflate(inflater,container,false)
        cardViewModel = ViewModelProvider(this)[CardViewModel::class.java]
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.GONE
        fetchData()
        binding.back.setOnClickListener {
            handleBackButton()
        }

        binding.copyBtn.setOnClickListener {
            val clipboard: ClipboardManager? = ContextCompat.getSystemService(
                requireContext(),
                ClipboardManager::class.java
            )
            val clip = ClipData.newPlainText("Card Number", binding.viewCardNumber.text.toString())
            clipboard?.setPrimaryClip(clip)
            Toast.makeText(requireContext(),"Card Number Copied", Toast.LENGTH_SHORT).show()
        }

        // Setting FLAG_SECURE to prevent screenshots
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // Clearing FLAG_SECURE when the fragment is destroyed
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    private fun showCard(cardType: String) {
        when(cardType.lowercase(Locale.ROOT).trim()){
            "visa" -> {
                binding.apply {
                    card.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.visa_bg,
                        null
                    )
                    demoCardType.setImageResource(R.drawable.ic_visa)
                }

            }
            "mastercard" -> {
                binding.apply {
                    card.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.mastercard_bg,
                        null
                    )
                    demoCardType.setImageResource(R.drawable.ic_mastercard)
                }

            }
            "rupay" -> {
                binding.apply {
                    card.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.rupay_bg,
                        null
                    )
                    demoCardType.setImageResource(R.drawable.rupay_logo)
                }

            }
            "american express" -> {
                binding.apply {
                    card.background = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.americanexpress_bg,
                        null
                    )
                    demoCardType.setImageResource(R.drawable.amex_logo)
                }

            }
        }
    }

    private fun fetchData() {
        val id = arguments?.getInt("id")
        val cardType = arguments?.getString("cardType").toString()
        val cardNumber = arguments?.getString("cardNumber").toString()
        val cardHolderName = arguments?.getString("cardName").toString()
        val cardExpireMonth = arguments?.getString("month").toString()
        val cardExpireYear = arguments?.getString("year").toString()
        val cardCVV = arguments?.getString("cvv").toString()

        binding.apply {
            viewCardNumber.setText(cardNumber)
            val formattedCardNumber = cardNumber.chunked(4).joinToString(" ")
            demoCardNumber.text = formattedCardNumber
            viewCardHolderName.setText(cardHolderName)
            demoCardHolderName.text = cardHolderName
            viewExpireMonth.setText(cardExpireMonth)
            demoExpiryMonth.text = cardExpireMonth
            viewExpireYear.setText(cardExpireYear)
            demoExpiryYear.text = cardExpireYear
            viewCvv.setText(cardCVV)
            demoCvv.text = cardCVV
        }
        showCard(cardType)
        currentCard = Card(id!! , cardType, cardNumber, cardHolderName, cardExpireMonth, cardExpireYear, cardCVV)

        setupDeleteButton()
    }

    private fun setupDeleteButton() {
        binding.delete.setOnClickListener {
            val encryptedCard = createEncryptedCard()
            cardViewModel.deleteCard(encryptedCard)
            Toast.makeText(requireContext(),"Successfully Deleted",Toast.LENGTH_SHORT).show()
            transaction()
        }
    }

    private fun createEncryptedCard(): Card {
        // Encrypting the data's
        val encryption = Encryption.getDefault("Key", "Salt", ByteArray(16))
        val encryptedCardNo = encryption.encryptOrNull(currentCard.cardNumber)
        val encryptedExpirationMonth = encryption.encryptOrNull(currentCard.expirationMonth)
        val encryptedExpirationYear = encryption.encryptOrNull(currentCard.expirationYear)
        val encryptedCVV = encryption.encryptOrNull(currentCard.cvv)

        return Card(
            currentCard.id,
            currentCard.cardType,
            encryptedCardNo,
            currentCard.cardHolderName,
            encryptedExpirationMonth,
            encryptedExpirationYear,
            encryptedCVV
        )
    }

    private fun handleBackButton() {

        val dialog = OptionsModalBinding.inflate(layoutInflater)
        val bottomSheet = requireContext().createBottomSheet()
        dialog.apply {

            optionsHeading.text = getString(R.string.discard)
            optionsContent.text = getString(R.string.discard_text)
            positiveOption.text = getString(R.string.DISCARD)
            positiveOption.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            negativeOption.text = getString(R.string.continue_editing)
            negativeOption.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            positiveOption.setOnClickListener {
                bottomSheet.dismiss()
                transaction()
            }
            negativeOption.setOnClickListener {
                bottomSheet.dismiss()

            }
        }
        dialog.root.setBottomSheet(bottomSheet)

    }

    private fun transaction() {
        val frag = CardFragment()
        val trans = fragmentManager?.beginTransaction()
        trans?.replace(R.id.fragment, frag)?.commit()
    }
}