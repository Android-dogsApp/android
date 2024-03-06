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

class editProfileFragment : Fragment() {
    private val args: editProfileFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val personId = args.postId
        val email= args.email

        val personModel = PersonModel.instance
        val editTextName: TextView = view.findViewById(R.id.editTextName)
        val editTextDogTypes: TextView = view.findViewById(R.id.editTextDogTypes)
        val editTextPhone: TextView = view.findViewById(R.id.editTextPhone)
        val editTextEmail: TextView = view.findViewById(R.id.editTextEmail)
        val buttonUpdate: Button = view.findViewById(R.id.buttonUpdate)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)
        var personId= ""

        PersonModel.instance.getPersonByEmail(email){
            Log.i("EditProfileFragment", "email : ${email}")
            editTextName.text = it?.name
            Log.i("EditProfileFragment", " editTextName: ${editTextName.text} ")
            editTextDogTypes.text = it?.dogType
            editTextPhone.text = it?.phoneNumber
            editTextEmail.text = it?.email
            personId= it?.id.toString()
            Log.i("EditProfileFragment", " personId: ${personId} ")
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
