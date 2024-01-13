package com.example.dogsandddapters.Modules.addPersonPost

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dogsandddapters.R
import com.example.dogsandddapters.Models.PersonPostsModel
import com.example.dogsandddapters.Models.PersonPosts


class addPersonPostFragment : Fragment() {

    private lateinit var editTextRequest: EditText
    private lateinit var textViewWordCount: TextView
    private lateinit var editTextOffer: EditText
    private lateinit var editTextContact: EditText
    private lateinit var btnPost: Button
    private lateinit var btnCancel: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_person_post, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize your views
        editTextRequest = view.findViewById(R.id.editTextRequest)
        textViewWordCount = view.findViewById(R.id.textViewWordCount)
        editTextOffer = view.findViewById(R.id.editTextOffer)
        editTextContact = view.findViewById(R.id.editTextContact)
        btnPost = view.findViewById(R.id.btnPost)
        btnCancel = view.findViewById(R.id.btnCancel)

        // Add text change listener to the request EditText
        editTextRequest.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                // Update word count
                val wordCount = s?.toString()?.trim()?.split("\\s+".toRegex())?.size ?: 0
                textViewWordCount.text = "$wordCount/10 words"

                // Limit to 10 words
                if (wordCount > 10) {
                    val words = s?.toString()?.trim()?.split("\\s+".toRegex())?.take(10)
                    editTextRequest.setText(words?.joinToString(" "))
                    editTextRequest.setSelection(editTextRequest.text?.length ?: 0)
                }
            }
        })


        btnPost.setOnClickListener {
            val name = nameTextField?.text.toString()
            val id = idTextField?.text.toString()

            val student = PersonPosts(name, id, "", false)
            PersonPosts.instance.addStudent(student) {
                Navigation.findNavController(it).popBackStack(R.id.PersonPostsFragment, false)
            }
        }
        }

        btnCancel.setOnClickListener {
            Navigation.findNavController(it).popBackStack(R.id.PersonPostsFragment, false)
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
    }


}