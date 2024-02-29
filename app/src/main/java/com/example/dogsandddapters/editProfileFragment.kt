package com.example.dogsandddapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dogsandddapters.Models.Person
import com.example.dogsandddapters.Models.PersonModel

class editProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val personModel = PersonModel.instance
        val editTextName: TextView = view.findViewById(R.id.editTextName)
        val editTextDogTypes: TextView = view.findViewById(R.id.editTextDogTypes)
        val editTextPhone: TextView = view.findViewById(R.id.editTextPhone)
        val editTextEmail: TextView = view.findViewById(R.id.editTextEmail)
        val buttonUpdate: Button = view.findViewById(R.id.buttonUpdate)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)

        buttonUpdate?.setOnClickListener {
            val id = "1"
            val name = editTextName.text.toString()
            val phoneNumber = editTextPhone.text.toString()
            val email = editTextEmail.text.toString()
            val dogType = editTextDogTypes.text.toString()
            val updatedPerson= Person(name, id, phoneNumber, email, dogType)

            PersonModel.instance.updatePerson(updatedPerson){
                //Navigation.findNavController(it).popBackStack(R.id.PersonPostsFragment, false)
            }

        }

        buttonCancel?.setOnClickListener {
//            val action = editProfileFragmentDirections.actioneditProfileFragmentToEntryFragment()
//            Navigation.findNavController(view).navigate(action)
        }

    }
}
