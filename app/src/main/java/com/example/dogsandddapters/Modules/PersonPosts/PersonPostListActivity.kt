package com.example.dogsandddapters.Modules.PersonPosts

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
import com.example.dogsandddapters.Models.PersonPost
import com.example.dogsandddapters.R

class PersonPostListActivity : AppCompatActivity() {
    var personPostListView: ListView? = null
    var personPosts: List<PersonPost>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_post_list)
//
//        Model.instance.getAllStudents { students ->
//            this.students = students
//        }

        personPostListView = findViewById(R.id.lvpersonPostList)
        personPostListView?.adapter = PersonPostsListAdapter(personPosts)

        personPostListView?.setOnItemClickListener { parent, view, position, id ->
            Log.i("TAG", "Row was clicked at: $position")
        }
    }

    class PersonPostsListAdapter(val personposts: List<PersonPost>?): BaseAdapter() {

        override fun getCount(): Int = personposts?.size ?: 0

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val personpost = personposts?.get(position)
            var view: View? = null
            if (convertView == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.person_post_layout_row, parent, false)
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

            val requestTextView: TextView? = view?.findViewById(R.id.requestTextViewperson)
            val offerTextView: TextView? = view?.findViewById(R.id.offerTextViewperson)
            val contactTextView: CheckBox? = view?.findViewById(R.id.contactTextViewperson)
            val personPostIdTextView: CheckBox? = view?.findViewById(R.id.personPostIdTextViewperson)

            requestTextView?.text = personpost?.request
            offerTextView?.text = personpost?.offer
            contactTextView?.text= personpost?.contact
            personPostIdTextView?.text= personpost?.postid

            return view!!
        }

        override fun getItemId(position: Int): Long = 0
    }
}