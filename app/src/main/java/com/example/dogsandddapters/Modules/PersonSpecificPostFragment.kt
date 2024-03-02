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
import com.example.dogsandddapters.Models.GeneralPostModel

class PersonSpecificPostFragment : Fragment() {
    private val args: PersonSpecificPostFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_general_post_specific, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = args.postId
        Log.i("TAG", " PersonSpecificPostFragment: Post ID $postId")
        val textViewRequest: TextView = view.findViewById(R.id.textViewRequest)
        val textViewOffer: TextView = view.findViewById(R.id.textViewOffer)
        val textViewPhoneNumber : TextView = view.findViewById(R.id.textViewPhoneNumber)
        val textViewEmail: TextView = view.findViewById(R.id.textViewEmail)


       GeneralPostModel.instance.getGeneralPostById(postId){
            textViewRequest.text = it?.request
            textViewOffer.text = it?.offer
            textViewEmail.text = it?.contact
       }

        //CAN EDIT POST ONLY IF IT BELONGS TO THE USER!
        val editButton: Button = view.findViewById(R.id.buttonEdit)

        editButton.setOnClickListener {
            val action = PersonSpecificPostFragmentDirections.actionPersonSpecificPostFragmentToEditPostFragment(postId)
            Navigation.findNavController(view).navigate(action)

        }
    }



}