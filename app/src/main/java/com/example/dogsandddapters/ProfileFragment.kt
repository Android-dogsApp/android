package com.example.dogsandddapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.dogsandddapters.Models.PersonModel
import com.example.dogsandddapters.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private val args: ProfileFragmentArgs by navArgs()
    private lateinit var viewModel: PersonViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[PersonViewModel::class.java]
        // inflater.inflate(R.layout.fragment_profile, container, false)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = args.email
        var userId= args.userId
        // Fetch user details using the email
        viewModel.person= PersonModel.instance.getPersonByEmail(email) { person ->
                // Update UI with person data
                view.findViewById<TextView>(R.id.nameTextView).text = "Name: ${person?.name}"
                view.findViewById<TextView>(R.id.dogTypesTextView).text = "Dog Type: ${person?.dogType}"
                view.findViewById<TextView>(R.id.phoneTextView).text = "Phone: ${person?.phoneNumber}"
                view.findViewById<TextView>(R.id.emailTextView).text = "Email: ${person?.email}"
                if (person != null) {
                    userId= person.id
                }
        }

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            // Perform logout functionality here
            logoutUser()
        }

        view.findViewById<Button>(R.id.editButton).setOnClickListener {
            // Handle navigating to edit profile fragment
            Log.i("ProfileFragment", "MoveToEdit-userId: $userId")
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(email)
            Navigation.findNavController(view).navigate(action)
        }
        val myPostsButton: Button = binding.moveToMyPostsButton
        myPostsButton.setOnClickListener {
            val action = ProfileFragmentDirections.actionProfileFragmentToPersonPostsFragment(userId)
            Navigation.findNavController(view).navigate(action)

        }

    }

    private fun logoutUser() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_entryFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
