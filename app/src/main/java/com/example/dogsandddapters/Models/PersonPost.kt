package com.example.dogsandddapters.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PersonPost(
    @PrimaryKey val postid: String,
    val publisher: Person? = null,
    val request: String,
    val offer: String,
    val contact: String,
    val image: String
) {
    companion object {
        @PrimaryKey
        const val id_KEY = "postid"
        const val PUBLISHER_KEY = "postpublisher"
        const val REQUEST_KEY = "postrequest"
        const val OFFER_KEY = "postoffer"
        const val CONTACT_KEY = "postcontact"
        const val IMAGE_KEY = "postimage"

        fun fromJSON(json: Map<String, Any>): PersonPost? {
            val postid = json[id_KEY] as? String ?: ""
            val publisher = json[PUBLISHER_KEY] as? Person ?: null
            val request = json[REQUEST_KEY] as? String ?: ""
            val offer = json[OFFER_KEY] as? String ?: ""
            val contact = json[CONTACT_KEY] as? String ?: ""
            val image = json[IMAGE_KEY] as? String ?: ""
            return PersonPost(postid, publisher, request, offer, contact, image)
        }
    }

    val toJson: Map<String, Any>
        get() {
            val map = hashMapOf(
                id_KEY to postid,
                REQUEST_KEY to request,
                OFFER_KEY to offer,
                CONTACT_KEY to contact,
                IMAGE_KEY to image
            )
            publisher?.let {
                map[PUBLISHER_KEY] = it.name
            } // Change 'name' to the actual property you want to include
            return map
        }
}
