package com.example.app1

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.databinding.FragmentSecondBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SecondFragment : Fragment(), CardAdapter.CardClickListener {

    private var _binding: FragmentSecondBinding? = null
    data class RowData(val title: String, val cards: List<CardAdapter.CardData>)

    private val rows = listOf(
        RowData("Veikali", listOf(CardAdapter.CardData(R.drawable.store1, "Veikals 1"), CardAdapter.CardData(R.drawable.store2, "Veikals 2"))),
        RowData("Restorāni", listOf(CardAdapter.CardData(R.drawable.restorant1, "Restorāns 1"), CardAdapter.CardData(R.drawable.restorant2, "Restorāns 2"))),
        RowData("Konditorejas", listOf(CardAdapter.CardData(R.drawable.bakery1, "Konditoreja 1"), CardAdapter.CardData(R.drawable.bakery2, "Maiznīca 1"))),
        RowData("Kafejnīcas", listOf(CardAdapter.CardData(R.drawable.bakery1, "Kafejnīca 1"), CardAdapter.CardData(R.drawable.bakery2, "Kafejnīca 2"))),
        // Add more rows
    )
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layout = binding.linearLayout

        for (row in rows) {
            val title = TextView(requireContext()).apply {
                text = row.title
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
                setTextColor(Color.BLACK)
            }
            layout.addView(title)
            val recyclerView = RecyclerView(requireContext())
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = CardAdapter(row.cards, this) // Set the adapter
            layout.addView(recyclerView)
        }


        val navView: BottomNavigationView = view.findViewById(R.id.nav_view)
        val navController = findNavController()
        NavigationUI.setupWithNavController(navView, navController)
    }
    override fun onCardClick(item: CardAdapter.CardData) {
        val action = SecondFragmentDirections.actionSecondFragmentToDetailFragment(item.text)
        findNavController().navigate(action)
        //findNavController().navigate(R.id.action_secondFragment_to_detailFragment)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
class CardAdapter(private val items: List<CardData>, private val listener: CardClickListener) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    interface CardClickListener {
        fun onCardClick(item: CardData)
    }

    data class CardData(val imageResId: Int, val text: String)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.card_image)
        val text: TextView = view.findViewById(R.id.card_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.image.setImageResource(item.imageResId)
        holder.text.text = item.text
        holder.itemView.setOnClickListener { listener.onCardClick(item) }  // Set the click listener
    }

    override fun getItemCount(): Int = items.size
}