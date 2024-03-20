package com.example.dogsandddapters.Modules.PersonPosts

import com.example.dogsandddapters.Models.PersonPostModel
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Modules.PersonPosts.PersonPostAdapter.PersonPostsRecyclerAdapter
import com.example.dogsandddapters.databinding.ActivityPersonPostRcyclerViewBinding

class PersonPostsRcyclerViewActivity : AppCompatActivity() {

    var personPostsRcyclerView: RecyclerView? = null
    var personposts: List<PersonPost>? = null
    var adapter: PersonPostsRecyclerAdapter? = null


    private lateinit var binding: ActivityPersonPostRcyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPersonPostRcyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)



       personPostsRcyclerView = binding.rvPersonPostRecyclerList
        personPostsRcyclerView?.setHasFixedSize(true)
        personPostsRcyclerView?.layoutManager = LinearLayoutManager(this)

        adapter = PersonPostsRecyclerAdapter(personposts)
        adapter?.listener = object : OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "PersonPostsRecyclerAdapter: Position clicked $position")
            }

            override fun onPersonPostClicked(personpost: PersonPost?) {
                Log.i("TAG", "PERSON POST $personposts")
            }
        }

        personPostsRcyclerView?.adapter = adapter
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) // General Post
        fun onPersonPostClicked(personpost:PersonPost?)
    }

    override fun onResume() {
        super.onResume()

    }
}