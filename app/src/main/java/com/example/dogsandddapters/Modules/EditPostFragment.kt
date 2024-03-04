//package com.example.dogsandddapters.Modules
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.navArgs
//import com.example.dogsandddapters.Models.GeneralPost
//import com.example.dogsandddapters.Models.GeneralPostModel
//import com.example.dogsandddapters.Models.PersonModel
//import com.example.dogsandddapters.Models.PersonPost
//import com.example.dogsandddapters.Models.PersonPostModel
//import com.example.dogsandddapters.R
//import java.util.concurrent.Executors
//
//class EditPostFragment : Fragment() {
//    private val args: EditPostFragmentArgs by navArgs()
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_edit_post, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val postId = args.postId
//        var executor = Executors.newSingleThreadExecutor()
//
//        val personModel = PersonModel.instance
//        val editTextPostId: TextView = view.findViewById(R.id.editTextPostId)
//        val editTextRequest: TextView = view.findViewById(R.id.editTextRequest)
//        val editTextOffer: TextView = view.findViewById(R.id.editTextOffer)
//        val editTextEmail: TextView = view.findViewById(R.id.editTextEmail1)
//        val buttonUpdate: Button = view.findViewById(R.id.buttonSave)
//        //val buttonCancel: Button = view.findViewById(R.id.buttonCancel)
//
//        GeneralPostModel.instance.getGeneralPostById(postId){
//            editTextPostId.text = it?.postid
//            editTextRequest.text = it?.request
//            editTextOffer.text = it?.offer
//            editTextEmail.text = it?.contact
//        }
//
//        //NEED UPDATE POST ONLY IF IT BELONGS TO THE USER!
//        buttonUpdate?.setOnClickListener {
//            val postid = postId
//            val offer = editTextOffer.text.toString()
//            val contact = editTextEmail.text.toString()
//            val request = editTextRequest.text.toString()
//            val publisher = postId
//            val updatedGeneralPost = GeneralPost(postid, publisher, request, offer, contact)
//            val updatedPersonPost = PersonPost(postid, publisher, request, offer, contact)
//
//            executor.execute {
//                GeneralPostModel.instance.updateGeneralPost(updatedGeneralPost) {
//                    //Navigation.findNavController(it).popBackStack(R.id.PersonPostsFragment, false)
//
//                }
//
//                PersonPostModel.instance.updatePersonPost(updatedPersonPost) {
//                    //Navigation.findNavController(it).popBackStack(R.id.PersonPostsFragment, false)
//                }
//            }
//        }
//
//
//
////        buttonCancel?.setOnClickListener {
////            val action = editProfileFragmentDirections.actionEditProfileFragmentToEntryFragment()
////            Navigation.findNavController(view).navigate(action)
////        }
//
//    }
//
//
//}

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.Models.GeneralPostModel
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Models.PersonPostModel
import com.example.dogsandddapters.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        val editTextPostId: TextView = view.findViewById(R.id.editTextPostId)
        val editTextRequest: TextView = view.findViewById(R.id.editTextRequest)
        val editTextOffer: TextView = view.findViewById(R.id.editTextOffer)
        val editTextEmail: TextView = view.findViewById(R.id.editTextEmail1)
        val buttonUpdate: Button = view.findViewById(R.id.buttonSave)

        GeneralPostModel.instance.getGeneralPostById(postId) { generalPost ->
            editTextPostId.text = generalPost?.postid
            editTextRequest.text = generalPost?.request
            editTextOffer.text = generalPost?.offer
            editTextEmail.text = generalPost?.contact
        }

        buttonUpdate.setOnClickListener {
            val offer = editTextOffer.text.toString()
            val contact = editTextEmail.text.toString()
            val request = editTextRequest.text.toString()
            val publisher = postId
            val updatedGeneralPost = GeneralPost(postId, publisher, request, offer, contact)
            val updatedPersonPost = PersonPost(postId, publisher, request, offer, contact)

            // Using lifecycleScope to launch coroutine
            view.lifecycleScope.launch(Dispatchers.IO) {
                GeneralPostModel.instance.updateGeneralPost(updatedGeneralPost) {
                    // Handle result if needed
                }

                PersonPostModel.instance.updatePersonPost(updatedPersonPost) {
                    // Handle result if needed
                }
            }
        }
    }
}
