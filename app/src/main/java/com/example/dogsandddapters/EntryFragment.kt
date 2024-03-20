package com.example.dogsandddapters
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import androidx.fragment.app.Fragment
//import androidx.navigation.Navigation
//
//
//class EntryFragment : Fragment() {
//    @SuppressLint("MissingInflatedId")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view= inflater.inflate(R.layout.fragment_entry, container, false)
//
//        val registerButton: Button = view.findViewById(R.id.btnRegister)
//        registerButton.setOnClickListener {
//            val action = EntryFragmentDirections.actionEntryFragmentToRegisterFragment()
//            Navigation.findNavController(view).navigate(action)
//        }
//
//        val loginButton: Button = view.findViewById(R.id.btnLogin)
//        loginButton.setOnClickListener {
//            val action = EntryFragmentDirections.actionEntryFragmentToLogInFragment()
//            Navigation.findNavController(view).navigate(action)
//        }
//        // Hide BottomNavigationView
////        if (activity is MainActivity) {
////            (activity as MainActivity).hideBottomNavigationView()
////        }
//
////        val preson= hashMapOf(
////            "name" to "John",
////            "id" to "123",
////            "phoneNumber" to "123-456-7890",
////            "email" to "jhon@jhon",
////        )
////         val db = Firebase.firestore
////        db.collection("persons").add(preson)
////            .addOnSuccessListener { documentReference ->
////               //Log.d("DocumentSnapshot added with ID: ${documentReference.id}")
////            }
////            .addOnFailureListener { e ->
////               // Log.d("Error adding document $e")
////            }
//
////        val generalpost= hashMapOf(
////            "request" to "request",
////            "postid" to "456",
////            "offer" to "offer",
////            "contact" to "jhon@jhon",
////            "publisher" to "jhon@jhon",
////        )
////        val db = Firebase.firestore
////        db.collection("generalPosts").add(  generalpost )
////        .addOnSuccessListener { documentReference ->
////                //Log.d("DocumentSnapshot added with ID: ${documentReference.id}")
////            }
////            .addOnFailureListener { e ->
////                // Log.d("Error adding document $e")
////            }
//
//
//
//        return view
//    }
//
//
//}
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.dogsandddapters.Models.PersonModel
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executors


class EntryFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textViewWelcomeBack: TextView
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_entry, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        //val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        // Get references to UI elements
        textViewWelcomeBack = view.findViewById(R.id.textViewWelcomeBack)
        btnRegister = view.findViewById(R.id.btnRegister)
        btnLogin = view.findViewById(R.id.btnLogin)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         var executor = Executors.newSingleThreadExecutor()
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("find a baybysitter")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("a")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("baybysitter")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("food for dogs")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("toys for dogs")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("fun saturday")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("r")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("g")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("y")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("i")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("o")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("m")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("a")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("dbm")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("k")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("p")
//        AppLocalDatabasePersonPost.db.PersonPostsDao()getPersonPostById("e")
//        executor.execute {
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("find a babysitter")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("a")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("babysitter")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("food for dogs")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("toys for dog")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("fun saturday")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("sedrickloveyou")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("test")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("11")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("hi")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("archi124")
//            AppLocalDatabasePersonPost.db.PersonPostsDao() deleteId ("test123")
//        }
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("db")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("db1")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("db2")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("michalpost")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("db3")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("t")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("r")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("g")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("y")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("i")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("o")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("m")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("a")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("dbm")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("k")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("p")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("e")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("find a babysitter")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("a")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("babysitter")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("food for dogs")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("toys for dogs")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("fun saturday")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("sedrickloveyou")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("test")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("11")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("hi")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("archi124")
//        AppLocalDatabaseGeneralPost.db.GeneralPostDao()getGeneralPostById("test123")
























        (requireActivity() as MainActivity).setBottomBarVisibility(false)
        (requireActivity() as MainActivity).setAddMenuItemVisibility(false)
        fun isLoggedIn(): Boolean {
            // return sharedPreferences.contains("email") && sharedPreferences.contains("password")
            FirebaseAuth.getInstance().currentUser?.let {
                return true
            }
            return false
        }
        // Update UI based on login state
        FirebaseAuth.getInstance().signOut()
         var isLoggedIn = isLoggedIn()
        if (isLoggedIn)
        {
            Log.i("EntryFragment", "EntryFragment-isLoggedIn: $isLoggedIn")
            textViewWelcomeBack.visibility = View.VISIBLE
            btnRegister.visibility = View.GONE
            btnLogin.visibility = View.GONE
            PersonModel.instance.getPerson(FirebaseAuth.getInstance().currentUser?.uid!!) {
                Log.i("EntryFragment", "isLoggedIn: ${FirebaseAuth.getInstance().currentUser?.uid}")

                val email = it?.email
//                textViewWelcomeBack.text = "Welcome back, ${it?.name}!"
//                textViewWelcomeBack.visibility = View.VISIBLE

                //Handler().postDelayed({
                val action = EntryFragmentDirections.actionEntryFragmentToProfileFragment(email!!, "")
                Navigation.findNavController(view).navigate(action)
                //}, 2000)

//                val action = EntryFragmentDirections.actionEntryFragmentToProfileFragment(email!!, "")
//                //findNavController().navigate(action)
//                Navigation.findNavController(view).navigate(action)
            }


        } else
        {
            Log.i("EntryFragment", "EntryFragment-isLoggedIn: $isLoggedIn")
            textViewWelcomeBack.visibility = View.GONE
            btnRegister.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE
            (requireActivity() as MainActivity).setBottomBarVisibility(false)
            (requireActivity() as MainActivity).setAddMenuItemVisibility(false)


            btnRegister.setOnClickListener {
                val action = EntryFragmentDirections.actionEntryFragmentToRegisterFragment()
                //findNavController().navigate(action)
                Navigation.findNavController(view).navigate(action)
            }

            btnLogin.setOnClickListener {
                val action = EntryFragmentDirections.actionEntryFragmentToLogInFragment()
                //findNavController().navigate(action)
                Navigation.findNavController(view).navigate(action)
            }

        }

    }



}
