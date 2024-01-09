package com.example.dogsandddapters.Modules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.Navigation
import com.example.dogsandddapters.R
//import com.example.dogsandddapters.EntryFragmentDirections

class AdapterRegisterFragment : Fragment() {

    private var etAddAdapterName: EditText? =null
    private var etAddAdapterID: EditText? =null
    private var etAddAvailability: EditText? =null
    private var etAddAgeAdapter: EditText?=null
    private var etAddEconomicStatus: EditText?=null
    private var etAddPlaceOfResidence: EditText?=null
    private var etAddBackyard: EditText?=null

    private var btnSaveAdapter: Button?=null
    private var btnCancelAdapter: Button?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_adapter_register, container, false)
    }

    private fun setupUI(view: View) {
        etAddAdapterName = view.findViewById(R.id.etAddAdapterName)
        etAddAdapterID = view.findViewById(R.id.etAddAdapterID)
        etAddAvailability = view.findViewById(R.id.etAddAvailability)
        etAddAgeAdapter = view.findViewById(R.id.etAddAgeAdapter)
        etAddEconomicStatus = view.findViewById(R.id.etAddEconomicStatus)
        etAddPlaceOfResidence= view.findViewById(R.id.etAddPlaceOfResidence)
        etAddBackyard = view.findViewById(R.id.etAddBackyard)
        btnSaveAdapter = view.findViewById(R.id.btnAddAdapterSave)
        btnCancelAdapter = view.findViewById(R.id.btnAddAdapterCancel)

        btnCancelAdapter?.setOnClickListener {
            //Navigation.findNavController(it).popBackStack(R.id.adaptersFragment, false)

            val action = AdapterRegisterFragmentDirections.actionAdapterRegisterFragmentToAdaptersFragment()
            Navigation.findNavController(view).navigate(action)
        }

        btnSaveAdapter?.setOnClickListener {

            val adapterName = etAddAdapterName?.text.toString()
            val adapterID = etAddAdapterID?.text.toString()
            val availability = etAddAvailability?.text.toString()
            val ageAdapter = etAddAgeAdapter?.text.toString()
            val economicStatus = etAddEconomicStatus?.text.toString()
            val placeOfResidence = etAddPlaceOfResidence?.text.toString()
            val backyard = etAddBackyard?.text.toString()

            val action = AdapterRegisterFragmentDirections.actionAdapterRegisterFragmentToAdaptersFragment()
            Navigation.findNavController(view).navigate(action)
        }

    }

}