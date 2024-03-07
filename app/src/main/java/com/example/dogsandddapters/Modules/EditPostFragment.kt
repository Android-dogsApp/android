package com.example.dogsandddapters.Modules

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.Models.GeneralPostModel
import com.example.dogsandddapters.Models.PersonModel
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Models.PersonPostModel
import com.example.dogsandddapters.R
import java.io.IOException
import java.util.concurrent.Executors

class EditPostFragment : Fragment() {
    private val args: EditPostFragmentArgs by navArgs()

    private val SELECT_IMAGE_REQUEST_CODE = 101
    private var selectedImage: String? = null

    private lateinit var imageViewPost: ImageView

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
        val executor = Executors.newSingleThreadExecutor()

        val personModel = PersonModel.instance
        val editTextPostId: EditText = view.findViewById(R.id.editTextPostId)
        val editTextRequest: EditText = view.findViewById(R.id.editTextRequest)
        val editTextOffer: EditText = view.findViewById(R.id.editTextOffer)
        val editTextcontact: EditText = view.findViewById(R.id.editTextcontact)
        val buttonUpdate: Button = view.findViewById(R.id.buttonSave)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)
        val buttonDeletePost: Button = view.findViewById(R.id.buttonDeletePost)
        imageViewPost = view.findViewById(R.id.imageViewPost)

        // Button to select image
        val buttonSelectImage: Button = view.findViewById(R.id.buttonSelectImage)
        buttonSelectImage.setOnClickListener {
            selectImageFromGallery()
        }

        GeneralPostModel.instance.getGeneralPostById(postId) {
            editTextPostId.setText(it?.postid)
            editTextRequest.setText(it?.request)
            editTextOffer.setText(it?.offer)
            editTextcontact.setText(it?.contact)
            val publisher = it?.publisher
            //NEED UPDATE POST ONLY IF IT BELONGS TO THE USER!
            buttonUpdate.setOnClickListener {
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

        buttonCancel.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        buttonDeletePost.setOnClickListener {
            GeneralPostModel.instance.getGeneralPostById(postId) { generalPost ->
                if (generalPost != null) {
                    GeneralPostModel.instance.deleteGeneralPost(generalPost) {
                    }
                }
            }
            PersonPostModel.instance.getPersonPostById(postId) { personPost ->
                if (personPost != null) {
                    PersonPostModel.instance.deletePersonPost(personPost) {
                    }
                }
            }
            val action = EditPostFragmentDirections.actionEditPostFragmentToPersonPostsFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    // Method to open gallery for image selection
    private fun selectImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, SELECT_IMAGE_REQUEST_CODE)
    }

    // Handle result from image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            try {
                selectedImageUri?.let {
                    // Set the selected image URI to your ImageView
                    val inputStream = requireActivity().contentResolver.openInputStream(selectedImageUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageViewPost.setImageBitmap(bitmap)

                    // Save the selected image URI
                    selectedImage = selectedImageUri.toString()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
