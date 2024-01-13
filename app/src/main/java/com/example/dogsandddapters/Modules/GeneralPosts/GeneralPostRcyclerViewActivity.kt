package com.example.dogsandddapters.Modules.GeneralPosts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.GeneralPostModel
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.Modules.GeneralPosts.GeneralPostAdapter.GeneralPostsRecyclerAdapter
import com.example.dogsandddapters.R
import com.example.dogsandddapters.databinding.ActivitySGeneralPostRcyclerViewBinding
class GeneralPostsRcyclerViewActivity : AppCompatActivity() {

    var generalPostsRcyclerView: RecyclerView? = null
    var generalposts: List<GeneralPost>? = null
    var adapter: GeneralPostsRecyclerAdapter? = null

    private lateinit var binding: ActivityGeneralPostsRcyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGeneralPostsRcyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GeneralPostModel.instance.getAllGeneralPosts { generalposts ->
            this.generalposts = generalposts
            adapter?.generalposts = generalposts
            adapter?.notifyDataSetChanged()
        }

        generalPostsRcyclerView = binding.rvGeneralPostRecyclerList
        generalPostsRcyclerView?.setHasFixedSize(true)
        generalPostsRcyclerView?.layoutManager = LinearLayoutManager(this)

        adapter = GeneralPostsRecyclerAdapter(generalposts)
        adapter?.listener = object : OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "GeneralPostsRecyclerAdapter: Position clicked $position")
            }

            override fun onGeneralPostClicked(generalpost: GeneralPost?) {
                Log.i("TAG", "GENERAL POST $generalposts")
            }
        }

        generalPostsRcyclerView?.adapter = adapter
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) // General Post
        fun onGeneralPostClicked(generalpost: GeneralPost?)
    }

    override fun onResume() {
        super.onResume()

        GeneralPostModel.instance.getAllGeneralPosts { generalposts ->
            this.generalposts = generalposts
            adapter?.generalposts = generalposts
            adapter?.notifyDataSetChanged()
        }
    }
}