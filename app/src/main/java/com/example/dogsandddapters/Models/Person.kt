package com.example.dogsandddapters.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    val name: String,
    val id: String,
    val phoneNumber: String,
    @PrimaryKey val email: String,
    val dogType: String,
    val image: String,

)
{


    constructor() : this("", "", "", "", "", "")

    companion object {

        const val NAME_KEY = "name"
        const val ID_KEY = "id"
        const val PHONE_NUMBER_KEY = "phoneNumber"
        const val EMAIL_KEY = "email"
        const val DOG_TYPE_KEY = "dogType"
        const val IMAGE_KEY = "image"
       const val GET_LAST_UPDATED = "get_last_updated"


        fun fromJSON(json: Map<String, Any>): Person {
            val id = json[ID_KEY] as? String ?: ""
            val name = json[NAME_KEY] as? String ?: ""
            val phoneNumber = json[PHONE_NUMBER_KEY] as? String ?: ""
            val email = json[EMAIL_KEY] as? String ?: ""
            val dogType = json[DOG_TYPE_KEY] as? String ?: ""
            val image = json[PersonPost.IMAGE_KEY] as? String ?: ""
            val person= Person(name, id, phoneNumber, email, dogType, image)

            return person
        }
    }


    val json: Map<String, Any>
        get() {
            return hashMapOf(
                ID_KEY to id,
                NAME_KEY to name,
                PHONE_NUMBER_KEY to phoneNumber,
                EMAIL_KEY to email,
                DOG_TYPE_KEY to dogType,
                IMAGE_KEY to image ,

            )
        }
}