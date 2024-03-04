////package com.example.dogsandddapters.Modules
////
////// src/com/example/yourapp/LoginFragment.kt
////
////import android.os.Bundle
////import android.view.LayoutInflater
////import android.view.View
////import android.view.ViewGroup
////import android.widget.Button
////import androidx.fragment.app.Fragment
////import com.example.dogsandddapters.R
////
////
////class LoginFragment : Fragment() {
////
////    private lateinit var buttonLogin: Button
////
////    override fun onCreateView(
////        inflater: LayoutInflater, container: ViewGroup?,
////        savedInstanceState: Bundle?
////    ): View? {
////        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
////
////         buttonLogin = view.findViewById(R.id.buttonLogin)
////
////        buttonLogin.setOnClickListener {
////            // Handle login button click here
////            // You can add your authentication logic or navigation logic here
////        }
////
////        return view
////    }
////}
//package com.example.dogsandddapters.Modules
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import com.example.dogsandddapters.R
//import com.google.firebase.auth.FirebaseAuth
//
//class LogInFragment : Fragment() {
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var emailEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var loginButton: Button
//    private lateinit var cancelButton: Button
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
//
//        emailEditText = view.findViewById(R.id.editTextEmail)
//        passwordEditText = view.findViewById(R.id.editTextPassword)
//        loginButton = view.findViewById(R.id.buttonLogin)
////        cancelButton = view.findViewById(R.id.buttonCancel)
//
//        loginButton.setOnClickListener {
//            val username = emailEditText.text.toString()
//            val password = passwordEditText.text.toString()
//
//            // Perform login logic here
//        }
//
////        cancelButton.setOnClickListener {
////            val action = LogInFragmentDirections.actionLogInFragmentToEntryFragment()
////            Navigation.findNavController(view).navigate(action)
////        }
//
//
//        return view
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Initialize Firebase Auth
//        auth = FirebaseAuth.getInstance()
//
//        loginButton.setOnClickListener {
//            val email = emailEditText.text.toString().trim()
//            val password = passwordEditText.text.toString().trim()
//
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Login user
//            loginUser(email, password)
//        }
//    }
//
//    private fun loginUser(email: String, password: String) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Login successful
//                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
//                    // Navigate to next screen or perform other actions
//                } else {
//                    // Login failed
//                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
//}


package com.example.dogsandddapters.Modules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dogsandddapters.R
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var cancelButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

        emailEditText = view.findViewById(R.id.editTextEmail)
        passwordEditText = view.findViewById(R.id.editTextPassword)
        loginButton = view.findViewById(R.id.buttonLogin)
//        cancelButton = view.findViewById(R.id.buttonCancel)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
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
                    val action = LogInFragmentDirections.actionLogInFragmentToProfileFragment()
                    Navigation.findNavController(requireView()).navigate(action)
                } else {
                    // Login failed
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
