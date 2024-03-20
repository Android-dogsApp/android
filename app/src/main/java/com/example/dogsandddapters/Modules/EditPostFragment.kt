package com.example.dogsandddapters.Modules

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
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Models.PersonPostModel
import com.example.dogsandddapters.Modules.addPersonPost.ImageSelectionAdapter
import com.example.dogsandddapters.R
import com.squareup.picasso.Picasso


class EditPostFragment : Fragment() {
    private val args: EditPostFragmentArgs by navArgs()

    private lateinit var editTextRequest: EditText
    private lateinit var editTextOffer: EditText
    private lateinit var editTextContact: EditText
    private lateinit var buttonCancel: Button
    private lateinit var buttonDeletePost: Button
    private lateinit var buttonSelectImage: Button
    private lateinit var buttonSave: Button
    private lateinit var imageView: ImageView
    private var publisher: String? = null
    private var currentImageUrl: String? = null


    private val PICK_IMAGE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_post, container, false)
        setupUI(view)
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         var postId= args.postId

        // Call the preloading task when the fragment is created
        ImagePreloadingTask().execute()

        PersonPostModel.instance.getPersonPostById(postId) {
           // editTextPostId.setText(it?.postid) // Corrected to setText
            editTextRequest.setText(it?.request) // Corrected to setText
            editTextOffer.setText(it?.offer) // Corrected to setText
            editTextContact.setText(it?.contact) // Corrected to setText
            publisher = it?.publisher
            Picasso.get().load(it?.image).into(imageView)
            currentImageUrl = it?.image
        }

        buttonCancel.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        buttonDeletePost.setOnClickListener {

            PersonPostModel.instance.getPersonPostById(postId) {
                if (it != null) {
                    PersonPostModel.instance.deletePersonPost(it) {}
                }
            }
            val action = EditPostFragmentDirections.actionEditPostFragmentToGeneralPostsFragment()
            Navigation.findNavController(view).navigate(action)
        }
        buttonSave.setOnClickListener {
            //val postid = editTextPostId.text.toString()
            val offer = editTextOffer.text.toString()
            val contact = editTextContact.text.toString()
            val request = editTextRequest.text.toString()

            if ( offer.isNullOrBlank() || contact.isNullOrBlank() || request.isNullOrBlank()) {
                // Show an error message to the user, for example, using a Toast
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {

                Log.i("EditPostFragment", "currentImageUrl: $currentImageUrl")

                val updatedPersonPost = PersonPost(postId, publisher, request, offer, contact,currentImageUrl!!)



                PersonPostModel.instance.updatePersonPost(updatedPersonPost) {
                }
                val action = EditPostFragmentDirections.actionEditPostFragmentToGeneralPostsFragment()
                Navigation.findNavController(view).navigate(action)
            }

        }
    }

    private fun setupUI(view: View) {
//        editTextPostId = view.findViewById(R.id.editTextPostId)
        editTextRequest = view.findViewById(R.id.editTextRequest)
        editTextOffer = view.findViewById(R.id.editTextOffer)
        editTextContact = view.findViewById(R.id.editTextContact)
        buttonCancel = view.findViewById(R.id.buttonCancel) // Corrected ID
        buttonDeletePost = view.findViewById(R.id.buttonDeletePost)
        buttonSelectImage = view.findViewById(R.id.btnSelectImage)
        buttonSave = view.findViewById(R.id.buttonSave)
        imageView = view.findViewById(R.id.imageView)
        val imageView1: ImageView = view.findViewById(R.id.imageView1)



        buttonSelectImage.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_image_selection, null)
            val recyclerViewImages: RecyclerView = dialogView.findViewById(R.id.recyclerViewImages)

            recyclerViewImages.layoutManager = GridLayoutManager(requireContext(), 2)


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
                Picasso.get().load(imageUrl)
                    .resize(600, 600)
                    .centerCrop()
                    .into(imageView)
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

        if (requestCode == EditPostFragment.IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            Picasso.get().load(selectedImageUri).into(imageView)
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

    companion object {
        const val IMAGE_PICK_REQUEST_CODE = 123
    }
}
