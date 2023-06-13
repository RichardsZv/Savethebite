package com.example.app1

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.app1.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            val sharedPref = activity?.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            editor?.putString("email", email)  // email is the user's input
            editor?.apply()
            Log.d("FirstFragment", "Saved email: $email")
            if (email.isNotBlank() && password.isNotBlank()) {
                // Navigate to SecondFragment
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            } else {
                // Show error message
                Toast.makeText(requireContext(), "Lūdzu ievadi derīgu e-pastu un paroli", Toast.LENGTH_SHORT).show()
            }
        }

        binding.submitButton2.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }


        return binding.root
    }
}