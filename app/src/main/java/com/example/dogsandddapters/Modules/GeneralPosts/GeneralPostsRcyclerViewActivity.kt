package com.example.dogsandddapters.Modules.GeneralPosts

//import com.example.dogsandddapters.Models.GeneralPostModel
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.Modules.GeneralPosts.GeneralPostAdapter.GeneralPostsRecyclerAdapter
import com.example.dogsandddapters.databinding.ActivityGeneralPostRcyclerViewBinding

class GeneralPostsRcyclerViewActivity : AppCompatActivity() {

    var generalPostsRcyclerView: RecyclerView? = null
    var generalposts: List<PersonPost>? = null
    var adapter: GeneralPostsRecyclerAdapter? = null


    private lateinit var binding: ActivityGeneralPostRcyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGeneralPostRcyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        GeneralPostModel.instance.getAllGeneralPosts { generalposts ->
//            this.generalposts = generalposts
//            adapter?.generalposts = generalposts
//            adapter?.notifyDataSetChanged()
//        }

        generalPostsRcyclerView = binding.rvGeneralPostRecyclerList
        generalPostsRcyclerView?.setHasFixedSize(true)
        generalPostsRcyclerView?.layoutManager = LinearLayoutManager(this)

        adapter = GeneralPostsRecyclerAdapter(generalposts)
        adapter?.listener = object : OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "GeneralPostsRecyclerAdapter: Position clicked $position")
            }

            override fun onGeneralPostClicked(generalpost:PersonPost?) {
                Log.i("TAG", "GENERAL POST $generalposts")
            }
        }

        generalPostsRcyclerView?.adapter = adapter
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int) // General Post
        fun onGeneralPostClicked(generalpost: PersonPost?)
    }

    override fun onResume() {
        super.onResume()
//
//        GeneralPostModel.instance.getAllGeneralPosts { generalposts ->
//            this.generalposts = generalposts
//            adapter?.generalposts = generalposts
//            adapter?.notifyDataSetChanged()
//        }
    }
}