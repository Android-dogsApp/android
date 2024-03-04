//package com.example.dogsandddapters.Modules
//
//// src/com/example/yourapp/LoginFragment.kt
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import androidx.fragment.app.Fragment
//import com.example.dogsandddapters.R
//
//
//class LoginFragment : Fragment() {
//
//    private lateinit var buttonLogin: Button
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
//
//         buttonLogin = view.findViewById(R.id.buttonLogin)
//
//        buttonLogin.setOnClickListener {
//            // Handle login button click here
//            // You can add your authentication logic or navigation logic here
//        }
//
//        return view
//    }
//}
package com.example.dogsandddapters.Modules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.dogsandddapters.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest

class LogInFragment : Fragment() {

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
            val username = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Perform login logic here
        }

//        cancelButton.setOnClickListener {
//            val action = LogInFragmentDirections.actionLogInFragmentToEntryFragment()
//            Navigation.findNavController(view).navigate(action)
//        }


        return view
    }
}
