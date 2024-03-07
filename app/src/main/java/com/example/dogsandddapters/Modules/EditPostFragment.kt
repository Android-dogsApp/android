package com.example.dogsandddapters.Modules

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.Models.GeneralPostModel
import com.example.dogsandddapters.R
import java.util.concurrent.Executors

class EditPostFragment : Fragment() {
    private val args: EditPostFragmentArgs by navArgs()

    private val SELECT_IMAGE_REQUEST_CODE = 101
    private lateinit var selectedImageUri: Uri
    private lateinit var imageViewPost: ImageView
    private val imageUrls = arrayOf(
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSw-KlE6gSN9QGgFWYWYxjeZd9SfyEOxt15mg&usqp=CAU",
        "https://www.papiz.co.il/wp-content/uploads/2020/12/H59941b5682e2404dac4794401fa3dfddv.jpg",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSriFQ_gsAOYXxVpCe-FaecfRNSsuciw5zdmA&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTz7Yqjh1V3-MOcpecuRRBVCHcq_2xY9v0Mnw&usqp=CAU",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTwcbB_1LWRE9Giluf3d3IicQ73h7XKgIOA4Q&usqp=CAU"
    )

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

        val editTextPostId: TextView = view.findViewById(R.id.editTextPostId)
        val editTextRequest: TextView = view.findViewById(R.id.editTextRequest)
        val editTextOffer: TextView = view.findViewById(R.id.editTextOffer)
        val editTextcontact: TextView = view.findViewById(R.id.editTextcontact)
        val buttonUpdate: Button = view.findViewById(R.id.buttonSave)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)
        val buttonDeletePost: Button = view.findViewById(R.id.buttonDeletePost)
        imageViewPost = view.findViewById(R.id.imageViewPost)

        // Button to select image
        val buttonSelectImage: Button = view.findViewById(R.id.buttonSelectImage)
        buttonSelectImage.setOnClickListener {
            selectImageFromGallery()
        }

        val generalPostModel = GeneralPostModel.instance
        generalPostModel.getGeneralPostById(postId) { generalPost ->
            generalPost?.let {
                editTextPostId.text = it.postid
                editTextRequest.text = it.request
                editTextOffer.text = it.offer
                editTextcontact.text = it.contact
                loadImage(Uri.parse(it.imageUrl)) // Load image from the general post
            }

            buttonUpdate.setOnClickListener {
                val offer = editTextOffer.text.toString()
                val contact = editTextcontact.text.toString()
                val request = editTextRequest.text.toString()
                val updatedGeneralPost = GeneralPost(postId, request, offer, contact, selectedImageUri.toString())

                executor.execute {
                    generalPostModel.updateGeneralPost(updatedGeneralPost) {
                        // Handle update completion if needed
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
            generalPostModel.getGeneralPostById(postId) { generalPost ->
                generalPost?.let {
                    generalPostModel.deleteGeneralPost(it) {
                        // Handle deletion completion if needed
                    }
                }
            }

            val action = EditPostFragmentDirections.actionEditPostFragmentToPersonPostsFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    // Method to open gallery for image selection
    private fun selectImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        }

        val imageUris = imageUrls.map { Uri.parse(it) }.toTypedArray()

        val chooserIntent = Intent.createChooser(galleryIntent, "Select Image").apply {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, imageUris)
        }

        galleryLauncher.launch(chooserIntent)
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult: ActivityResult? ->
        activityResult?.let { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val clipData = data.clipData
                    if (clipData != null) {
                        // Multiple images selected
                        for (i in 0 until clipData.itemCount) {
                            val uri = clipData.getItemAt(i).uri
                            // Handle the selected image URI here
                            loadImage(uri)
                        }
                    } else {
                        // Single image selected
                        val uri = data.data
                        // Handle the selected image URI here
                        loadImage(uri)
                    }
                }
            }
        }
    }

    // Function to load image from URI into ImageView
    private fun loadImage(uri: Uri?) {
        uri?.let { // Use the safe call operator to check for nullability
            Glide.with(this)
                .load(it)
                .into(imageViewPost)
        }
    }
}
