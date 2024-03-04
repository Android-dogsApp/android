package com.example.dogsandddapters.Modules

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.HandlerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.Models.GeneralPostModel
import com.example.dogsandddapters.Models.PersonModel
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Models.PersonPostModel
import com.example.dogsandddapters.R
import java.util.concurrent.Executors

class EditPostFragment : Fragment() {
    private val args: EditPostFragmentArgs by navArgs()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = args.postId
        var executor = Executors.newSingleThreadExecutor()

        val personModel = PersonModel.instance
        val editTextPostId: TextView = view.findViewById(R.id.editTextPostId)
        val editTextRequest: TextView = view.findViewById(R.id.editTextRequest)
        val editTextOffer: TextView = view.findViewById(R.id.editTextOffer)
        val editTextcontact: TextView = view.findViewById(R.id.editTextcontact)
        val buttonUpdate: Button = view.findViewById(R.id.buttonSave)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)
        val buttonDeletePost: Button = view.findViewById(R.id.buttonDeletePost)

        GeneralPostModel.instance.getGeneralPostById(postId){
            editTextPostId.text = it?.postid
            editTextRequest.text = it?.request
            editTextOffer.text = it?.offer
            editTextcontact.text = it?.contact
        }

        //NEED UPDATE POST ONLY IF IT BELONGS TO THE USER!
        buttonUpdate?.setOnClickListener {
            val postid = postId
            val offer = editTextOffer.text.toString()
            val contact = editTextcontact.text.toString()
            val request = editTextRequest.text.toString()
            val publisher = postId
            val updatedGeneralPost = GeneralPost(postid, publisher, request, offer, contact)
            val updatedPersonPost = PersonPost(postid, publisher, request, offer, contact)
           try {
                GeneralPostModel.instance.updateGeneralPost(updatedGeneralPost) {
                }

                PersonPostModel.instance.updatePersonPost(updatedPersonPost) {
                }
           } catch (e: Exception) {
               // Log the exception
               Log.e(TAG, "Error performing database transaction", e)
           }

                val action =
                    EditPostFragmentDirections.actionEditPostFragmentToPersonSpecificPostFragment(postId)
                Navigation.findNavController(view).navigate(action)

        }


        buttonCancel?.setOnClickListener {
            Navigation.findNavController(it).navigateUp();
        }

        buttonDeletePost.setOnClickListener {
          GeneralPostModel.instance.getGeneralPostById(postId){
              if (it != null) {
                  GeneralPostModel.instance.deleteGeneralPost(it) {

                  }
              }
          }
            PersonPostModel.instance.getPersonPostById(postId){
                if (it != null) {
                    PersonPostModel.instance.deletePersonPost(it) {
                    }
                }

            }
            val action = EditPostFragmentDirections.actionEditPostFragmentToPersonPostsFragment()
            Navigation.findNavController(view).navigate(action)

        }


    }

}