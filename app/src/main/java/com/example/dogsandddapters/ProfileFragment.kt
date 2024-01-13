package com.example.dogsandddapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val dogsCountTextView : TextView= view.findViewById(R.id.dogsCountTextView)
        val dogTypesTextView: TextView = view.findViewById(R.id.dogTypesTextView)
        val phoneTextView : TextView= view.findViewById(R.id.phoneTextView)
        val emailTextView: TextView = view.findViewById(R.id.emailTextView)

        val myPostsButton: Button = view.findViewById(R.id.myPostsButton)
        val editButton: Button = view.findViewById(R.id.editButton)

        myPostsButton.setOnClickListener {

        }

        editButton.setOnClickListener {

        }
    }
}

