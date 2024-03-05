package com.example.dogsandddapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.dogsandddapters.Models.Person
import com.example.dogsandddapters.Models.PersonModel
import com.example.dogsandddapters.Modules.EditPostFragmentArgs

class editProfileFragment : Fragment() {
    private val args: EditPostFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val personId = args.postId

        val personModel = PersonModel.instance
        val editTextName: TextView = view.findViewById(R.id.editTextName)
        val editTextDogTypes: TextView = view.findViewById(R.id.editTextDogTypes)
        val editTextPhone: TextView = view.findViewById(R.id.editTextPhone)
        val editTextEmail: TextView = view.findViewById(R.id.editTextEmail)
        val buttonUpdate: Button = view.findViewById(R.id.buttonUpdate)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)

        PersonModel.instance.getPerson(personId){
            editTextName.text = it?.name
            editTextDogTypes.text = it?.dogType
            editTextPhone.text = it?.phoneNumber
            editTextEmail.text = it?.email
        }

        buttonUpdate?.setOnClickListener {
            val id = personId
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
            val action = editProfileFragmentDirections.actionEditProfileFragmentToEntryFragment()
            Navigation.findNavController(view).navigate(action)
        }

    }
}
