package com.example.dogsandddapters.Modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.dogsandddapters.R

override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_register, container, false)

    val dogButton: Button = view.findViewById(R.id.btnDog)
    dogButton.setOnClickListener {
        val action = RegisterFragmentDirections.actionRegisterFragmentToDogRegisterFragment()
        Navigation.findNavController(view).navigate(action)
    }

    val adapterButton: Button = view.findViewById(R.id.btnAdapter)
    adapterButton.setOnClickListener {
        val action = RegisterFragmentDirections.actionRegisterFragmentToAdapterRegisterFragment()
        Navigation.findNavController(view).navigate(action)
    }

//    // Hide BottomNavigationView
//    if (activity is MainActivity) {
//        (activity as MainActivity).hideBottomNavigationView()
//    }



    return view
}


}