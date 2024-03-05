package com.example.dogsandddapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.dogsandddapters.Models.PersonModel

class ProfileFragment : Fragment() {
    // private lateinit var personModel: PersonModel
    private val args: ProfileFragmentArgs by navArgs()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        personModel = ViewModelProvider(this).get(PersonModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = args.email
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val dogTypesTextView: TextView = view.findViewById(R.id.dogTypesTextView)
        val phoneTextView: TextView = view.findViewById(R.id.phoneTextView)
        val emailTextView: TextView = view.findViewById(R.id.emailTextView)
        val numberOfDogsTextView: TextView = view.findViewById(R.id.numberOfDogsTextView)



        // Fetch user details using the email
        PersonModel.instance.getPersonByEmail(email) {

            // Update UI with person data
            nameTextView.text = "Name: ${it?.name}"
            Log.i("ProfileFragment", "name: $nameTextView.text")
            dogTypesTextView.text = "Dog Types: ${it?.dogType}"
            phoneTextView.text = "Phone: ${it?.phoneNumber}"
            emailTextView.text = "Email: ${it?.email}"
            //numberOfDogsTextView.text = "Number of Dogs: ${it?.numberOfDogs}"
        }


        val myPostsButton: Button = view.findViewById(R.id.myPostsButton)
        val editButton: Button = view.findViewById(R.id.editButton)

        myPostsButton.setOnClickListener {
            // Handle navigating to user's posts fragment
        }

        editButton.setOnClickListener {
            // Handle navigating to edit profile fragment
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment("5")
            Navigation.findNavController(view).navigate(action)
            }
       }
}