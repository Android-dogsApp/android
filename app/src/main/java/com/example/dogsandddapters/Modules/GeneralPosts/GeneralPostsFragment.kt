package com.example.dogsandddapters.Modules.GeneralPosts

//import com.example.dogsandddapters.Models.GeneralPostModel
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
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.Models.GeneralPostModel
import com.example.dogsandddapters.Modules.GeneralPosts.GeneralPostAdapter.GeneralPostsRecyclerAdapter
import com.example.dogsandddapters.databinding.FragmentGeneralPostsBinding

class GeneralPostsFragment : Fragment() {
    var GeneralPostsRcyclerView: RecyclerView? = null
    var adapter: GeneralPostsRecyclerAdapter? = null
    var progressBar: ProgressBar? = null

    private var _binding: FragmentGeneralPostsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GeneralPostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGeneralPostsBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[GeneralPostViewModel::class.java]

        progressBar = binding.progressBar

        progressBar?.visibility = View.VISIBLE

        viewModel.generalposts = GeneralPostModel.instance.getAllgeneralPosts()

//        GeneralPostModel.instance.getAllGeneralPosts { generalposts ->
//            this.generalposts = generalposts
//            adapter?.generalposts = generalposts
//            adapter?.notifyDataSetChanged()
//
//            progressBar?.visibility = View.GONE
//        }


        GeneralPostsRcyclerView = binding.rvGeneralPostFragmentList
        GeneralPostsRcyclerView?.setHasFixedSize(true)
        GeneralPostsRcyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = GeneralPostsRecyclerAdapter((viewModel.generalposts?.value))
        adapter?.listener = object : GeneralPostsRcyclerViewActivity.OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "GeneralPostsRecyclerAdapter: Position clicked $position")
                val generalPost = viewModel.generalposts?.value?.get(position)
                generalPost?.let {
                    val action = GeneralPostsFragmentDirections.actionGeneralPostsFragmentToPersonSpecificPostFragment(it.postid)
                    Navigation.findNavController(view).navigate(action)
                }
            }

            override fun onGeneralPostClicked(generalposts: GeneralPost?) {
                Log.i("TAG", "General Post $generalposts")
            }
        }

        GeneralPostsRcyclerView?.adapter = adapter

        viewModel.generalposts?.observe(viewLifecycleOwner) {
            adapter?.generalposts = it
            adapter?.notifyDataSetChanged()
            progressBar?.visibility = View.GONE
        }

        binding.pullToRefresh.setOnRefreshListener {
            reloadData()
        }
        GeneralPostModel.instance.generalPostsListLoadingState.observe(viewLifecycleOwner) { state ->
            binding.pullToRefresh.isRefreshing = state == GeneralPostModel.LoadingState.LOADING
        }





        return view

//        val addGeneralPostButton: ImageButton = view.findViewById(R.id.ibtnGeneralPostFragmentAddGeneralPost)
//        addGeneralPostButton.setOnClickListener {
//            val action = GeneralPostsFragmentDirections.actionGeneralPostsFragmentToAddPersonPostFragment()
//            Navigation.findNavController(view).navigate(action)
//        }


    }

    override fun onResume() {
        super.onResume()
        reloadData()
    }

    private fun reloadData() {
        progressBar?.visibility = View.VISIBLE
        GeneralPostModel.instance.refreshAllgeneralPosts()
        progressBar?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}