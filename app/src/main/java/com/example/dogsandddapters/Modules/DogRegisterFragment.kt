package com.example.dogsandddapters.Modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.dogsandddapters.R

class DogRegisterFragment : Fragment() {

    private  var etAddDogName: EditText? =null
    private  var etAddStudentID: EditText? = null
    private  var etAddRepName: EditText? = null
    private  var etAddAge: EditText? = null
    private  var etAddSheltersLocation: EditText? = null
    private  var etAddEnergyLevel: EditText? = null
    private  var etAddBackyard: EditText? = null

    private  var btnAddDogSave: Button? = null
    private  var btnAddDogCancel: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dog_register, container, false)
    }

    private fun setupUI(view: View) {
        etAddDogName = view.findViewById(R.id.etAddAdapterName)
        etAddStudentID = view.findViewById(R.id.etAddDogID)
        etAddRepName = view.findViewById(R.id.etAddRepName)
        etAddAge = view.findViewById(R.id.etAddAge)
        etAddSheltersLocation = view.findViewById(R.id.etAddSheltersLocation)
        etAddEnergyLevel = view.findViewById(R.id.etAddEnergyLevel)
        etAddBackyard = view.findViewById(R.id.etAddBackyard)

        btnAddDogSave = view.findViewById(R.id.btnAddDogSave)
        btnAddDogCancel = view.findViewById(R.id.btnAddDogCancel)

        btnAddDogCancel?.setOnClickListener {
            // Navigation.findNavController(it).popBackStack(R.id.dogsFragment, false)
            val action = DogRegisterFragmentDirections.actionDogRegisterFragmentToDogsFragment()
            Navigation.findNavController(view).navigate(action)
        }



        btnAddDogSave?.setOnClickListener {
            val dogName = etAddDogName?.text.toString()
            val studentID = etAddStudentID?.text.toString()
            val repName = etAddRepName?.text.toString()
            val age = etAddAge?.text.toString()
            val sheltersLocation = etAddSheltersLocation?.text.toString()
            val energyLevel = etAddEnergyLevel?.text.toString()
            val backyard = etAddBackyard?.text.toString()

            // Navigation.findNavController(it).popBackStack(R.id.dogsFragment, false)

            val action = DogRegisterFragmentDirections.actionDogRegisterFragmentToDogsFragment()
            Navigation.findNavController(view).navigate(action)

        }
    }

}