package com.example.dogsandddapters.Modules.addPersonPost

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.Models.GeneralPostModel
import com.example.dogsandddapters.Models.PersonModel
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Models.PersonPostModel
import com.example.dogsandddapters.R
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import java.util.UUID

class addPersonPostFragment : Fragment() {

    private lateinit var editTextRequest: EditText
    private lateinit var textViewWordCount: TextView
    private lateinit var editTextOffer: EditText
    private lateinit var editTextContact: EditText
    private lateinit var btnPost: Button
    private lateinit var btnCancel: Button
    private lateinit var btnUploadImage: Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_person_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        editTextRequest = view.findViewById(R.id.editTextRequest)
        textViewWordCount = view.findViewById(R.id.textViewWordCount)
        editTextOffer = view.findViewById(R.id.editTextOffer)
        editTextContact = view.findViewById(R.id.editTextContact)
        btnPost = view.findViewById(R.id.btnPost)
        btnCancel = view.findViewById(R.id.btnCancel)
        btnUploadImage = view.findViewById(R.id.btnUploadImage)
        imageView = view.findViewById(R.id.imageView)

        btnUploadImage.setOnClickListener {
            openImageChooser()
        }

        btnPost.setOnClickListener {
            PersonModel.instance.getPerson(FirebaseAuth.getInstance().currentUser?.uid!!) { user ->
                val ID: String = UUID.randomUUID().toString()
                val publisher = user?.id.toString()
                val request = editTextRequest.text.toString()
                val offer = editTextOffer.text.toString()
                val contact = editTextContact.text.toString()

                val personPost = PersonPost(ID, publisher, request, offer, contact)
                PersonPostModel.instance.addPersonPost(personPost) {}

                val generalPost = GeneralPost(ID, publisher, request, offer, contact)
                GeneralPostModel.instance.addGeneralPost(generalPost) {}

                Navigation.findNavController(it).popBackStack(R.id.personPostsFragment, false)
            }
        }

        btnCancel.setOnClickListener {
            Navigation.findNavController(it).popBackStack(R.id.personPostsFragment, false)
        }
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            Picasso.get().load(selectedImageUri).into(imageView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        const val IMAGE_PICK_REQUEST_CODE = 123
    }
}
