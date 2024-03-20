package com.example.dogsandddapters.Modules

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.MainActivity
import com.example.dogsandddapters.Models.Person
import com.example.dogsandddapters.Modules.addPersonPost.ImageSelectionAdapter
import com.example.dogsandddapters.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var nameEditText: EditText? = null
    private var idEditText: EditText? = null
    private var phoneNumberEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var dogTypeEditText: EditText? = null
    private var registerButton: Button? = null
    private var cancelButton: Button? = null
    private var btnUploadImage: Button? = null
    private var imageView: ImageView? = null
    var currentImageUrl: String?= null
   // var currentImageUrl: String? = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        nameEditText = view.findViewById(R.id.editTextName)
        idEditText = view.findViewById(R.id.editTextID)
        phoneNumberEditText = view.findViewById(R.id.editTextPhoneNumber)
        emailEditText = view.findViewById(R.id.editTextEmail)
        passwordEditText = view.findViewById(R.id.editTextPassword)
        dogTypeEditText = view.findViewById(R.id.editTextDogType)
        registerButton = view.findViewById(R.id.buttonRegister)
        cancelButton = view.findViewById(R.id.buttonCancel)
        btnUploadImage = view.findViewById(R.id.buttonUploadImage)
        imageView = view.findViewById(R.id.imageView)

        cancelButton?.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToEntryFragment()
            Navigation.findNavController(view).navigate(action)
        }

        btnUploadImage?.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_image_selection, null)
            val recyclerViewImages: RecyclerView = dialogView.findViewById(R.id.recyclerViewImages)

            recyclerViewImages.layoutManager = GridLayoutManager(requireContext(), 4)

            val imageUrls = listOf(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Labrador_Retriever_portrait.jpg/1200px-Labrador_Retriever_portrait.jpg",
                "https://www.southernliving.com/thmb/NnmgOEms-v3uG4T6SRgc8QDGlUA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/gettyimages-837898820-2000-667fc4cc028a43369037e229c9bd52fb.jpg",
                "https://media.npr.org/assets/img/2022/05/25/gettyimages-917452888-edit_custom-c656c35e4e40bf22799195af846379af6538810c-s1100-c50.jpg",
                "https://hgtvhome.sndimg.com/content/dam/images/hgtv/fullset/2022/6/16/1/shutterstock_1862856634.jpg.rend.hgtvcom.1280.853.suffix/1655430860853.jpeg",
                "https://m.media-amazon.com/images/I/71U+j7MrRqL._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/71VdO22kE0L._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/81DdQ4NqADL._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/614ZHzz6P+L._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/71NGW62YZ4L._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/613Arr4PtrL._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/91gMs06F57L._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/81jViwAqYxL._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/81mkoOlGsTL._AC_UL480_FMwebp_QL65_.jpg"
            )

            val adapter = ImageSelectionAdapter(imageUrls) { imageUrl ->
                currentImageUrl = imageUrl
                Picasso.get().load(imageUrl).into(imageView)

            }
            recyclerViewImages.adapter = adapter

            AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("Select Image")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        // Call the preloading task when the fragment is created
        ImagePreloadingTask().execute()

        registerButton?.setOnClickListener {
            var filled= true
            val email = emailEditText?.text.toString().trim()
            val password = passwordEditText?.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            registerUser(email, password)

//                Navigation.findNavController(requireView())
//                    .navigate(R.id.action_registerFragment_to_generalPostsFragment)
//                (requireActivity() as MainActivity).setBottomBarVisibility(true)
//                (requireActivity() as MainActivity).setAddMenuItemVisibility(true)


        }
    }

    inner class ImagePreloadingTask : AsyncTask<Void, Void, Unit>() {
        override fun doInBackground(vararg params: Void?) {
            preloadImages()
        }
        private fun preloadImages() {
            val imageUrls = listOf(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Labrador_Retriever_portrait.jpg/1200px-Labrador_Retriever_portrait.jpg",
                "https://www.southernliving.com/thmb/NnmgOEms-v3uG4T6SRgc8QDGlUA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/gettyimages-837898820-2000-667fc4cc028a43369037e229c9bd52fb.jpg",
                "https://media.npr.org/assets/img/2022/05/25/gettyimages-917452888-edit_custom-c656c35e4e40bf22799195af846379af6538810c-s1100-c50.jpg",
                "https://hgtvhome.sndimg.com/content/dam/images/hgtv/fullset/2022/6/16/1/shutterstock_1862856634.jpg.rend.hgtvcom.1280.853.suffix/1655430860853.jpeg",
                "https://m.media-amazon.com/images/I/71U+j7MrRqL._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/71VdO22kE0L._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/81DdQ4NqADL._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/614ZHzz6P+L._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/71NGW62YZ4L._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/613Arr4PtrL._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/91gMs06F57L._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/81jViwAqYxL._AC_UL480_FMwebp_QL65_.jpg",
                "https://m.media-amazon.com/images/I/81mkoOlGsTL._AC_UL480_FMwebp_QL65_.jpg"
            )

            for (imageUrl in imageUrls) {
                Picasso.get().load(imageUrl).fetch()
            }
        }
    }



    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        val userId = user.uid
                        var name = nameEditText?.text.toString()
                        var id = idEditText?.text.toString()
                        var phoneNumber = phoneNumberEditText?.text.toString()
                        var dogType = dogTypeEditText?.text.toString()

                        // Check each field and update flag accordingly
                        if (name.isNullOrBlank() || id.isNullOrBlank() || phoneNumber.isNullOrBlank() || dogType.isNullOrBlank() || currentImageUrl.isNullOrEmpty()) {
//                            name = "NeedToBeFilled"
//                            id = "NeedToBeFilled"
//                            phoneNumber = "NeedToBeFilled"
//                            dogType = "NeedToBeFilled"
                            Toast.makeText(
                                requireContext(),
                                "Please fill in all fields",
                                Toast.LENGTH_SHORT
                            ).show()
                            user.delete()

                        } else {
                            val person = Person(name, id, phoneNumber, email, dogType, currentImageUrl!!)
                            val db = FirebaseFirestore.getInstance()
                            db.collection("persons")
                                .document(userId)
                                .set(person)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        "Registration successful",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        context,
                                        "Error adding document: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_registerFragment_to_generalPostsFragment)
                            (requireActivity() as MainActivity).setBottomBarVisibility(true)
                            (requireActivity() as MainActivity).setAddMenuItemVisibility(true)

                        }
                    } else {
                        Toast.makeText(context, "User is null", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(context, "Email is already in use", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Registration failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                    }

                }
            }

    }


