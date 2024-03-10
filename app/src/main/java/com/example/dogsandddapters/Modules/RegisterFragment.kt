package com.example.dogsandddapters.Modules

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dogsandddapters.MainActivity
import com.example.dogsandddapters.Models.Person
import com.example.dogsandddapters.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var nameEditText: EditText? = null
    private var idEditText: EditText? = null
    private var phoneNumberEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var dogTypeEditText: EditText? = null
    private var registerButton: Button? = null
    private var cancelButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        nameEditText = view.findViewById(R.id.editTextName)
        idEditText = view.findViewById(R.id.editTextID)
        phoneNumberEditText = view.findViewById(R.id.editTextPhoneNumber)
        emailEditText = view.findViewById(R.id.editTextEmail)
        passwordEditText = view.findViewById(R.id.editTextPassword)
        dogTypeEditText = view.findViewById(R.id.editTextDogType)
        registerButton = view.findViewById(R.id.buttonRegister)
        cancelButton = view.findViewById(R.id.buttonCancel)



        cancelButton?.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToEntryFragment()
            Navigation.findNavController(view).navigate(action)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        registerButton?.setOnClickListener {
            val email = emailEditText?.text.toString().trim()
            val password = passwordEditText?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register user
            registerUser(email, password)
            Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_generalPostsFragment)
            (requireActivity() as MainActivity).setBottomBarVisibility(true)
            (requireActivity() as MainActivity).setAddMenuItemVisibility(true)
        }
    }

//    private fun registerUser(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    // Registration successful
//                    val name = nameEditText?.text.toString()
//                    val id = idEditText?.text.toString()
//                    val phoneNumber = phoneNumberEditText?.text.toString()
//                    val email = emailEditText?.text.toString()
//                    val dogType = dogTypeEditText?.text.toString()
//
//                    val person = Person(name, id, phoneNumber, email, dogType)
//
//                    // Save user data to Firestore
//                    val db = FirebaseFirestore.getInstance()
//                    db.collection("persons")
//                        .add(person)
//                        .addOnSuccessListener { documentReference ->
//                            Log.i(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//                        }
//                        .addOnFailureListener { e ->
//                            Log.i(TAG, "Error adding document", e)
//                        }
//
//                    Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
//                    // Navigate to next screen or perform other actions
//                } else {
//                    // Registration failed
//                    val exception = task.exception
//                    if (exception is FirebaseAuthUserCollisionException) {
//                        // Email is already in use
//                        Toast.makeText(context, "Email is already in use", Toast.LENGTH_SHORT).show()
//                    } else {
//                        // Other registration errors
//                        Toast.makeText(
//                            context,
//                            "Registration failed: ${exception?.message}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//            }
//    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Registration successful
                    val user = auth.currentUser
                    if (user != null) {
                        val userId = user.uid
                        val name = nameEditText?.text.toString()
                        val id = idEditText?.text.toString()
                        val phoneNumber = phoneNumberEditText?.text.toString()
                        val dogType = dogTypeEditText?.text.toString()

                        val person = Person(name, id, phoneNumber, email, dogType)

                        // Save user data to Firestore with the user's ID as the document ID
                        val db = FirebaseFirestore.getInstance()
                        db.collection("persons")
                            .document(userId)
                            .set(person)
                            .addOnSuccessListener {
                                Log.i(TAG, "DocumentSnapshot added with ID: $userId")
                            }
                            .addOnFailureListener { e ->
                                Log.i(TAG, "Error adding document", e)
                            }

                        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                        // Navigate to next screen or perform other actions
                    } else {
                        // Handle the case when user is null
                        Toast.makeText(context, "User is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Registration failed
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        // Email is already in use
                        Toast.makeText(context, "Email is already in use", Toast.LENGTH_SHORT).show()
                    } else {
                        // Other registration errors
                        Toast.makeText(
                            context,
                            "Registration failed: ${exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }



}
