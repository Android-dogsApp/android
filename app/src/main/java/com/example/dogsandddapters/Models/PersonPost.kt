package com.example.dogsandddapters.Models

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dogsandddapters.base.MyApplication
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

@Entity
data class PersonPost(
    @PrimaryKey val postid: String,
    val publisher: String?, //id of the person who posted
    val request: String,
    val offer: String,
    val contact: String,
    val image: String,
    var lastUpdated: Long? = null
)
{
    companion object {

        var lastUpdated: Long
            get()
            {
                return MyApplication.Globals
                    .appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                    ?.getLong(GET_LAST_UPDATED, 0) ?: 0
            }
        set(value)
            {
                MyApplication.Globals
                    .appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
                    ?.edit()
                    ?.putLong(GET_LAST_UPDATED, value)
                    ?.apply()
            }

        @PrimaryKey
        const val id_KEY = "postid"
        const val PUBLISHER_KEY = "postpublisher"
        const val REQUEST_KEY = "postrequest"
        const val OFFER_KEY = "postoffer"
        const val CONTACT_KEY = "postcontact"
        const val IMAGE_KEY = "postimage"
        const val LAST_UPDATED = "lastUpdated"
        const val GET_LAST_UPDATED = "get_last_updated"

        fun fromJSON(json: Map<String, Any>): PersonPost? {
            val postid = json[id_KEY] as? String ?: ""
            val publisher = json[PUBLISHER_KEY] as? String ?: ""
            val request = json[REQUEST_KEY] as? String ?: ""
            val offer = json[OFFER_KEY] as? String ?: ""
            val contact = json[CONTACT_KEY] as? String ?: ""
            val image = json[IMAGE_KEY] as? String ?: ""
            val personPost= PersonPost(postid, publisher, request, offer, contact, image)

//            val timestamp: Timestamp? = json[LAST_UPDATED] as? Timestamp
//            timestamp?.let {
//                personPost.lastUpdated= it.seconds
            val timestamp: Timestamp? = json[LAST_UPDATED] as? Timestamp
            timestamp?.let {
                personPost.lastUpdated = it.seconds
            }

            return personPost
        }
    }


    val json: Map<String, Any>
        get() {
            return hashMapOf(
                id_KEY to postid,
                REQUEST_KEY to request,
                OFFER_KEY to offer,
                CONTACT_KEY to contact,
                IMAGE_KEY to image ,
                LAST_UPDATED to FieldValue.serverTimestamp(),
                PUBLISHER_KEY to publisher!!

            )
//            publisher?.let {
//                map[PUBLISHER_KEY] = it.name
//            } // Change 'name' to the actual property you want to include
//            return map
        }
}
