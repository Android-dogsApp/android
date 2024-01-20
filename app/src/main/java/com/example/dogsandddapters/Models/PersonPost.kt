package com.example.dogsandddapters.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonPost(
    @PrimaryKey val postTitle: String,
    val publisher: Person,
    val date: String,
    val content: String,
    val numOfLikes: Number,
    val numOfComments: Number,
    val comments:List<String>){

        companion object{
            @PrimaryKey const val POSTTITLE_KEY="postTitle"
            const val PUBLISHER_KEY="publisher"
            const val DATE_KEY="date"
            const val CONTENT_KEY="content"
            const val NUMOFLIKES_KEY="numOfLikes"
            const val NUMOFCOMMENTS_KEY="numofcomments"
            const val COMMENTS_KEY="comments"

            fun fromJSON(json: Map<String, Any>): PersonPost? {
                val postTitle = json[POSTTITLE_KEY] as? String ?: ""
                val publisher = json[PUBLISHER_KEY] as? Person ?: null
                val date = json[DATE_KEY] as? String ?: ""
                val content = json[CONTENT_KEY] as? String ?: ""
                val numOfLikes = json[NUMOFLIKES_KEY] as? Number ?: 0
                val numOfComments = json[NUMOFCOMMENTS_KEY] as? Number ?: 0
                val comments = json[COMMENTS_KEY] as? List<String> ?: null
             //   return PersonPost(postTitle, publisher, date, content,numOfLikes,numOfComments,comments)
                return null
        }
    }

    val json: Map<String, Any>
        get() {
            return hashMapOf(
                POSTTITLE_KEY to postTitle,
                PUBLISHER_KEY to publisher,
                DATE_KEY to date,
                CONTENT_KEY to content,
                NUMOFLIKES_KEY to numOfLikes,
                NUMOFCOMMENTS_KEY to numOfComments,
                COMMENTS_KEY to comments

            )
        }
}