package com.example.dogsandddapters.Modules

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.dogsandddapters.R
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var EmailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var cancelButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

        EmailEditText = view.findViewById(R.id.editTextEmail)
        passwordEditText = view.findViewById(R.id.editTextPassword)
        loginButton = view.findViewById(R.id.buttonLogin)
//        cancelButton = view.findViewById(R.id.buttonCancel)

        loginButton.setOnClickListener {
            val email = EmailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Login user
            loginUser(email, password)
        }

//        cancelButton.setOnClickListener {
//            val action = LogInFragmentDirections.actionLogInFragmentToEntryFragment()
//            Navigation.findNavController(view).navigate(action)
//        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        if (isLoggedIn()) {
            navigateToGeneralPosts()
        } else {
            // Show the login screen
            // Implement your login logic here
            loginButton.setOnClickListener {
                val email = EmailEditText.text.toString()
                val password = passwordEditText.text.toString()

                // Perform login authentication here
                // For demonstration, let's assume login is successful
                saveLoginCredentials(email, password)
                navigateToGeneralPosts()
            }
        }

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Login successful
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                    // Navigate to ProfileFragment
                    val action = LogInFragmentDirections.actionLogInFragmentToProfileFragment(email,"")
                    Navigation.findNavController(requireView()).navigate(action)
                } else {
                    // Login failed
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun isLoggedIn(): Boolean {
        return sharedPreferences.contains("email") && sharedPreferences.contains("password")
    }


    private fun navigateToGeneralPosts() {
        Navigation.findNavController(requireView()).navigate(R.id.action_logInFragment_to_generalPostsFragment)
    }

    private fun saveLoginCredentials(email: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }
}
