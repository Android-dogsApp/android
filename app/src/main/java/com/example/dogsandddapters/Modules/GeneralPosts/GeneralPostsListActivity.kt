package com.example.dogsandddapters.Modules.GeneralPosts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.R

class GeneralPostsListActivity: AppCompatActivity() {
        var generalPostListView: ListView? = null
        var generalPosts: List<GeneralPost>? = null
     override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_general_post_list)
//
//        Model.instance.getAllStudents { students ->
//            this.students = students
//        }

         generalPostListView = findViewById(R.id.lvGeneralPostList)
         generalPostListView?.adapter = GeneralPostsListAdapter(generalPosts)

         generalPostListView?.setOnItemClickListener { parent, view, position, id ->
                Log.i("TAG", "Row was clicked at: $position")
            }
        }

        class GeneralPostsListAdapter(val generalposts: List<GeneralPost>?): BaseAdapter() {

            override fun getCount(): Int = generalposts?.size ?: 0

            override fun getItem(position: Int): Any {
                TODO("Not yet implemented")
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val generalpost = generalposts?.get(position)
                var view: View? = null
                if (convertView == null) {
                    view = LayoutInflater.from(parent?.context).inflate(R.layout.general_post_layout_row, parent, false)
//                    val studentCheckbox: CheckBox? = view?.findViewById(R.id.cbStudentListRow)
//                    studentCheckbox?.setOnClickListener {
//
//                        (studentCheckbox?.tag as? Int)?.let {tag ->
//                            var student = students?.get(tag)
//                            student?.isChecked = studentCheckbox?.isChecked ?: false
//                        }
//                    }
                }

                view = view ?: convertView

                val requestTextView: TextView? = view?.findViewById(R.id.requestTextView)
                val offerTextView: TextView? = view?.findViewById(R.id.offerTextView)
                val contactTextView: CheckBox? = view?.findViewById(R.id.contactTextView)
                val generalPostIdTextView: CheckBox? = view?.findViewById(R.id.generalPostIdTextView)

                requestTextView?.text = generalpost?.request
                offerTextView?.text = generalpost?.offer
                contactTextView?.text= generalpost?.contact
                generalPostIdTextView?.text= generalpost?.postid

                return view!!
            }

            override fun getItemId(position: Int): Long = 0
        }

}