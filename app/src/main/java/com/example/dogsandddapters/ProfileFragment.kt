package com.example.dogsandddapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dogsandddapters.Models.PersonModel

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

//        val nameEditText: EditText = view.findViewById(R.id.nameEditText)
//        val dogsCountEditText: EditText = view.findViewById(R.id.dogsCountEditText)
//        val dogTypesEditText: EditText = view.findViewById(R.id.dogTypesEditText)
//        val phoneEditText: EditText = view.findViewById(R.id.phoneEditText)
//        val emailEditText: EditText = view.findViewById(R.id.emailEditText)
//
//        val myPostsButton: Button = view.findViewById(R.id.myPostsButton)
//        val editButton: Button = view.findViewById(R.id.editButton)


        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val dogTypesTextView: TextView = view.findViewById(R.id.dogTypesTextView)
        val phoneTextView : TextView= view.findViewById(R.id.phoneTextView)
        val emailTextView: TextView = view.findViewById(R.id.emailTextView)

        //NEED TO GET THE USER ID...:
      PersonModel.instance.getPerson("4"){
          nameTextView.text = it?.name
          dogTypesTextView.text = it?.dogType
          phoneTextView.text = it?.phoneNumber
          emailTextView.text = it?.email
      }

        val myPostsButton: Button = view.findViewById(R.id.myPostsButton)
        val editButton: Button = view.findViewById(R.id.editButton)

        myPostsButton.setOnClickListener {
//            val action = ProfileFragmentDirections.actionProfileFragmentToPersonPostsFragment()
//            Navigation.findNavController(view).navigate(action)

        }
        editButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment("5")
            Navigation.findNavController(view).navigate(action)

        }
    }
}

