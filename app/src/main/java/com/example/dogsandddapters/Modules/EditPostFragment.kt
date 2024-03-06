package com.example.dogsandddapters.Modules

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
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.Models.GeneralPostModel
import com.example.dogsandddapters.Models.PersonModel
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Models.PersonPostModel
import com.example.dogsandddapters.R
import java.util.concurrent.Executors

class EditPostFragment : Fragment() {
    private val args: EditPostFragmentArgs by navArgs()

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
        var publisher: String?

        GeneralPostModel.instance.getGeneralPostById(postId) {
            editTextPostId.text = it?.postid
            editTextRequest.text = it?.request
            editTextOffer.text = it?.offer
            editTextcontact.text = it?.contact
            publisher = it?.publisher
            //NEED UPDATE POST ONLY IF IT BELONGS TO THE USER!
            buttonUpdate?.setOnClickListener {
                Log.i("TAG", "EditPostFragment: publisher $publisher")
                val postid = postId
                val offer = editTextOffer.text.toString()
                val contact = editTextcontact.text.toString()
                val request = editTextRequest.text.toString()
                val updatedGeneralPost = GeneralPost(postid, publisher, request, offer, contact)
                val updatedPersonPost = PersonPost(postid, publisher, request, offer, contact)

                executor.execute {
                    GeneralPostModel.instance.updateGeneralPost(updatedGeneralPost) {
                        //Navigation.findNavController(it).popBackStack(R.id.PersonPostsFragment, false)
                    }

                    PersonPostModel.instance.updatePersonPost(updatedPersonPost) {
                        //Navigation.findNavController(it).popBackStack(R.id.PersonPostsFragment, false)
                    }
                }

                val action = EditPostFragmentDirections.actionEditPostFragmentToPersonSpecificPostFragment(postId)
                Navigation.findNavController(view).navigate(action)
            }
        }


        buttonCancel?.setOnClickListener {
            Navigation.findNavController(it).navigateUp();
        }

        buttonDeletePost.setOnClickListener {
            GeneralPostModel.instance.getGeneralPostById(postId) {
                if (it != null) {
                    GeneralPostModel.instance.deleteGeneralPost(it) {
                    }
                }

            }
            PersonPostModel.instance.getPersonPostById(postId) {
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