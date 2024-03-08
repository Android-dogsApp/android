package com.example.dogsandddapters.Modules.addPersonPost

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private val REQUEST_CODE_STORAGE_PERMISSION = 456 // You can use any unique integer value


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

//        btnUploadImage.setOnClickListener {
//            openImageChooser()
//            downloadAndSaveDogPhoto()
//
//        }

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


        btnPost.setOnClickListener {
            PersonModel.instance.getPerson(FirebaseAuth.getInstance().currentUser?.uid!!) { user ->
                val ID: String = UUID.randomUUID().toString()
                val publisher = user?.id.toString()
                val request = editTextRequest.text.toString()
                val offer = editTextOffer.text.toString()
                val contact = editTextContact.text.toString()

                val personPost = PersonPost(ID, publisher, request, offer, contact)
                PersonPostModel.instance.addPersonPost(user?.email!!,personPost) {}

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
    private fun checkStoragePermission(): Boolean {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return permission == PackageManager.PERMISSION_GRANTED
    }
    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE_STORAGE_PERMISSION
        )
    }

    // Inside your Fragment class
    private fun downloadAndSaveDogPhoto() {
        val imageUrl = "https://thumbor.forbes.com/thumbor/fit-in/900x510/https://www.forbes.com/advisor/wp-content/uploads/2023/07/top-20-small-dog-breeds.jpeg.jpg"
        Picasso.get().load(imageUrl).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                bitmap?.let { saveImageToGallery(it) }
            }

            override fun onBitmapFailed(e: java.lang.Exception?, errorDrawable: Drawable?) {
                // Handle failure
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Prepare to load
            }
        })
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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        const val IMAGE_PICK_REQUEST_CODE = 123
    }
}
