package com.example.dogsandddapters
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
import com.example.dogsandddapters.dao.AppLocalDatabasePersonPost
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executors
import com.example.dogsandddapters.dao.PersonPostsDao


class EntryFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textViewWelcomeBack: TextView
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        executor.execute {
            AppLocalDatabasePersonPost.db.PersonPostsDao().deleteId("toys for dog")



        }

        (requireActivity() as MainActivity).setBottomBarVisibility(false)
        (requireActivity() as MainActivity).setAddMenuItemVisibility(false)
        fun isLoggedIn(): Boolean {
            // return sharedPreferences.contains("email") && sharedPreferences.contains("password")
            FirebaseAuth.getInstance().currentUser?.let {
                return true
            }
            return false
        }
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

                val action = EntryFragmentDirections.actionEntryFragmentToProfileFragment(email!!, "")
                Navigation.findNavController(view).navigate(action)


         Navigation.findNavController(view).navigate(action)
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
                Navigation.findNavController(view).navigate(action)
            }

            btnLogin.setOnClickListener {
                val action = EntryFragmentDirections.actionEntryFragmentToLogInFragment()
                Navigation.findNavController(view).navigate(action)
            }

        }

    }
}
