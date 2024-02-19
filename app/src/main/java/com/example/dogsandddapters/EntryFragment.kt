package com.example.dogsandddapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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
            val action = EntryFragmentDirections.actionEntryFragmentToLogInFragment()
            Navigation.findNavController(view).navigate(action)
        }
        // Hide BottomNavigationView
//        if (activity is MainActivity) {
//            (activity as MainActivity).hideBottomNavigationView()
//        }

        val preson= hashMapOf(
            "name" to "John",
            "id" to "123",
            "phoneNumber" to "123-456-7890",
            "email" to "jhon@jhon",
        )
         val db = Firebase.firestore
        db.collection("persons").add(preson)
            .addOnSuccessListener { documentReference ->
               //Log.d("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
               // Log.d("Error adding document $e")
            }



        return view
    }


}