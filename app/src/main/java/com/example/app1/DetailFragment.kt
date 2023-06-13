package com.example.app1

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app1.databinding.FragmentDetailBinding
import com.example.app1.databinding.FragmentSecondBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    data class RowData(val title: String, val cards: List<InnerCardAdapter.CardData>)

    private val rows = listOf(
        RowData("Title 1", listOf(
            InnerCardAdapter.CardData(R.drawable.image, "Produkts 1", "0.99€"),
            InnerCardAdapter.CardData(R.drawable.image, "Produkts 2", "1.25€"),
        )),
        RowData("", listOf(
            InnerCardAdapter.CardData(R.drawable.image, "Produkts 3", "1.25€"),
            InnerCardAdapter.CardData(R.drawable.image, "Produkts 4", "1.25€"),
        )),
        RowData("Title 1", listOf(
            InnerCardAdapter.CardData(R.drawable.image, "Produkts 5", "1.25€"),
            InnerCardAdapter.CardData(R.drawable.image, "Produkts 6", "1.25€"),
        )),
        RowData("", listOf(
            InnerCardAdapter.CardData(R.drawable.image, "Produkts 7", "1.25€"),
            InnerCardAdapter.CardData(R.drawable.image, "Produkts 8", "1.25"),
        ))
    )
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layout = binding.linearLayout
        val cardText = arguments?.getString("cardTitle")
        val title = TextView(requireContext()).apply {
            text = cardText + " - Produkti"
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f)
            setTextColor(Color.BLACK)

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width
                LinearLayout.LayoutParams.WRAP_CONTENT  // Height
            ).apply {
                // Convert 16dp to pixels
                val topMarginInDp = 50
                val topMarginInPx = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    topMarginInDp.toFloat(),
                    resources.displayMetrics
                ).toInt()

                // Set top margin
                setMargins(0, topMarginInPx, 0, 0)
            }

            // Apply the layout parameters to the TextView
            this.layoutParams = layoutParams
        }
        layout.addView(title)
        for (row in rows) {

            val recyclerView = RecyclerView(requireContext())
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            recyclerView.adapter = InnerCardAdapter(row.cards)
            layout.addView(recyclerView)
        }


        val navView: BottomNavigationView = view.findViewById(R.id.nav_view)
        val navController = findNavController()
        NavigationUI.setupWithNavController(navView, navController)
    }

}

class InnerCardAdapter(private val items: List<CardData>) : RecyclerView.Adapter<InnerCardAdapter.ViewHolder>() {

    data class CardData(val imageResId: Int, val text: String, val price: String)

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.card_image)
        val text: TextView = view.findViewById(R.id.card_text)
        val price: TextView = view.findViewById(R.id.card_price)
        val button: Button = view.findViewById(R.id.card_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inner_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.image.setImageResource(item.imageResId)
        holder.text.text = item.text
        holder.price.text = item.price
        holder.button.setOnClickListener {
            // Add the item to the basket
            Basket.items.add(item)

            // Display a toast
            Toast.makeText(holder.itemView.context, "Prece pievienota grozam", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = items.size
}

object Basket {
    val items = mutableListOf<InnerCardAdapter.CardData>()
}