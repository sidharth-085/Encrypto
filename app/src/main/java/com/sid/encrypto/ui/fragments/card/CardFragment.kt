package com.sid.encrypto.ui.fragments.card

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sid.encrypto.R
import com.sid.encrypto.adapter.CardAdapter
import com.sid.encrypto.data.model.Card
import com.sid.encrypto.databinding.FragmentCardBinding
import com.sid.encrypto.util.Util
import com.sid.encrypto.viewModel.CardViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null
    private val binding get() = _binding!!

    private lateinit var cardViewModel: CardViewModel
    private lateinit var cardAdapter: CardAdapter
    private lateinit var cardRecyclerView: RecyclerView
    private var cardList = mutableListOf<Card>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        cardViewModel = ViewModelProvider(this)[CardViewModel::class.java]
        cardAdapter = CardAdapter(requireContext())
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav).visibility = View.VISIBLE

        // Setting up RecyclerView
        cardAdapter.notifyDataSetChanged()
        cardRecyclerView = binding.cardRecyclerView
        cardRecyclerView.apply {
            adapter = cardAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        cardViewModel.readAllCardData.observe(viewLifecycleOwner) { card ->
            cardList.clear()
            cardList.addAll(card)
            cardAdapter.setCardData(card)
            if (card.isEmpty()) {
                binding.emptyCardList.visibility = View.VISIBLE
            } else {
                binding.emptyCardList.visibility = View.GONE
            }
        }
        // Moving to AddCardDetailsFragment
        binding.cardFab.setOnClickListener {
            goToCardAddFragment()
        }
        setCardFilter()
        cardClearText()
        return binding.root

    }

    private fun cardClearText() {
        binding.apply {
            cardClearText.setOnClickListener {
                Util.hideKeyboard(requireActivity())
                it.visibility = View.GONE
                cardFilter.apply {
                    setText("")
                    setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0)
                    clearFocus()
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setCardFilter() {
        binding.cardFilter.apply {

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0.toString().isNotEmpty()) {
                        filter(Util.getBaseStringForFiltering(p0.toString().lowercase()))
                        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                        binding.cardClearText.visibility = View.VISIBLE
                    } else {
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0)
                        binding.cardClearText.visibility = View.GONE
                        cardAdapter.setCardData(cardList)
                    }
                }

            })

        }
    }
    private fun filter(searchCard: String) {
        val filterCardList = mutableListOf<Card>()
        if (searchCard.isNotEmpty()){
            for (card in cardList){
                if (Util.getBaseStringForFiltering(card.cardType.lowercase()).contains(searchCard)){
                    filterCardList.add(card)
                }
            }
            cardAdapter.setCardData(filterCardList)
        }else{
            cardAdapter.setCardData(cardList)
        }
    }
    private fun goToCardAddFragment() {
        val fragment = CardDetialsAddFragment()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment,fragment)?.addToBackStack( "tag" )?.commit()
    }
}