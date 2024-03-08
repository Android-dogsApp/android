package com.example.dogsandddapters.dao


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dogsandddapters.Models.GeneralPost
import com.example.dogsandddapters.base.MyApplication

@Database(entities = [GeneralPost::class], version = 5)
abstract class AppLocalDbGPRepository : RoomDatabase() {
    abstract fun GeneralPostDao(): GeneralPostDao
}

object AppLocalDatabaseGeneralPost {
    val db: AppLocalDbGPRepository by lazy {

        val context = MyApplication.Globals.appContext
            ?: throw IllegalStateException("Application context not available")

        Room.databaseBuilder(
            context,
            AppLocalDbGPRepository::class.java,
            "GeneralPostFileName.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

