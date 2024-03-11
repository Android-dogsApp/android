package com.example.dogsandddapters

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.Person
import com.example.dogsandddapters.Models.PersonModel
import com.example.dogsandddapters.Modules.addPersonPost.ImageSelectionAdapter
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class editProfileFragment : Fragment() {
    private val args: editProfileFragmentArgs by navArgs()

    private lateinit var imageView: ImageView // Declare imageView here

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call the preloading task when the fragment is created
        ImagePreloadingTask().execute()

        //val personId = args.postId
        val email= args.email

        val personModel = PersonModel.instance
        val editTextName: TextView = view.findViewById(R.id.editTextName)
        val editTextDogTypes: TextView = view.findViewById(R.id.editTextDogTypes)
        val editTextPhone: TextView = view.findViewById(R.id.editTextPhone)
        val editTextEmail: TextView = view.findViewById(R.id.editTextEmail)
        val btnUploadImage: Button = view.findViewById(R.id.btnUploadImage)
        imageView = view.findViewById(R.id.imageView) // Initialize imageView here
        val buttonUpdate: Button = view.findViewById(R.id.buttonUpdate)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)
        var personId= ""

        PersonModel.instance.getPersonByEmail(email){
            Log.i("EditProfileFragment", "email : ${email}")
            editTextName.text =  it?.name
            Log.i("EditProfileFragment", " editTextName: ${editTextName.text} ")
            editTextDogTypes.text = it?.dogType
            editTextPhone.text = it?.phoneNumber
            editTextEmail.text = it?.email
            personId= it?.id.toString()
            Log.i("EditProfileFragment", " personId: ${personId} ")
        }

        btnUploadImage.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_image_selection, null)
            val recyclerViewImages: RecyclerView = dialogView.findViewById(R.id.recyclerViewImages)

            recyclerViewImages.layoutManager = GridLayoutManager(requireContext(), 2)

            val imageUrls = listOf(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Labrador_Retriever_portrait.jpg/1200px-Labrador_Retriever_portrait.jpg",
                "https://www.southernliving.com/thmb/NnmgOEms-v3uG4T6SRgc8QDGlUA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/gettyimages-837898820-2000-667fc4cc028a43369037e229c9bd52fb.jpg",
                "https://media.npr.org/assets/img/2022/05/25/gettyimages-917452888-edit_custom-c656c35e4e40bf22799195af846379af6538810c-s1100-c50.jpg",
                "https://hgtvhome.sndimg.com/content/dam/images/hgtv/fullset/2022/6/16/1/shutterstock_1862856634.jpg.rend.hgtvcom.1280.853.suffix/1655430860853.jpeg"
            )

            val adapter = ImageSelectionAdapter(imageUrls) { imageUrl ->
                // Load the selected image directly into the ImageView
                Picasso.get().load(imageUrl).into(imageView)
            }
            recyclerViewImages.adapter = adapter

            // Notify the adapter that data has changed
            adapter.notifyDataSetChanged()

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("Select Image")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            // Show the dialog after attaching the adapter to the RecyclerView
            dialog.show()
        }

//        buttonUpdate?.setOnClickListener {
//            val id = personId
//            val name = editTextName.text.toString()
//            val phoneNumber = editTextPhone.text.toString()
//            val email = editTextEmail.text.toString()
//            val dogType = editTextDogTypes.text.toString()
//            val updatedPerson= Person(name, id, phoneNumber, email, dogType)
//
//            PersonModel.instance.updatePerson(updatedPerson){
//                //Navigation.findNavController(it).popBackStack(R.id.PersonPostsFragment, false)
//            }
//            val action = editProfileFragmentDirections.actionEditProfileFragmentToGeneralPostsFragment()
//            Navigation.findNavController(view).navigate(action)
//        }

        buttonUpdate?.setOnClickListener {
            val id = personId
            val name = editTextName.text.toString()
            val phoneNumber = editTextPhone.text.toString()
            val email = editTextEmail.text.toString()
            val dogType = editTextDogTypes.text.toString()

            if (name.isNullOrBlank() || phoneNumber.isNullOrBlank() || email.isNullOrBlank() || dogType.isNullOrBlank()) {
                // Show an error message to the user, for example, using a Toast
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val updatedPerson = Person(name, id, phoneNumber, email, dogType)

                PersonModel.instance.updatePerson(FirebaseAuth.getInstance().currentUser?.uid!!,updatedPerson) {
                    // Navigation.findNavController(it).popBackStack(R.id.PersonPostsFragment, false)
                }
                PersonModel.instance.getPerson(FirebaseAuth.getInstance().currentUser?.uid!!) {
                    val personId= it?.id
                    val email = it?.email
                    val action = editProfileFragmentDirections.actionEditProfileFragmentToProfileFragment(email!!,personId!!)
                    Navigation.findNavController(view).navigate(action)
                }
            }


//            val action = editProfileFragmentDirections.actionEditProfileFragmentToGeneralPostsFragment()
//            Navigation.findNavController(view).navigate(action)
        }


        buttonCancel?.setOnClickListener {
            val action = editProfileFragmentDirections.actionEditProfileFragmentToEntryFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        // Define the values to insert into the MediaStore
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        // Insert the image into the MediaStore
        val uri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        // Write the bitmap data to the content resolver
        uri?.let { imageUri ->
            requireContext().contentResolver.openOutputStream(imageUri).use { outputStream ->
                outputStream?.let {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
            }
            Toast.makeText(requireContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == editProfileFragment.IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            Picasso.get().load(selectedImageUri).into(imageView as ImageView)
        }
    }

    companion object {
        const val IMAGE_PICK_REQUEST_CODE = 123
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
                "https://hgtvhome.sndimg.com/content/dam/images/hgtv/fullset/2022/6/16/1/shutterstock_1862856634.jpg.rend.hgtvcom.1280.853.suffix/1655430860853.jpeg"
            )

            for (imageUrl in imageUrls) {
                Picasso.get().load(imageUrl).fetch()
            }
        }
    }
}
