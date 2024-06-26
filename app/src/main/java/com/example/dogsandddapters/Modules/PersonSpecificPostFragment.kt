package com.example.dogsandddapters

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.dogsandddapters.Models.PersonModel
import com.example.dogsandddapters.Models.PersonPostModel
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

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
        val textViewPublisher: TextView = view.findViewById(R.id.textViewPublisher)
       // val textViewPhoneNumber : TextView = view.findViewById(R.id.textViewPhoneNumber)
        val textViewContact: TextView = view.findViewById(R.id.textViewcontact)
        val imageView: ImageView = view.findViewById(R.id.imageViewPost)
        var publisher: String?

        PersonPostModel.instance.getPersonPostById(postId){
            textViewPublisher.text = it?.publisher
            textViewRequest.text = it?.request
            textViewOffer.text = it?.offer
           textViewContact.text = it?.contact
           publisher = it?.publisher
            if (!it?.image.isNullOrEmpty()) {
                Picasso.get().load(it?.image)
                    .resize(400, 400)
                    .centerCrop()
                    .into(imageView)
            }
            PersonModel.instance.getPerson(FirebaseAuth.getInstance().currentUser?.uid!!){
                Log.i("TAG", " PersonSpecificPostFragment: Person ID ${it?.id}")
                Log.i("TAG", " PersonSpecificPostFragment: Person publisher ${publisher}")
                if (it?.id == publisher) {
                    Log.i("TAG", " PersonSpecificPostFragment: Person ID ${it?.id}")
                    Log.i("TAG", " PersonSpecificPostFragment: Person publisher ${publisher}")
                    val editButton: Button = view.findViewById(R.id.buttonEdit)
                    editButton.visibility = View.VISIBLE  // Set the visibility to visible

                    editButton.setOnClickListener {
                        val action =
                            PersonSpecificPostFragmentDirections.actionPersonSpecificPostFragmentToEditPostFragment(
                                postId
                            )
                        Navigation.findNavController(view).navigate(action)
                    }
                } else {
                    val editButton: Button = view.findViewById(R.id.buttonEdit)
                    editButton.visibility = View.GONE  // Set the visibility to gone
                }
            }
       }

    }



}