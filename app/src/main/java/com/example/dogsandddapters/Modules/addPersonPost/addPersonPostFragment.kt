package com.example.dogsandddapters.Modules.addPersonPost

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
        // Inflate the layout for this fragment
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

        // Add text change listener to the request EditText
//        editTextRequest.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//                // Update word count
//                val wordCount = s?.toString()?.trim()?.split("\\s+".toRegex())?.size ?: 0
//                textViewWordCount.text = "$wordCount/10 words"
//
//                // Limit to 10 words
//                if (wordCount > 10) {
//                    val words = s?.toString()?.trim()?.split("\\s+".toRegex())?.take(10)
//                    editTextRequest.setText(words?.joinToString(" "))
//                    editTextRequest.setSelection(editTextRequest.text?.length ?: 0)
//                }
//            }
//        })


        btnUploadImage.setOnClickListener {
            // Load image using Picasso
            val imageUrl = "https://hips.hearstapps.com/hmg-prod/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg?crop=0.752xw:1.00xh;0.175xw,0&resize=1200:*" // Replace with your image URL
            Picasso.get().load(imageUrl).into(imageView)
        }

        btnPost.setOnClickListener {
            PersonModel.instance.getPerson(FirebaseAuth.getInstance().currentUser?.uid!!) {
                Log.i("TAG", " AddPersonPostFragment: Person ID ${it?.id}")
                //val ID = it?.id.toString()
                val ID: String = UUID.randomUUID().toString()
                val publisher = it?.id.toString()
                Log.i("TAG", " AddPersonPostFragment: Publisher after tostring ${publisher}")
                val request = editTextRequest.text.toString()
                val offer = editTextOffer.text.toString()
                val contact = editTextContact.text.toString()

                val personPost = PersonPost(ID, publisher, request, offer, contact)
                PersonPostModel.instance.addPersonPost(personPost) {}
                val generalPost = GeneralPost(ID, publisher, request, offer, contact)
                GeneralPostModel.instance.addGeneralPost(generalPost) {}
            }

        }

        btnCancel.setOnClickListener {
            Navigation.findNavController(it).popBackStack(R.id.personPostsFragment, false)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

}