//    private fun registerUser(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    val user = auth.currentUser
//                    if (user != null) {
//                        val userId = user.uid
//                        val name = nameEditText?.text.toString()
//                        val id = idEditText?.text.toString()
//                        val phoneNumber = phoneNumberEditText?.text.toString()
//                        val dogType = dogTypeEditText?.text.toString()
//                        if (name.isNullOrBlank() || id.isNullOrBlank() || phoneNumber.isNullOrBlank() || dogType.isNullOrBlank() || currentImageUrl.isNullOrEmpty()) {
//                            Toast.makeText(
//                                requireContext(),
//                                "Please fill in all fields",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            return@addOnCompleteListener
//                        } else {
//                            val person =
//                                Person(name, id, phoneNumber, email, dogType, currentImageUrl!!)
//
//
//                            //val person = Person(name, id, phoneNumber, email, dogType, currentImageUrl!!)
//
//                            val db = FirebaseFirestore.getInstance()
//                            db.collection("persons")
//                                .document(userId)
//                                .set(person)
//                                .addOnSuccessListener {
//                                    // Log.i(TAG, "DocumentSnapshot added with ID: $userId")
//                                }
//                                .addOnFailureListener { e ->
//                                    // Log.i(TAG, "Error adding document", e)
//                                }
//
//
//                            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT)
//                                .show()
//                         }
//                    } else {
//                            Toast.makeText(context, "User is null", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                        val exception = task.exception
//                        if (exception is FirebaseAuthUserCollisionException) {
//                            Toast.makeText(context, "Email is already in use", Toast.LENGTH_SHORT)
//                                .show()
//                            return@addOnCompleteListener
//                        } else {
//                            Toast.makeText(
//                                context,
//                                "Registration failed: ${exception?.message}",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            return@addOnCompleteListener
//                        }
//                }
//
//            }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            currentImageUrl = selectedImageUri.toString() // Save the selected image URL
            Picasso.get().load(selectedImageUri).into(imageView) // Display the selected image
        }
    }


    companion object {
        const val IMAGE_PICK_REQUEST_CODE = 123
        // const val TAG = "RegisterFragment"
    }
}
