package com.example.dogsandddapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.dogsandddapters.Models.PersonModel
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private val args: ProfileFragmentArgs by navArgs()

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

        // Fetch user details using the email
        PersonModel.instance.getPersonByEmail(email) { person ->
            // Update UI with person data
            view.findViewById<TextView>(R.id.nameTextView).text = "Name: ${person?.name}"
            view.findViewById<TextView>(R.id.dogTypesTextView).text = "Dog Types: ${person?.dogType}"
            view.findViewById<TextView>(R.id.phoneTextView).text = "Phone: ${person?.phoneNumber}"
            view.findViewById<TextView>(R.id.emailTextView).text = "Email: ${person?.email}"
        }

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            // Perform logout functionality here
            logoutUser()
        }

        view.findViewById<Button>(R.id.myPostsButton).setOnClickListener {
            // Handle navigating to user's posts fragment
        }

        view.findViewById<Button>(R.id.editButton).setOnClickListener {
            // Handle navigating to edit profile fragment
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment("5")
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun logoutUser() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_entryFragment)
    }
}
