package com.example.dogsandddapters.Modules.PersonPosts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.PersonModel
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Models.PersonPostModel
import com.example.dogsandddapters.Modules.PersonPosts.PersonPostAdapter.PersonPostsRecyclerAdapter
import com.example.dogsandddapters.databinding.FragmentPersonPostsBinding
import com.google.firebase.auth.FirebaseAuth

class PersonPostsFragment : Fragment() {

    var PersonPostsRcyclerView: RecyclerView? = null
    var adapter: PersonPostsRecyclerAdapter? = null
    var progressBar: ProgressBar? = null

    private var _binding: FragmentPersonPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PersonPostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPersonPostsBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[PersonPostsViewModel::class.java]

        progressBar = binding.progressBar2

        progressBar?.visibility = View.VISIBLE

        viewModel.personposts = PersonPostModel.instance.getAllpersonPosts()
             //Navigation.findNavController(it).popBackStack(R.id.personPostsFragment, false)


//        viewModel.personposts = PersonPostModel.instance.getAllpersonPosts { newPosts ->
//            //viewModel.addAllPersonPosts(newPosts ?: emptyList())
//            //Navigation.findNavController(view).popBackStack(R.id.personPostsFragment, false)
//        }

        val btnMoveToProfile: Button = binding.btnMoveToProfile
        btnMoveToProfile.setOnClickListener {
            PersonModel.instance.getPerson(FirebaseAuth.getInstance().currentUser?.uid!!){
                val email= it?.email
                val action = PersonPostsFragmentDirections.actionPersonPostsFragmentToProfileFragment(email!!, "")
                Navigation.findNavController(view).navigate(action)
            }
        }



        PersonPostsRcyclerView = binding.rvPersonPostFragmentList
        PersonPostsRcyclerView?.setHasFixedSize(true)
        PersonPostsRcyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = PersonPostsRecyclerAdapter((viewModel.personposts?.value))
        adapter?.listener = object : PersonPostsRcyclerViewActivity.OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "personPostsRecyclerAdapter: Position clicked $position")
                val personPost = viewModel.personposts?.value?.get(position)
                personPost?.let {
                    val action = PersonPostsFragmentDirections.actionPersonPostsFragmentToPersonSpecificPostFragment(it.postid)
                    Navigation.findNavController(view).navigate(action)
                }
            }

            override fun onPersonPostClicked(personposts: PersonPost?) {
                Log.i("TAG", "Person Post $personposts")
            }
        }

        PersonPostsRcyclerView?.adapter = adapter

        viewModel.personposts?.observe(viewLifecycleOwner) {
            adapter?.personposts = it
            adapter?.notifyDataSetChanged()
            progressBar?.visibility = View.GONE
        }

        binding.pullToRefresh2.setOnRefreshListener {
            reloadData()
        }
        PersonPostModel.instance.personPostsListLoadingState.observe(viewLifecycleOwner) { state ->
            binding.pullToRefresh2.isRefreshing = state == PersonPostModel.LoadingState.LOADING
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        reloadData()
    }
    private fun reloadData() {
        progressBar?.visibility = View.VISIBLE
        PersonPostModel.instance.refreshAllpersonPosts()
        progressBar?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}