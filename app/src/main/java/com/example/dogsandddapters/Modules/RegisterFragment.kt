package com.example.dogsandddapters.Modules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dogsandddapters.Models.Person
import com.example.dogsandddapters.Models.PersonModel
import com.example.dogsandddapters.R

class RegisterFragment : Fragment() {

    private  var nameEditText: EditText? = null
    private  var idEditText: EditText? = null
    private  var phoneNumberEditText: EditText? = null
    private  var emailEditText: EditText? = null
    private  var dogTypeEditText: EditText? = null
    private  var registerButton: Button? = null
    private  var cancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        nameEditText = view.findViewById(R.id.editTextName)
        idEditText = view.findViewById(R.id.editTextID)
        phoneNumberEditText = view.findViewById(R.id.editTextPhoneNumber)
        emailEditText = view.findViewById(R.id.editTextEmail)
        dogTypeEditText = view.findViewById(R.id.editTextDogType)
        registerButton = view.findViewById(R.id.buttonRegister)
        cancelButton = view.findViewById(R.id.buttonCancel)

        registerButton?.setOnClickListener {
            val name = nameEditText?.text.toString()
            val id = idEditText?.text.toString()
            val phoneNumber = phoneNumberEditText?.text.toString()
            val email = emailEditText?.text.toString()
            val dogType = dogTypeEditText?.text.toString()

            val person = Person(name,id, phoneNumber, email,dogType)
            PersonModel.instance.addPerson(person) {
//                Navigation.findNavController(it).popBackStack(R.id.PersonPostsFragment, false)
//                val action = RegisterFragmentDirections.actionRegisterFragmentToGeneralPostsFragment()
//                Navigation.findNavController(view).navigate(action)
            }
        }

        cancelButton?.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToEntryFragment()
            Navigation.findNavController(view).navigate(action)
        }

        return view
    }

}



