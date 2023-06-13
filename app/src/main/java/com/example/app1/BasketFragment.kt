package com.example.app1

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.databinding.FragmentBasketBinding
import com.example.app1.databinding.FragmentSecondBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class BasketFragment : Fragment() {
    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val basketItems = Basket.items
        val listView = view.findViewById<ListView>(R.id.basket_list)

        // Creating a custom ArrayAdapter
        val adapter = BasketAdapter(requireContext(), basketItems)
        listView.adapter = adapter


        val navView: BottomNavigationView = view.findViewById(R.id.nav_view)
        val navController = findNavController()
        NavigationUI.setupWithNavController(navView, navController)
    }

}

class BasketAdapter(context: Context, private val items: List<InnerCardAdapter.CardData>) :
    ArrayAdapter<InnerCardAdapter.CardData>(context, R.layout.item_basket, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_basket, parent, false)

        val item = items[position]

        val textView = view.findViewById<TextView>(R.id.basket_item_text)
        val priceView = view.findViewById<TextView>(R.id.basket_item_price)

        textView.text = item.text
        priceView.text = item.price

        return view
    }
}