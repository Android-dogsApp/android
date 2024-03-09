package com.example.dogsandddapters.Modules

import android.app.Activity
import android.content.Intent
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
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Models.PersonPostModel
import com.example.dogsandddapters.R

class EditPostFragment : Fragment() {
    private val args: EditPostFragmentArgs by navArgs()

    private lateinit var editTextPostId: EditText
    private lateinit var editTextRequest: EditText
    private lateinit var editTextOffer: EditText
    private lateinit var editTextContact: EditText
    private lateinit var buttonCancel: Button
    private lateinit var buttonDeletePost: Button
    private lateinit var buttonSelectImage: Button
    private lateinit var buttonSave: Button
    private lateinit var imageView: ImageView
    private var publisher: String? = null

    private val PICK_IMAGE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        editTextPostId = view.findViewById(R.id.editTextPostId)
        editTextRequest = view.findViewById(R.id.editTextRequest)
        editTextOffer = view.findViewById(R.id.editTextOffer)
        editTextContact = view.findViewById(R.id.editTextContact)
        buttonCancel = view.findViewById(R.id.buttonCancel) // Corrected ID
        buttonDeletePost = view.findViewById(R.id.buttonDeletePost)
        buttonSelectImage = view.findViewById(R.id.btnSelectImage)
        buttonSave = view.findViewById(R.id.buttonSave)
        imageView = view.findViewById(R.id.imageView)

        buttonSelectImage.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, PICK_IMAGE)
        }

        buttonSave.setOnClickListener {
            val postid = editTextPostId.text.toString()
            val offer = editTextOffer.text.toString()
            val contact = editTextContact.text.toString()
            val request = editTextRequest.text.toString()
            val updatedGeneralPost = GeneralPost(postid, publisher, request, offer, contact)
            val updatedPersonPost = PersonPost(postid, publisher, request, offer, contact)

            GeneralPostModel.instance.updateGeneralPost(updatedGeneralPost) {
                Navigation.findNavController(it).popBackStack(R.id.personPostsFragment, false)
            }

            PersonPostModel.instance.updatePersonPost(updatedPersonPost) {
                Navigation.findNavController(it).popBackStack(R.id.personPostsFragment, false)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postId = args.postId

        GeneralPostModel.instance.getGeneralPostById(postId) {
            editTextPostId.setText(it?.postid) // Corrected to setText
            editTextRequest.setText(it?.request) // Corrected to setText
            editTextOffer.setText(it?.offer) // Corrected to setText
            editTextContact.setText(it?.contact) // Corrected to setText
            publisher = it?.publisher
        }

        buttonCancel.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        buttonDeletePost.setOnClickListener {
            GeneralPostModel.instance.getGeneralPostById(postId) {
                if (it != null) {
                    GeneralPostModel.instance.deleteGeneralPost(it) {}
                }
            }
            PersonPostModel.instance.getPersonPostById(postId) {
                if (it != null) {
                    PersonPostModel.instance.deletePersonPost(it) {}
                }
            }
            val action = EditPostFragmentDirections.actionEditPostFragmentToPersonPostsFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            imageView.setImageURI(imageUri)
        }
    }
}
