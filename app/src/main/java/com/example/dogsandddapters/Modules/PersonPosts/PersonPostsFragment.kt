package com.example.dogsandddapters.Modules.PersonPosts

//import com.example.dogsandddapters.databinding.FragmentPersonPostsBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.dogsandddapters.Modules.PersonPosts.PersonPostAdapter.PersonPostsRecyclerAdapter

class PersonPostsFragment : Fragment() {

    var PersonPostsRcyclerView: RecyclerView? = null
    var adapter: PersonPostsRecyclerAdapter? = null
    var progressBar: ProgressBar? = null

    //private var _binding: FragmentPersonPostsBinding? = null
    //private val binding get() = _binding!!
    private lateinit var viewModel: PersonPostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        _binding = FragmentPersonPostsBinding.inflate(inflater, container, false)
//        val view = binding.root

        //progressBar = binding.progressBar

        progressBar?.visibility = View.VISIBLE

//        GeneralPostModel.instance.getAllGeneralPosts { generalposts ->
//            this.generalposts = generalposts
//            adapter?.generalposts = generalposts
//            adapter?.notifyDataSetChanged()
//
//            progressBar?.visibility = View.GONE
//        }

//       PersonPostsRcyclerView = binding.rvGeneralPostFragmentList
//        PersonPostsRcyclerView?.setHasFixedSize(true)
//        PersonPostsRcyclerView?.layoutManager = LinearLayoutManager(context)
//        adapter = PersonPostsRecyclerAdapter(personPosts)
//        adapter?.listener = object : PersonPostsRcyclerViewActivity.OnItemClickListener {
//
//            override fun onItemClick(position: Int) {
//                Log.i("TAG", "GeneralPostsRecyclerAdapter: Position clicked $position")
//            }
//
//            override fun onPersonPostClicked(personposts: PersonPost?) {
//                Log.i("TAG", "Person Post $personposts")
//            }
//        }

      //PersonPostsRcyclerView?.adapter = adapter

//        val addGeneralPostButton: ImageButton = view.findViewById(R.id.ibtnGeneralPostFragmentAddGeneralPost)
//        addGeneralPostButton.setOnClickListener {
//            val action = PersonPostsFragmentDirections.actionGeneralPostsFragmentToAddPersonPostFragment()
//            Navigation.findNavController(view).navigate(action)
//        }
//
        return view
    }

//    override fun onResume() {
//        super.onResume()
//
//        progressBar?.visibility = View.VISIBLE
//
////        GeneralPostModel.instance.getAllGeneralPosts { generalposts ->
////            this.generalposts = generalposts
////            adapter?.generalposts = generalposts
////            adapter?.notifyDataSetChanged()
////
////            progressBar?.visibility = View.GONE
////        }
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }


}