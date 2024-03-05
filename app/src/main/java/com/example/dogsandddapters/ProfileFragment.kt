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
        // Code to log out the user
        // For example, if you are using Firebase Authentication, you would call FirebaseAuth.getInstance().signOut();
        // If you are using any other authentication mechanism, you should log the user out according to that mechanism.

        // After logging out the user, navigate to the EntryFragment
        // Replace "EntryFragment" with the actual name of your EntryFragment class
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_entryFragment)
    }
}
