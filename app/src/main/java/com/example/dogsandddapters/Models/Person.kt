package com.example.dogsandddapters.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    @PrimaryKey val name: String,
    val id: String,
    val phoneNumber: String,
    val email: String,
    val dogType: String){
    //val avatarUrl: String) {

    companion object {
//        var lastUpdated: Long
//            get() {
//                return MyApplication.Globals
//                    .appContext?.getSharedPreferences("TAG", Context.MODE_PRIVATE)
//                    ?.getLong(GET_LAST_UPDATED, 0) ?: 0
//            }
//            set(value) {
//                MyApplication.Globals
//                    ?.appContext
//                    ?.getSharedPreferences("TAG", Context.MODE_PRIVATE)?.edit()
//                    ?.putLong(GET_LAST_UPDATED, value)?.apply()
//            }

        const val NAME_KEY = "name"
        const val ID_KEY = "id"
        const val PHONE_NUMBER_KEY = "phoneNumber"
        const val EMAIL_KEY = "email"
        const val DOG_TYPE_KEY = "dogType"
       // const val AVATAR_URL_KEY = "avatarUrl"


        fun fromJSON(json: Map<String, Any>): Person {
            val id = json[ID_KEY] as? String ?: ""
            val name = json[NAME_KEY] as? String ?: ""
            val phoneNumber = json[PHONE_NUMBER_KEY] as? String ?: ""
            val email = json[EMAIL_KEY] as? String ?: ""
            val dogType = json[DOG_TYPE_KEY] as? String ?: ""
           // val avatarUrl = json[AVATAR_URL_KEY] as? String ?: ""
            return Person(name, id, phoneNumber, email, dogType)
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
                //AVATAR_URL_KEY to avatarUrl,
            )
        }
}