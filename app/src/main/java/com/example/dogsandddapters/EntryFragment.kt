package com.example.dogsandddapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation


class EntryFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_entry, container, false)

        val registerButton: Button = view.findViewById(R.id.btnRegister)
        registerButton.setOnClickListener {
            val action = EntryFragmentDirections.actionEntryFragmentToRegisterFragment()
            Navigation.findNavController(view).navigate(action)
        }

        val loginButton: Button = view.findViewById(R.id.btnLogin)
        loginButton.setOnClickListener {
            val action = EntryFragmentDirections.actionEntryFragmentToLoginFragment()
            Navigation.findNavController(view).navigate(action)
        }
        // Hide BottomNavigationView
//        if (activity is MainActivity) {
//            (activity as MainActivity).hideBottomNavigationView()
//        }



        return view
    }


}