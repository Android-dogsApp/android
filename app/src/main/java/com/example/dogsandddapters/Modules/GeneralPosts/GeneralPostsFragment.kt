package com.example.dogsandddapters.Modules.GeneralPosts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.GeneralPostModel
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.Modules.GeneralPosts.GeneralPostAdapter.GeneralPostsRecyclerAdapter
import com.example.dogsandddapters.R

class GeneralPostsFragment : Fragment() {
    var GeneralPostsRcyclerView: RecyclerView? = null
    var generalposts: List<GeneralPost>? = null
    var adapter: GeneralPostsRecyclerAdapter? = null
    var progressBar: ProgressBar? = null

    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        val view = binding.root

        progressBar = binding.progressBar

        progressBar?.visibility = View.VISIBLE

        GeneralPostModel.instance.getAllGeneralPosts { generalposts ->
            this.generalposts = generalposts
            adapter?.generalposts = generalposts
            adapter?.notifyDataSetChanged()

            progressBar?.visibility = View.GONE
        }

        GeneralPostsRcyclerView = binding.rvStudentsFragmentList
        GeneralPostsRcyclerView?.setHasFixedSize(true)
        GeneralPostsRcyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = GeneralPostsRecyclerAdapter(generalposts)
        adapter?.listener = object : GeneralPostsRcyclerViewActivity.OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "GeneralPostsRecyclerAdapter: Position clicked $position")
            }

            override fun onGeneralPostClicked(generalposts: GeneralPost?) {
                Log.i("TAG", "General Post $generalposts")
            }
        }

        GeneralPostsRcyclerView?.adapter = adapter

        val addStudentButton: ImageButton = view.findViewById(R.id.ibtnGeneralPostFragmentAddGeneralPost)
        val action = Navigation.createNavigateOnClickListener(GeneralPostsFragmentDirections.actionGlobalAddPostFragment())
        addStudentButton.setOnClickListener(action)

        return view
    }

    override fun onResume() {
        super.onResume()

        progressBar?.visibility = View.VISIBLE

        GeneralPostModel.instance.getAllGeneralPosts { generalposts ->
            this.generalposts = generalposts
            adapter?.generalposts = generalposts
            adapter?.notifyDataSetChanged()

            progressBar?.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }


}